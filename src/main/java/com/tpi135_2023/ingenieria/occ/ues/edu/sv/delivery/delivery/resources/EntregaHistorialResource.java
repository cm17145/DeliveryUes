/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.EntregaHistorialBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.EntregaHistorial;
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
@Path("/entregaHistorial")
@Produces(MediaType.APPLICATION_JSON)
public class EntregaHistorialResource {
    
    @Inject 
    private EntregaHistorialBean entregaHistorialBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarEntregaHistorial(){
    List<EntregaHistorial> listaEntregaHistorial = entregaHistorialBean.listar();
    return Response.ok(listaEntregaHistorial).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        EntregaHistorial entregaHistorialEncontrado = entregaHistorialBean.obtenerPorId(id).orElse(null);
        if(entregaHistorialEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la entregaHistorial con id" + id).build();
        }
        return Response.ok(entregaHistorialEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(EntregaHistorial entregaHistorial) throws URISyntaxException {
     if(entregaHistorial.getEstadoEntrega() == null || entregaHistorial.getEstadoEntrega() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     entregaHistorialBean.insertar(entregaHistorial);
     return Response.created(new URI(uriInfo + "/" + entregaHistorial.getIdEntregaHistorial())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, EntregaHistorial entregaHistorial){
     EntregaHistorial entregaHistorialEncontrado = entregaHistorialBean.obtenerPorId(id).orElse(null);
     if(entregaHistorialEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la entregaHistorial con id" + id).build();
     }
     entregaHistorialEncontrado.setEstadoEntrega(entregaHistorial.getEstadoEntrega());
     entregaHistorialEncontrado.setFechaAlcanzado(entregaHistorial.getFechaAlcanzado());
     entregaHistorialEncontrado.setLongitud(entregaHistorial.getLongitud());
     entregaHistorialEncontrado.setLatitud(entregaHistorial.getLatitud());
     entregaHistorialEncontrado.setObservaciones(entregaHistorial.getObservaciones());
     
     EntregaHistorial entregaHistorialActualizado = entregaHistorialBean.actualizar(entregaHistorialEncontrado);
     
     return Response.ok(entregaHistorialActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarEntregaHistorial(@PathParam("id") Long id){
     EntregaHistorial entregaHistorialEncontrado = entregaHistorialBean.obtenerPorId(id).orElse(null);
     if(entregaHistorialEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la entregaHistorial con id" + id).build();
     }
     
     entregaHistorialBean.eliminar(entregaHistorialEncontrado);
     return Response.ok().build();
     }
     
}
