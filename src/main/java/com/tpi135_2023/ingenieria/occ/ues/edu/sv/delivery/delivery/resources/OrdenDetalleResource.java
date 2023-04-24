/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.OrdenDetalleBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.OrdenDetalle;
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
@Path("/ordenDetalle")
@Produces(MediaType.APPLICATION_JSON)
public class OrdenDetalleResource {
    
    @Inject 
    private OrdenDetalleBean ordenDetalleBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarOrdenDetalle(){
    List<OrdenDetalle> listaOrdenDetalle = ordenDetalleBean.listar();
    return Response.ok(listaOrdenDetalle).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        OrdenDetalle ordenDetalleEncontrado = ordenDetalleBean.obtenerPorId(id).orElse(null);
        if(ordenDetalleEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la OrdenDetalle con id" + id).build();
        }
        return Response.ok(ordenDetalleEncontrado).build();
    }  
    
     /*@POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(OrdenDetalle ordenDetalle) throws URISyntaxException {
     if(ordenDetalle.getObservaciones() == null || ordenDetalle.getObservaciones() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     ordenDetalleBean.insertar(ordenDetalle);
     return Response.created(new URI(uriInfo + "/" + ordenDetalle.getIdOrdenDetalle())).build();
    } */
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, OrdenDetalle ordenDetalle){
     OrdenDetalle ordenDetalleEncontrado = ordenDetalleBean.obtenerPorId(id).orElse(null);
     if(ordenDetalleEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la OrdenDetalle con id" + id).build();
     }
     ordenDetalleEncontrado.setCantidad(ordenDetalle.getCantidad());
     ordenDetalleEncontrado.setPrecio(ordenDetalle.getPrecio());
     ordenDetalleEncontrado.setObservaciones(ordenDetalle.getObservaciones());
     
     OrdenDetalle ordenDetalleActualizado = ordenDetalleBean.actualizar(ordenDetalleEncontrado);
     
     return Response.ok(ordenDetalleActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarOrdenDetalle(@PathParam("id") Long id){
     OrdenDetalle ordenDetalleEncontrado = ordenDetalleBean.obtenerPorId(id).orElse(null);
     if(ordenDetalleEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la OrdenDetalle con id" + id).build();
     }
     
     ordenDetalleBean.eliminar(ordenDetalleEncontrado);
     return Response.ok().build();
     }
     
}
