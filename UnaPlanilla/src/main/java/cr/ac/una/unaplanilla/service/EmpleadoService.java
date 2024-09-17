package cr.ac.una.unaplanilla.service;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.util.Request;
import cr.ac.una.unaplanilla.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cbcar
 */
public class EmpleadoService {
    
    public Respuesta getUsuario(String usuario, String clave){
        try{
            Map<String,Object> parametros = new HashMap<>();
            parametros.put("usuario",usuario);
            parametros.put("clave",clave);
            Request request=new Request("EmpleadoController/usuario","/{usuario}/{clave}",parametros);
            request.get();
            if(request.isError()){
                return new Respuesta(false,request.getError(),"");
            }
            EmpleadoDto empleadoDto= (EmpleadoDto) request.readEntity(EmpleadoDto.class);
            
            return new Respuesta(true,"","", "Usuario",empleadoDto);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario [" + usuario + "]", ex);
            return new Respuesta(false, "Error obteniendo el usuario.", "getUsuario " + ex.getMessage());
        } 
    }
    
    public Respuesta getEmpleado(Long id){
        try{
            Map<String,Object> parametros = new HashMap<>();
            parametros.put("id",id);
            Request request=new Request("EmpleadoController/empleado","/{id}",parametros);
            request.get();
            if(request.isError()){
                return new Respuesta(false,request.getError(),"");
            }
            EmpleadoDto empleadoDto= (EmpleadoDto) request.readEntity(EmpleadoDto.class);
            
            return new Respuesta(true,"","", "Empleado",empleadoDto);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo el empleado [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el empleado.", "getEmpleado " + ex.getMessage());
        } 
    }
    
    public Respuesta getEmpleados(String cedula, String nombre, String pApellido, String sApellido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cedula", cedula);
            parametros.put("nombre", nombre);
            parametros.put("pApellido", pApellido);
            parametros.put("sApellido", sApellido);
            Request request = new Request("EmpleadoController/empleados", "/{cedula}/{nombre}/{pApellido}/{sApellido}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<EmpleadoDto> empleadosDto = (List<EmpleadoDto>) request.readEntity(new GenericType<List<EmpleadoDto>>() {});
            return new Respuesta(true, "", "", "Empleados", empleadosDto);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo empleados.", ex);
            return new Respuesta(false, "Error obteniendo empleados.", "getEmpleados " + ex.getMessage());
        }
    }
    
    public Respuesta guardarEmpleado(EmpleadoDto empleadoDto){
        try {
            Request request = new Request("EmpleadoController/empleado");
            request.post(empleadoDto);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            EmpleadoDto empleado= (EmpleadoDto) request.readEntity(EmpleadoDto.class);
            
            return new Respuesta(true, "", "", "Empleado",empleado);
        
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el empleado.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar el empleado.", "guardarEmpleado " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarEmpleado(Long id){
        try{
            Map<String,Object> parametros = new HashMap<>();
            parametros.put("id",id);
            Request request=new Request("EmpleadoController/empleado","/{id}",parametros);
            request.delete();
            if(request.isError()){
                return new Respuesta(false,request.getError(),"");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error eliminando el empleado", ex);
            return new Respuesta(false, "Error eliminando el empleado.", "eliminarEmpleado " + ex.getMessage());
        }
    }
}
