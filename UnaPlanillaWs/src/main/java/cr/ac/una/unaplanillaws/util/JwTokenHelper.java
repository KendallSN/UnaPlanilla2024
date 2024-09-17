package cr.ac.una.unaplanillaws.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwTokenHelper {
    private static JwTokenHelper jwTokenHelper=null;
    private static final long EXPIRATION_LIMIT=10;
    private static final long EXPIRATION_RENEWALL_LIMIT=20;
    private static final String AUTHENTICATION_SCHEME="Bearer ";
    private final Key key=Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private JwTokenHelper() {
    
    }
    public static JwTokenHelper getInstance() {
        if(jwTokenHelper==null){
            jwTokenHelper= new JwTokenHelper();
        }
        return jwTokenHelper;
    }
    private Date getExpirationDate(boolean renewal){
        long currentTimeInMillis = System.currentTimeMillis();
        long expMilliSecond = TimeUnit.MINUTES.toMillis(EXPIRATION_LIMIT);
        if(renewal){
            expMilliSecond = TimeUnit.MINUTES.toMillis(EXPIRATION_RENEWALL_LIMIT);
        }
        return new Date(currentTimeInMillis+expMilliSecond);
    }
    public String generatePrivateKey(String username){
    return AUTHENTICATION_SCHEME+Jwts
            .builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(getExpirationDate(false))
            .signWith(key).compact();
    }
    public Claims claimKey(String privateKey)throws ExpiredJwtException,MalformedJwtException{
        return Jwts.parser().setSigningKey(key)
                .parseClaimsJws(privateKey)
                .getBody();
    }
}
