/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.PagoBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Pago;
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
@Path("/pago")
@Produces(MediaType.APPLICATION_JSON)
public class PagoResource {
    
    @Inject 
    private PagoBean pagoBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarPago(){
    List<Pago> listaPago = pagoBean.listar();
    return Response.ok(listaPago).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        Pago pagoEncontrado = pagoBean.obtenerPorId(id).orElse(null);
        if(pagoEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el Pago con id" + id).build();
        }
        return Response.ok(pagoEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(Pago pago) throws URISyntaxException {
     if(pago.getTipoPago() == null || pago.getTipoPago() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(pago.getMonto()== null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(pago.getReferencia()== null || pago.getReferencia()== ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(pago.getEstado()== null || pago.getEstado()== ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     pagoBean.insertar(pago);
     return Response.created(new URI(uriInfo + "/" + pago.getIdPago())).build();
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, Pago pago){
     Pago pagoEncontrado = pagoBean.obtenerPorId(id).orElse(null);
     if(pagoEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el Pago con id" + id).build();
     }
     pagoEncontrado.setTipoPago(pago.getTipoPago());
     pagoEncontrado.setMonto(pago.getMonto());
     pagoEncontrado.setReferencia(pago.getReferencia());
     pagoEncontrado.setEstado(pago.getEstado());
     
     Pago pagoActualizado = pagoBean.actualizar(pagoEncontrado);
     
     return Response.ok(pagoActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarPago(@PathParam("id") Long id){
     Pago pagoEncontrado = pagoBean.obtenerPorId(id).orElse(null);
     if(pagoEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el Pago con id" + id).build();
     }
     
     pagoBean.eliminar(pagoEncontrado);
     return Response.ok().build();
     }
     
}
