/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.MenuComercioBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.MenuComercio;
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
@Path("/menuComercio")
@Produces(MediaType.APPLICATION_JSON)
public class MenuComercioResource {
    
    @Inject 
    private MenuComercioBean menuComercioBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarMenuComercio(){
    List<MenuComercio> listaMenuComercio = menuComercioBean.listar();
    return Response.ok(listaMenuComercio).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        MenuComercio menuComercioEncontrado = menuComercioBean.obtenerPorId(id).orElse(null);
        if(menuComercioEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el MenuComercio con id" + id).build();
        }
        return Response.ok(menuComercioEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(MenuComercio menuComercio) throws URISyntaxException {
     if(menuComercio.getNombre() == null || menuComercio.getNombre() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(menuComercio.getDescripcion()== null || menuComercio.getDescripcion()== ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(menuComercio.getPrecioBase()== null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     menuComercioBean.insertar(menuComercio);
     return Response.created(new URI(uriInfo + "/" + menuComercio.getIdMenu())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, MenuComercio menuComercio){
     MenuComercio menuComercioEncontrado = menuComercioBean.obtenerPorId(id).orElse(null);
     if(menuComercioEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el MenuComercio con id" + id).build();
     }
     menuComercioEncontrado.setNombre(menuComercio.getNombre());
     menuComercioEncontrado.setDescripcion(menuComercio.getDescripcion());
     menuComercioEncontrado.setPrecioBase(menuComercio.getPrecioBase());
     
     MenuComercio menuComercioActualizado = menuComercioBean.actualizar(menuComercioEncontrado);
     
     return Response.ok(menuComercioActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarMenuComercio(@PathParam("id") Long id){
     MenuComercio menuComercioEncontrado = menuComercioBean.obtenerPorId(id).orElse(null);
     if(menuComercioEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el MenuComercio con id" + id).build();
     }
     
     menuComercioBean.eliminar(menuComercioEncontrado);
     return Response.ok().build();
     }
     
}
