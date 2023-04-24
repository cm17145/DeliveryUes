/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.TipoProductoBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.TipoProducto;
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
@Path("/tipoProducto")
@Produces(MediaType.APPLICATION_JSON)
public class TipoProductoResource {
    
    @Inject 
    private TipoProductoBean tipoProductoBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarTipoProducto(){
    List<TipoProducto> listaTipoProducto = tipoProductoBean.listar();
    return Response.ok(listaTipoProducto).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        TipoProducto tipoProductoEncontrado = tipoProductoBean.obtenerPorId(id).orElse(null);
        if(tipoProductoEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el TipoProducto con id" + id).build();
        }
        return Response.ok(tipoProductoEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(TipoProducto tipoProducto) throws URISyntaxException {
     if(tipoProducto.getNombre() == null || tipoProducto.getNombre() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     tipoProductoBean.insertar(tipoProducto);
     return Response.created(new URI(uriInfo + "/" + tipoProducto.getIdTipoProducto())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, TipoProducto tipoProducto){
     TipoProducto tipoProductoEncontrado = tipoProductoBean.obtenerPorId(id).orElse(null);
     if(tipoProductoEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el TipoProducto con id" + id).build();
     }
     tipoProductoEncontrado.setNombre(tipoProducto.getNombre());
     tipoProductoEncontrado.setActivo(tipoProducto.getActivo());
     tipoProductoEncontrado.setComentarios(tipoProducto.getComentarios());
     
     TipoProducto tipoProductoActualizado = tipoProductoBean.actualizar(tipoProductoEncontrado);
     
     return Response.ok(tipoProductoActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarTipoProducto(@PathParam("id") Long id){
     TipoProducto tipoProductoEncontrado = tipoProductoBean.obtenerPorId(id).orElse(null);
     if(tipoProductoEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el TipoProducto con id" + id).build();
     }
     
     tipoProductoBean.eliminar(tipoProductoEncontrado);
     return Response.ok().build();
     }
     
}
