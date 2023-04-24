/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.RepartidorBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Repartidor;
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
@Path("/repartidor")
@Produces(MediaType.APPLICATION_JSON)
public class RepartidorResource {
    
    @Inject 
    private RepartidorBean repartidorBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarRepartidor(){
    List<Repartidor> listaRepartidor = repartidorBean.listar();
    return Response.ok(listaRepartidor).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        Repartidor repartidorEncontrado = repartidorBean.obtenerPorId(id).orElse(null);
        if(repartidorEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el cliente con id" + id).build();
        }
        return Response.ok(repartidorEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(Repartidor repartidor) throws URISyntaxException {
     if(repartidor.getNombres() == null || repartidor.getNombres() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     repartidorBean.insertar(repartidor);
     return Response.created(new URI(uriInfo + "/" + repartidor.getIdRepartidor())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, Repartidor repartidor){
     Repartidor repartidorEncontrado = repartidorBean.obtenerPorId(id).orElse(null);
     if(repartidorEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el cliente con id" + id).build();
     }
     repartidorEncontrado.setNombres(repartidor.getNombres());
     repartidorEncontrado.setApellidos(repartidor.getApellidos());
     repartidorEncontrado.setTipoLicencia(repartidor.getTipoLicencia());
     repartidorEncontrado.setFechaNacimiento(repartidor.getFechaNacimiento());
     repartidorEncontrado.setActivo(repartidor.getActivo());
     repartidorEncontrado.setObservaciones(repartidor.getObservaciones());
     
     Repartidor repartidorActualizado = repartidorBean.actualizar(repartidorEncontrado);
     
     return Response.ok(repartidorActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarRepartidor(@PathParam("id") Long id){
     Repartidor repartidorEncontrado = repartidorBean.obtenerPorId(id).orElse(null);
     if(repartidorEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el cliente con id" + id).build();
     }
     
     repartidorBean.eliminar(repartidorEncontrado);
     return Response.ok().build();
     }
     
}
