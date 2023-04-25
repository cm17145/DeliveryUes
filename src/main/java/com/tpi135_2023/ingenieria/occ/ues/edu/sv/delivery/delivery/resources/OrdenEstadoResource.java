/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.OrdenEstadoBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.OrdenEstado;
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
@Path("/ordenEstado")
@Produces(MediaType.APPLICATION_JSON)
public class OrdenEstadoResource {
    
    @Inject 
    private OrdenEstadoBean ordenEstadoBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarOrdenEstado(){
    List<OrdenEstado> listaOrdenEstado = ordenEstadoBean.listar();
    return Response.ok(listaOrdenEstado).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        OrdenEstado ordenEstadoEncontrado = ordenEstadoBean.obtenerPorId(id).orElse(null);
        if(ordenEstadoEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la OrdenEstado con id" + id).build();
        }
        return Response.ok(ordenEstadoEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(OrdenEstado ordenEstado) throws URISyntaxException {
     if(ordenEstado.getEstado() == null || ordenEstado.getEstado() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(ordenEstado.getFechaCreacion()== null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(ordenEstado.getComentarios()== null || ordenEstado.getComentarios()== ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     ordenEstadoBean.insertar(ordenEstado);
     return Response.created(new URI(uriInfo + "/" + ordenEstado.getIdOrdenEstado())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, OrdenEstado ordenEstado){
     OrdenEstado ordenEstadoEncontrado = ordenEstadoBean.obtenerPorId(id).orElse(null);
     if(ordenEstadoEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la OrdenEstado cliente con id" + id).build();
     }
     ordenEstadoEncontrado.setEstado(ordenEstado.getEstado());
     ordenEstadoEncontrado.setFechaCreacion(ordenEstado.getFechaCreacion());
     ordenEstadoEncontrado.setComentarios(ordenEstado.getComentarios());
     
     OrdenEstado ordenEstadoActualizado = ordenEstadoBean.actualizar(ordenEstadoEncontrado);
     
     return Response.ok(ordenEstadoActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarOrdenEstado(@PathParam("id") Long id){
     OrdenEstado ordenEstadoEncontrado = ordenEstadoBean.obtenerPorId(id).orElse(null);
     if(ordenEstadoEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la OrdenEstado cliente con id" + id).build();
     }
     
     ordenEstadoBean.eliminar(ordenEstadoEncontrado);
     return Response.ok().build();
     }
     
}
