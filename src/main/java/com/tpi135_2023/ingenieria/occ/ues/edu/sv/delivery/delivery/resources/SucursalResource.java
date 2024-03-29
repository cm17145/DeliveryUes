/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.SucursalBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Sucursal;
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
@Path("/sucursal")
@Produces(MediaType.APPLICATION_JSON)
public class SucursalResource {
    
    @Inject 
    private SucursalBean sucursalBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarSucursal(){
    List<Sucursal> listaSucursal = sucursalBean.listar();
    return Response.ok(listaSucursal).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        Sucursal sucursalEncontrado = sucursalBean.obtenerPorId(id).orElse(null);
        if(sucursalEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la Sucursal con id" + id).build();
        }
        return Response.ok(sucursalEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(Sucursal sucursal) throws URISyntaxException {
     if(sucursal.getNombre() == null || sucursal.getNombre() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(sucursal.getPathLogo()== null || sucursal.getPathLogo()== ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(sucursal.getIdDireccion()== null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     sucursalBean.insertar(sucursal);
     return Response.created(new URI(uriInfo + "/" + sucursal.getIdSucursal())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, Sucursal sucursal){
     Sucursal sucursalEncontrado = sucursalBean.obtenerPorId(id).orElse(null);
     if(sucursalEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la Sucursal con id" + id).build();
     }
     sucursalEncontrado.setNombre(sucursal.getNombre());
     sucursalEncontrado.setPathLogo(sucursal.getPathLogo());
     sucursalEncontrado.setIdDireccion(sucursal.getIdDireccion());
     
     Sucursal sucursalActualizado = sucursalBean.actualizar(sucursalEncontrado);
     
     return Response.ok(sucursalActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarSucursal(@PathParam("id") Long id){
     Sucursal sucursalEncontrado = sucursalBean.obtenerPorId(id).orElse(null);
     if(sucursalEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la Sucursal con id" + id).build();
     }
     
     sucursalBean.eliminar(sucursalEncontrado);
     return Response.ok().build();
     }
     
}
