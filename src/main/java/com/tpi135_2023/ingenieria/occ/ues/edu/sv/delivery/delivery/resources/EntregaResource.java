/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.EntregaBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Entrega;
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
@Path("/entrega")
@Produces(MediaType.APPLICATION_JSON)
public class EntregaResource {
    
    @Inject 
    private EntregaBean entregaBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarEntrega(){
    List<Entrega> listaEntrega = entregaBean.listar();
    return Response.ok(listaEntrega).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        Entrega entregaEncontrado = entregaBean.obtenerPorId(id).orElse(null);
        if(entregaEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la Entrega con id" + id).build();
        }
        return Response.ok(entregaEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(Entrega entrega) throws URISyntaxException {
     if(entrega.getObservaciones() == null || entrega.getObservaciones() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     entregaBean.insertar(entrega);
     return Response.created(new URI(uriInfo + "/" + entrega.getIdEntrega())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, Entrega entrega){
     Entrega entregaEncontrado = entregaBean.obtenerPorId(id).orElse(null);
     if(entregaEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la Entrega con id" + id).build();
     }
     entregaEncontrado.setFechaCreacion(entrega.getFechaCreacion());
     entregaEncontrado.setObservaciones(entrega.getObservaciones());
     
     Entrega entregaActualizado = entregaBean.actualizar(entregaEncontrado);
     
     return Response.ok(entregaActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarComercio(@PathParam("id") Long id){
     Entrega entregaEncontrado = entregaBean.obtenerPorId(id).orElse(null);
     if(entregaEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la entrega con id" + id).build();
     }
     
     entregaBean.eliminar(entregaEncontrado);
     return Response.ok().build();
     }
     
}