/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.TipoComercioBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.TipoComercio;
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
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 *
 * @author Luis
 */
@RequestScoped
@Path("/TipoComercio")
@Produces(MediaType.APPLICATION_JSON)
public class TipoComercioResource {
    
    @Inject 
    private TipoComercioBean tipoComercioBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarComercios(){
    List<TipoComercio> listaTipoComercios = tipoComercioBean.listar();
    return Response.ok(listaTipoComercios).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        TipoComercio tipoComercioEncontrado = tipoComercioBean.obtenerPorId(id).orElse(null);
        if(tipoComercioEncontrado == null) {
            return Response.status(Response.Status.NOT_FOUND).header("mensaje", "No se encontro el tipoComercio con id" + id).build();
        }
        return Response.ok(tipoComercioEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(TipoComercio tipoComercio) throws URISyntaxException {
     if(tipoComercio.getNombre() == null || tipoComercio.getNombre() == ""){
     return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     tipoComercioBean.insertar(tipoComercio);
     return Response.created(new URI(uriInfo + "/" + tipoComercio.getIdTipoComercio())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, TipoComercio tipoComercio){
     TipoComercio tipoComercioEncontrado = tipoComercioBean.obtenerPorId(id).orElse(null);
     if(tipoComercioEncontrado == null){
        return Response.status(Response.Status.NOT_FOUND).header("mensaje", "No se encontro el tipoComercio con id" + id).build();
     }
     tipoComercioEncontrado.setNombre(tipoComercio.getNombre());
     tipoComercioEncontrado.setActivo(tipoComercio.getActivo());
     tipoComercioEncontrado.setComentarios(tipoComercio.getComentarios());
     
     TipoComercio tipoComercioActualizado = tipoComercioBean.actualizar(tipoComercioEncontrado);
     
     return Response.ok(tipoComercioActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarTipoComercio(@PathParam("id") Long id){
     TipoComercio tipoComercioEncontrado = tipoComercioBean.obtenerPorId(id).orElse(null);
     if(tipoComercioEncontrado == null){
        return Response.status(Response.Status.NOT_FOUND).header("mensaje", "No se encontro el tipoComercio con id" + id).build();
     }
     
     tipoComercioBean.eliminar(tipoComercioEncontrado);
     return Response.ok().build();
     }
     
}
