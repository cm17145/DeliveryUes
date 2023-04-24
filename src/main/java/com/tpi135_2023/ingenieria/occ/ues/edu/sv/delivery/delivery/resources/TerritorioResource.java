/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.TerritorioBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Territorio;
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
@Path("/territorio")
@Produces(MediaType.APPLICATION_JSON)
public class TerritorioResource {
    
    @Inject 
    private TerritorioBean territorioBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarTerritorio(){
    List<Territorio> listaTerritorio = territorioBean.listar();
    return Response.ok(listaTerritorio).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        Territorio territorioEncontrado = territorioBean.obtenerPorId(id).orElse(null);
        if(territorioEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el Territorio con id" + id).build();
        }
        return Response.ok(territorioEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(Territorio territorio) throws URISyntaxException {
     if(territorio.getNombre() == null || territorio.getNombre() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     territorioBean.insertar(territorio);
     return Response.created(new URI(uriInfo + "/" + territorio.getIdTerritorio())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, Territorio territorio){
     Territorio territorioEncontrado = territorioBean.obtenerPorId(id).orElse(null);
     if(territorioEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el Territorio con id" + id).build();
     }
     territorioEncontrado.setNombre(territorio.getNombre());
     territorioEncontrado.setTextoVisible(territorio.getTextoVisible());
     territorioEncontrado.setHijosObligatorios(territorio.getHijosObligatorios());
     
     Territorio territorioActualizado = territorioBean.actualizar(territorioEncontrado);
     
     return Response.ok(territorioActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarTerritorio(@PathParam("id") Long id){
     Territorio territorioEncontrado = territorioBean.obtenerPorId(id).orElse(null);
     if(territorioEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el Territorio con id" + id).build();
     }
     
     territorioBean.eliminar(territorioEncontrado);
     return Response.ok().build();
     }
     
}
