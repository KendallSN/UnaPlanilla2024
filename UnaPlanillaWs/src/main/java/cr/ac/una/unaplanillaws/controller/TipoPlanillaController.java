package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.model.TipoPlanillaDto;
import cr.ac.una.unaplanillaws.service.TipoPlanillaService;
import cr.ac.una.unaplanillaws.util.CodigoRespuesta;
import cr.ac.una.unaplanillaws.util.Respuesta;
import cr.ac.una.unaplanillaws.util.Secure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//@Secure
@Path("/TipoPlanillaController")
@Tag(name = "TiposPlanilla",description = "Operacines sobre tipoPlanilla")
@SecurityRequirement(name="jwt-auth")
public class TipoPlanillaController {
    @EJB
    TipoPlanillaService tipoPlanillaService;
    
    
    @GET
    @Path("/tipoPlanilla/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Obtiene el tipoPlanilla")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ("TipoPlanilla Obtenido"),content = @Content(mediaType = MediaType.APPLICATION_JSON,schema = @Schema(implementation = TipoPlanillaDto.class))),
        @ApiResponse(responseCode = "404", description = "TipoPlanilla No Obtenido", content = @Content(mediaType = MediaType.TEXT_PLAIN)),
        @ApiResponse(responseCode = "500", description = "Error interno durante la obtención del TipoPlanilla", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    public Response getTipoPlanilla(@PathParam ("id")Long id) {
    try {
        Respuesta res=tipoPlanillaService.getTipoPlanilla(id);
           if(!res.getEstado()){
           return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
           }
           TipoPlanillaDto tipoPlanillaDto=(TipoPlanillaDto) res.getResultado("TipoPlanilla");
           return Response.ok(tipoPlanillaDto).build();
            
            } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el tipoPlanilla").build();
        }
    }
    @GET
    @Path("/tipoPlanillas/{codigo}/{descripcion}/{plaxmes}/{idemp}/{cedula}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Obtiene los tipoPlanilla")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ("TipoPlanillas Obtenidos"),content = @Content(mediaType = MediaType.APPLICATION_JSON,schema = @Schema(implementation = TipoPlanillaDto.class))),
        @ApiResponse(responseCode = "404", description = "TipoPlanillas No Obtenidos", content = @Content(mediaType = MediaType.TEXT_PLAIN)),
        @ApiResponse(responseCode = "500", description = "Error interno durante la obtención de los TipoPlanillas", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    public Response getTipoPlanillas(@PathParam ("codigo")String codigo,@PathParam ("descripcion")String descripcion,@PathParam ("plaxmes")String plaxmes,@PathParam ("idemp")String idemp,@PathParam ("cedula")String cedula) {
    try {
        Respuesta res=tipoPlanillaService.getTipoPlanillas(codigo, descripcion, plaxmes, idemp, cedula);
           if(!res.getEstado()){
           return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
           }
           return Response.ok((new GenericEntity<List<TipoPlanillaDto>>((List<TipoPlanillaDto>) res.getResultado("TipoPlanillas")) {
            })).build();
            
            } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo los tipoPlanillas").build();
        }
    }
    
    @POST
    @Path("/tipoPlanilla")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto){
    try {
            Respuesta res = tipoPlanillaService.guardarTipoPlanilla(tipoPlanillaDto);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            
            return Response.ok((TipoPlanillaDto)res.getResultado("TipoPlanilla")).build();
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el tipoPlanilla").build();
        }
    }
    
    @DELETE
    @Path("/tipoPlanilla/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarTipoPlanilla(@PathParam ("id")Long id) {
    try {
        Respuesta res=tipoPlanillaService.eliminarTipoPlanilla(id);
           if(!res.getEstado()){
           return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
           }
           return Response.ok().build();
           
            } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error eliminando el tipoPlanilla").build();
        }
    }
}
