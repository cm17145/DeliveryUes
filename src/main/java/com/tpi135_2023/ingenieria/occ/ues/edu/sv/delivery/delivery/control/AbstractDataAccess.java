/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Luis
 */

@Transactional
@RequestScoped
public class AbstractDataAccess<T> {
    
    @PersistenceContext
    protected EntityManager em;
    
    protected Class<T> tipoEntidad;
    
    public AbstractDataAccess(Class<T> tipoEntidad){
    this.tipoEntidad = tipoEntidad;
    }
    
    public List<T> listar(){
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<T> query = cb.createQuery(tipoEntidad);
    Root<T> root = query.from(tipoEntidad);//SELECT * FROM tipoEntidad
    query.select(root);
    TypedQuery<T> typedQuery = em.createQuery(query);
    return typedQuery.getResultList();
    }
    
    public Optional<T> obtenerPorId(Long id){
    return Optional.ofNullable(em.find(tipoEntidad, id));
    }
    
    public void insertar(T entidad){
    em.persist(entidad);
    }
    
    public T actualizar(T entidad){
    return em.merge(entidad);
    }
    
    public void eliminar(T entidad){
    em.remove(em.contains(entidad) ? entidad: em.merge(entidad));
    }
    
}
