/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.DireccionBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Direccion;
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
@Path("/Direccion")
@Produces(MediaType.APPLICATION_JSON)
public class DireccionResource {
    
    @Inject 
    private DireccionBean direccionBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarDireccion(){
    List<Direccion> listaDireccion = direccionBean.listar();
    return Response.ok(listaDireccion).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        Direccion direccionEncontrado = direccionBean.obtenerPorId(id).orElse(null);
        if(direccionEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la direccion con id" + id).build();
        }
        return Response.ok(direccionEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(Direccion direccion) throws URISyntaxException {
     if(direccion.getDireccion() == null || direccion.getDireccion() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(direccion.getLatitud() == null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(direccion.getLongitud() == null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(direccion.getReferencias()== null || direccion.getReferencias()== ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     direccionBean.insertar(direccion);
     return Response.created(new URI(uriInfo + "/" + direccion.getIdDireccion())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, Direccion direccion){
     Direccion direccionEncontrado = direccionBean.obtenerPorId(id).orElse(null);
     if(direccionEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la direccion con id" + id).build();
     }
     direccionEncontrado.setDireccion(direccion.getDireccion());
     direccionEncontrado.setLatitud(direccion.getLatitud());
     direccionEncontrado.setLongitud(direccion.getLongitud());
     direccionEncontrado.setReferencias(direccion.getReferencias());
     
     Direccion comercioActualizado = direccionBean.actualizar(direccionEncontrado);
     
     return Response.ok(comercioActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarComercio(@PathParam("id") Long id){
     Direccion direccionEncontrado = direccionBean.obtenerPorId(id).orElse(null);
     if(direccionEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el comercio con id" + id).build();
     }
     
     direccionBean.eliminar(direccionEncontrado);
     return Response.ok().build();
     }
     
}