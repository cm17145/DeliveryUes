/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.ClienteBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Cliente;
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
@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {
    
    @Inject 
    private ClienteBean clienteBean;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    public Response listarCliente(){
    List<Cliente> listaCliente = clienteBean.listar();
    return Response.ok(listaCliente).build();
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id){
        Cliente clienteEncontrado = clienteBean.obtenerPorId(id).orElse(null);
        if(clienteEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el cliente con id" + id).build();
        }
        return Response.ok(clienteEncontrado).build();
    }  
    
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response insertar(Cliente cliente) throws URISyntaxException {
     if(cliente.getNombres() == null || cliente.getNombres() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(cliente.getApellidos() == null || cliente.getApellidos() == ""){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(cliente.getIdDireccionActual()== null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else if(cliente.getFechaNacimiento()== null){
     return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
     else{clienteBean.insertar(cliente);
     return Response.created(new URI(uriInfo + "/" + cliente.getIdCliente())).build();
     }
    } 
      
     @PUT
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/{id}")
     public Response actualizar(@PathParam("id") Long id, Cliente cliente){
     Cliente clienteEncontrado = clienteBean.obtenerPorId(id).orElse(null);
     if(clienteEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el cliente con id" + id).build();
     }
     clienteEncontrado.setNombres(cliente.getNombres());
     clienteEncontrado.setApellidos(cliente.getApellidos());
     clienteEncontrado.setIdDireccionActual(cliente.getIdDireccionActual());
     clienteEncontrado.setFechaNacimiento(cliente.getFechaNacimiento());
     
     Cliente clienteActualizado = clienteBean.actualizar(clienteEncontrado);
     
     return Response.ok(clienteActualizado).build();
     }
     
     @DELETE
     @Path("/{id}")
     public Response eliminarCliente(@PathParam("id") Long id){
     Cliente clienteEncontrado = clienteBean.obtenerPorId(id).orElse(null);
     if(clienteEncontrado == null){
        return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el cliente con id" + id).build();
     }
     
     clienteBean.eliminar(clienteEncontrado);
     return Response.ok().build();
     }
     
}
