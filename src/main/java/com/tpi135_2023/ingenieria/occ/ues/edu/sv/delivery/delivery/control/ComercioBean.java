/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Comercio;
import jakarta.enterprise.context.RequestScoped;

/**
 *
 * @author Cornejo
 */
@RequestScoped
public class ComercioBean extends AbstractDataAccess<Comercio> {
    
    public ComercioBean() {
        super(Comercio.class);
    }
    
}
