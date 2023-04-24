/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.TipoProductoTipoComercioBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.TipoProductoTipoComercio;
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
@Path("/tipoProductoTipoComercio")
@Produces(MediaType.APPLICATION_JSON)
public class TipoProductoTipoComercioResource {
    
    @Inject 
    private TipoProductoTipoComercioBean tipoProductoTipoComercioBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarTipoProductoTipoComercio(){
    List<TipoProductoTipoComercio> listaTipoProductoTipoComercio = tipoProductoTipoComercioBean.listar();
    return Response.ok(listaTipoProductoTipoComercio).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        TipoProductoTipoComercio tipoProductoTipoComercioEncontrado = tipoProductoTipoComercioBean.obtenerPorId(id).orElse(null);
        if(tipoProductoTipoComercioEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el cliente con id" + id).build();
        }
        return Response.ok(tipoProductoTipoComercioEncontrado).build();
    }  
    
     /*@POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(TipoProductoTipoComercio tipoProductoTipoComercio) throws URISyntaxException {
     if(tipoProductoTipoComercio.getActivo()== null || tipoProductoTipoComercio.getActivo()== ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     tipoProductoTipoComercioBean.insertar(tipoProductoTipoComercio);
     return Response.created(new URI(uriInfo + "/" + tipoProductoTipoComercio.getIdTipoProductoTipoComercio())).build();
    } */
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, TipoProductoTipoComercio tipoProductoTipoComercio){
     TipoProductoTipoComercio tipoProductoTipoComercioEncontrado = tipoProductoTipoComercioBean.obtenerPorId(id).orElse(null);
     if(tipoProductoTipoComercioEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el cliente con id" + id).build();
     }
     tipoProductoTipoComercioEncontrado.setActivo(tipoProductoTipoComercio.getActivo());
     tipoProductoTipoComercioEncontrado.setFechaCreacion(tipoProductoTipoComercio.getFechaCreacion());
     
     TipoProductoTipoComercio tipoProductoTipoComercioActualizado = tipoProductoTipoComercioBean.actualizar(tipoProductoTipoComercioEncontrado);
     
     return Response.ok(tipoProductoTipoComercioActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarTipoProductoTipoComercio(@PathParam("id") Long id){
     TipoProductoTipoComercio tipoProductoTipoComercioEncontrado = tipoProductoTipoComercioBean.obtenerPorId(id).orElse(null);
     if(tipoProductoTipoComercioEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el cliente con id" + id).build();
     }
     
     tipoProductoTipoComercioBean.eliminar(tipoProductoTipoComercioEncontrado);
     return Response.ok().build();
     }
     
}
