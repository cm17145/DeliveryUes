/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Orden;
import jakarta.enterprise.context.RequestScoped;

/**
 *
 * @author Luis
 */
@RequestScoped
public class OrdenBean extends AbstractDataAccess<Orden> {
    
    public OrdenBean() {
        super(Orden.class);
    }
    
}
