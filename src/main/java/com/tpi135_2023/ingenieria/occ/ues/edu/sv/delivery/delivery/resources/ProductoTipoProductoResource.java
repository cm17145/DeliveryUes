/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.ProductoTipoProductoBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.ProductoTipoProducto;
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
@Path("/productoTipoProducto")
@Produces(MediaType.APPLICATION_JSON)
public class ProductoTipoProductoResource {
    
    @Inject 
    private ProductoTipoProductoBean productoTipoProductoBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarProductoTipoProducto(){
    List<ProductoTipoProducto> listaProductoTipoProducto = productoTipoProductoBean.listar();
    return Response.ok(listaProductoTipoProducto).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        ProductoTipoProducto productoTipoProductoEncontrado = productoTipoProductoBean.obtenerPorId(id).orElse(null);
        if(productoTipoProductoEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el ProductoTipoProducto con id" + id).build();
        }
        return Response.ok(productoTipoProductoEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(ProductoTipoProducto productoTipoProducto) throws URISyntaxException {
     if(productoTipoProducto.getActivo()== null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(productoTipoProducto.getFechaCreacion()== null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     productoTipoProductoBean.insertar(productoTipoProducto);
     return Response.created(new URI(uriInfo + "/" + productoTipoProducto.getProducto())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, ProductoTipoProducto productoTipoProducto){
     ProductoTipoProducto productoTipoProductoEncontrado = productoTipoProductoBean.obtenerPorId(id).orElse(null);
     if(productoTipoProductoEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el ProductoTipoProducto con id" + id).build();
     }
     productoTipoProductoEncontrado.setActivo(productoTipoProducto.getActivo());
     productoTipoProductoEncontrado.setFechaCreacion(productoTipoProducto.getFechaCreacion());
     
     ProductoTipoProducto productoTipoProductoActualizado = productoTipoProductoBean.actualizar(productoTipoProductoEncontrado);
     
     return Response.ok(productoTipoProductoActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarProductoTipoProducto(@PathParam("id") Long id){
     ProductoTipoProducto productoTipoProductoEncontrado = productoTipoProductoBean.obtenerPorId(id).orElse(null);
     if(productoTipoProductoEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el ProductoTipoProducto con id" + id).build();
     }
     
     productoTipoProductoBean.eliminar(productoTipoProductoEncontrado);
     return Response.ok().build();
     }
     
}
