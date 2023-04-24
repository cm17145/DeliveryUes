/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.ProductoComercioBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.ProductoComercio;
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
@Path("/productoComercio")
@Produces(MediaType.APPLICATION_JSON)
public class ProductoComercioResource {
    
    @Inject 
    private ProductoComercioBean productoComercioBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarProductoComercio(){
    List<ProductoComercio> listaProductoComercio = productoComercioBean.listar();
    return Response.ok(listaProductoComercio).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        ProductoComercio productoComercioEncontrado = productoComercioBean.obtenerPorId(id).orElse(null);
        if(productoComercioEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el ProductoComercio con id" + id).build();
        }
        return Response.ok(productoComercioEncontrado).build();
    }  
    
     /*@POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(ProductoComercio productoComercio) throws URISyntaxException {
     if(productoComercio.getActivo()== null || productoComercio.getActivo() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     productoComercioBean.insertar(productoComercio);
     return Response.created(new URI(uriInfo + "/" + productoComercio.getIdProductoComercio())).build();
    } */
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, ProductoComercio productoComercio){
     ProductoComercio productoComercioEncontrado = productoComercioBean.obtenerPorId(id).orElse(null);
     if(productoComercioEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el ProductoComercio con id" + id).build();
     }
     productoComercioEncontrado.setActivo(productoComercio.getActivo());
     productoComercioEncontrado.setFechaCreacion(productoComercio.getFechaCreacion());
     
     ProductoComercio productoComercioActualizado = productoComercioBean.actualizar(productoComercioEncontrado);
     
     return Response.ok(productoComercioActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarProductoComercio(@PathParam("id") Long id){
     ProductoComercio productoComercioEncontrado = productoComercioBean.obtenerPorId(id).orElse(null);
     if(productoComercioEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el ProductoComercio con id" + id).build();
     }
     
     productoComercioBean.eliminar(productoComercioEncontrado);
     return Response.ok().build();
     }
     
}
