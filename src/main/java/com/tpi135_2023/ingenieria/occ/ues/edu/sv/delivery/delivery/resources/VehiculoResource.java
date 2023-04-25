/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.VehiculoBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Vehiculo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 *
 * @author Luis
 */
@RequestScoped
@Path("/vehiculo")
@Produces(MediaType.APPLICATION_JSON)
public class VehiculoResource {
    
    @Inject 
    private VehiculoBean vehiculoBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarVehiculo(){
    List<Vehiculo> listaVehiculo = vehiculoBean.listar();
    return Response.ok(listaVehiculo).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        Vehiculo vehiculoEncontrado = vehiculoBean.obtenerPorId(id).orElse(null);
        if(vehiculoEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el Vehiculo con id" + id).build();
        }
        return Response.ok(vehiculoEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(Vehiculo vehiculo) throws URISyntaxException {
     if(vehiculo.getTipoVehiculo()== null || vehiculo.getTipoVehiculo()== ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(vehiculo.getPlaca()== null || vehiculo.getPlaca()== ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(vehiculo.getPropietario()== null || vehiculo.getPropietario()== ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(vehiculo.getActivo()== null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(vehiculo.getComentarios()== null || vehiculo.getComentarios()== ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     vehiculoBean.insertar(vehiculo);
     return Response.created(new URI(uriInfo + "/" + vehiculo.getIdVehiculo())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, Vehiculo vehiculo){
     Vehiculo vehiculoEncontrado = vehiculoBean.obtenerPorId(id).orElse(null);
     if(vehiculoEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el cliente con id" + id).build();
     }
     vehiculoEncontrado.setTipoVehiculo(vehiculo.getTipoVehiculo());
     vehiculoEncontrado.setPlaca(vehiculo.getPlaca());
     vehiculoEncontrado.setPropietario(vehiculo.getPropietario());
     vehiculoEncontrado.setActivo(vehiculo.getActivo());
     vehiculoEncontrado.setComentarios(vehiculo.getComentarios());
     
     Vehiculo vehiculoActualizado = vehiculoBean.actualizar(vehiculoEncontrado);
     
     return Response.ok(vehiculoActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarVehiculo(@PathParam("id") Long id){
     Vehiculo vehiculoEncontrado = vehiculoBean.obtenerPorId(id).orElse(null);
     if(vehiculoEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el cliente con id" + id).build();
     }
     
     vehiculoBean.eliminar(vehiculoEncontrado);
     return Response.ok().build();
     }
     
}
