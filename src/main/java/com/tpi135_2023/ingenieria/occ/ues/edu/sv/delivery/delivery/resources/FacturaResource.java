/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.FacturaBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Factura;
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
@Path("/factura")
@Produces(MediaType.APPLICATION_JSON)
public class FacturaResource {
    
    @Inject 
    private FacturaBean facturaBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarFactura(){
    List<Factura> listaFactura = facturaBean.listar();
    return Response.ok(listaFactura).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        Factura facturaEncontrado = facturaBean.obtenerPorId(id).orElse(null);
        if(facturaEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la factura con id" + id).build();
        }
        return Response.ok(facturaEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(Factura factura) throws URISyntaxException {
     if(factura.getFechaEmision()== null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(factura.getAnulada()== null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(factura.getObservaciones() == null || factura.getObservaciones() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     facturaBean.insertar(factura);
     return Response.created(new URI(uriInfo + "/" + factura.getNumeroFactura())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, Factura factura){
     Factura facturaEncontrado = facturaBean.obtenerPorId(id).orElse(null);
     if(facturaEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la factura con id" + id).build();
     }
     facturaEncontrado.setFechaEmision(factura.getFechaEmision());
     facturaEncontrado.setAnulada(factura.getAnulada());
     facturaEncontrado.setObservaciones(factura.getObservaciones());
     
     Factura facturaActualizado = facturaBean.actualizar(facturaEncontrado);
     
     return Response.ok(facturaActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarFactura(@PathParam("id") Long id){
     Factura facturaEncontrado = facturaBean.obtenerPorId(id).orElse(null);
     if(facturaEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro la factura con id" + id).build();
     }
     
     facturaBean.eliminar(facturaEncontrado);
     return Response.ok().build();
     }
     
}
