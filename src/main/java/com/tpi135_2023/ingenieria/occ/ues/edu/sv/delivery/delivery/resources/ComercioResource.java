/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.ComercioBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Comercio;
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
@Path("/comercio")
@Produces(MediaType.APPLICATION_JSON)
public class ComercioResource {

    @Inject
    private ComercioBean comercioBean;

    @Context
    private UriInfo uriInfo;

    @GET
    public Response listarComercio() {
        List<Comercio> listaComercio = comercioBean.listar();
        return Response.ok(listaComercio).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id) {
        Comercio comercioEncontrado = comercioBean.obtenerPorId(id).orElse(null);
        if (comercioEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el comercio con id" + id).build();
        }
        return Response.ok(comercioEncontrado).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertar(Comercio comercio) throws URISyntaxException {
        if (comercio.getNombre() == null || comercio.getNombre() == "") {
            return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        } else if (comercio.getActivo() == null) {
            return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        } else if (comercio.getDescripcion() == null || comercio.getDescripcion() == "") {
            return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
        comercioBean.insertar(comercio);
        return Response.created(new URI(uriInfo + "/" + comercio.getIdComercio())).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, Comercio comercio) {
        Comercio comercioEncontrado = comercioBean.obtenerPorId(id).orElse(null);
        if (comercioEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el comercio con id" + id).build();
        }
        comercioEncontrado.setNombre(comercio.getNombre());
        comercioEncontrado.setActivo(comercio.getActivo());
        comercioEncontrado.setDescripcion(comercio.getDescripcion());

        Comercio comercioActualizado = comercioBean.actualizar(comercioEncontrado);

        return Response.ok(comercioActualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarComercio(@PathParam("id") Long id) {
        Comercio comercioEncontrado = comercioBean.obtenerPorId(id).orElse(null);
        if (comercioEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el comercio con id" + id).build();
        }

        comercioBean.eliminar(comercioEncontrado);
        return Response.ok().build();
    }

}
