/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control;

import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.entity.Repartidor;
import jakarta.enterprise.context.RequestScoped;

/**
 *
 * @author Luis
 */
@RequestScoped
public class RepartidorBean extends AbstractDataAccess<Repartidor> {
    
    public RepartidorBean() {
        super(Repartidor.class);
    }
    
}
