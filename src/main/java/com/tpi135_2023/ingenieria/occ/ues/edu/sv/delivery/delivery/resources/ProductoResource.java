/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;


import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.ProductoBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Producto;
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
@Path("/producto")
@Produces(MediaType.APPLICATION_JSON)
public class ProductoResource {
    
    @Inject 
    private ProductoBean productoBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarProducto(){
    List<Producto> listaProducto = productoBean.listar();
    return Response.ok(listaProducto).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        Producto productoEncontrado = productoBean.obtenerPorId(id).orElse(null);
        if(productoEncontrado == null) {
            return Response.status(Response.Status.NOT_FOUND).header("mensaje", "No se encontro el producto con id" + id).build();
        }
        return Response.ok(productoEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(Producto producto) throws URISyntaxException {
     if(producto.getNombre() == null || producto.getNombre() == ""){
     return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(producto.getActivo()== null){
     return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(producto.getDescripcion()== null || producto.getDescripcion()== ""){
     return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     productoBean.insertar(producto);
     return Response.created(new URI(uriInfo + "/" + producto.getIdProducto())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, Producto producto){
     Producto productoEncontrado = productoBean.obtenerPorId(id).orElse(null);
     if(productoEncontrado == null){
        return Response.status(Response.Status.NOT_FOUND).header("mensaje", "No se encontro el producto con id" + id).build();
     }
     productoEncontrado.setNombre(producto.getNombre());
     productoEncontrado.setActivo(producto.getActivo());
     productoEncontrado.setDescripcion(producto.getDescripcion());
     
     Producto productoActualizado = productoBean.actualizar(productoEncontrado);
     
     return Response.ok(productoActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarComercio(@PathParam("id") Long id){
     Producto productoEncontrado = productoBean.obtenerPorId(id).orElse(null);
     if(productoEncontrado == null){
        return Response.status(Response.Status.NOT_FOUND).header("mensaje", "No se encontro el producto con id" + id).build();
     }
     
     productoBean.eliminar(productoEncontrado);
     return Response.ok().build();
     }
}