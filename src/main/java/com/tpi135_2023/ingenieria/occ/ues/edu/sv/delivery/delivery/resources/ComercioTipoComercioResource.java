/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.ComercioTipoComercioBean;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.ComercioTipoComercio;
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
@Path("/comercioTipoComercio")
@Produces(MediaType.APPLICATION_JSON)
public class ComercioTipoComercioResource {

    @Inject
    private ComercioTipoComercioBean comercioTipoComercioBean;

    @Context
    private UriInfo uriInfo;

    @GET
    public Response listarComerciosTipoComercio() {
        List<ComercioTipoComercio> listaComerciosTipoComercio = comercioTipoComercioBean.listar();
        return Response.ok(listaComerciosTipoComercio).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id) {
        ComercioTipoComercio comercioTipoComercioEncontrado = comercioTipoComercioBean.obtenerPorId(id).orElse(null);
        if (comercioTipoComercioEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el comercioTipoComercio con id" + id).build();
        }
        return Response.ok(comercioTipoComercioEncontrado).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertar(ComercioTipoComercio comercioTipoComercio) throws URISyntaxException {
        if (comercioTipoComercio.getActivo() == null) {
            return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        } else if (comercioTipoComercio.getFechaCreacion() == null) {
            return Response.status(Status.BAD_REQUEST).header("mensaje", "El objeto enviado no es valido").build();
        }
        comercioTipoComercioBean.insertar(comercioTipoComercio);
        return Response.created(new URI(uriInfo + "/" + comercioTipoComercio.getTipoComercio())).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, ComercioTipoComercio comercioTipoComercio) {
        ComercioTipoComercio comercioTipoComercioEncontrado = comercioTipoComercioBean.obtenerPorId(id).orElse(null);
        if (comercioTipoComercioEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el comercioTipoComercio con id" + id).build();
        }
        comercioTipoComercioEncontrado.setActivo(comercioTipoComercio.getActivo());
        comercioTipoComercioEncontrado.setFechaCreacion(comercioTipoComercio.getFechaCreacion());

        ComercioTipoComercio comercioTipoComercioActualizado = comercioTipoComercioBean.actualizar(comercioTipoComercioEncontrado);

        return Response.ok(comercioTipoComercioActualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarComercioTipoComercio(@PathParam("id") Long id) {
        ComercioTipoComercio comercioTipoComercioEncontrado = comercioTipoComercioBean.obtenerPorId(id).orElse(null);
        if (comercioTipoComercioEncontrado == null) {
            return Response.status(Status.NOT_FOUND).header("mensaje", "No se encontro el comercioTipoComercio con id" + id).build();
        }

        comercioTipoComercioBean.eliminar(comercioTipoComercioEncontrado);
        return Response.ok().build();
    }

}
