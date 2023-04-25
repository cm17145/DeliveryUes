/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Factura;
import jakarta.enterprise.context.RequestScoped;

/**
 *
 * @author Luis
 */
@RequestScoped
public class FacturaBean extends AbstractDataAccess<Factura> {
    
    public FacturaBean() {
        super(Factura.class);
    }
    
}
