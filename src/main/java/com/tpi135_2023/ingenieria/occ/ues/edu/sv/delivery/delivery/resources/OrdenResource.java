/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.OrdenBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Orden;
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
@Path("/orden")
@Produces(MediaType.APPLICATION_JSON)
public class OrdenResource {
    
    @Inject 
    private OrdenBean ordenBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarOrden(){
    List<Orden> listaOrden = ordenBean.listar();
    return Response.ok(listaOrden).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        Orden ordenEncontrado = ordenBean.obtenerPorId(id).orElse(null);
        if(ordenEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el cliente con id" + id).build();
        }
        return Response.ok(ordenEncontrado).build();
    }  
    
     /*@POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(Orden orden) throws URISyntaxException {
     if(orden.getObservaciones() == null || orden.getObservaciones(() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     ordenBean.insertar(orden);
     return Response.created(new URI(uriInfo + "/" + orden.getIdCliente())).build();
    } */
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, Orden orden){
     Orden ordenEncontrado = ordenBean.obtenerPorId(id).orElse(null);
     if(ordenEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la Orden con id" + id).build();
     }
     ordenEncontrado.setFechaOrden(orden.getFechaOrden());
     ordenEncontrado.setObservaciones(orden.getObservaciones());
     ordenEncontrado.setIdDireccion(orden.getIdDireccion());
     
     Orden ordenActualizado = ordenBean.actualizar(ordenEncontrado);
     
     return Response.ok(ordenActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarOrden(@PathParam("id") Long id){
     Orden ordenEncontrado = ordenBean.obtenerPorId(id).orElse(null);
     if(ordenEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la Orden con id" + id).build();
     }
     
     ordenBean.eliminar(ordenEncontrado);
     return Response.ok().build();
     }
     
}
