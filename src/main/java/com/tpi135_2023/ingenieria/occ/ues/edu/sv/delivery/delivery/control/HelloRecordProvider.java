/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import java.io.Serializable;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources.HelloRecord;

/**
 *
 * @author Luis
 */
@Stateless
@LocalBean
public class HelloRecordProvider implements Serializable {

    private static final long serialVersionUID = 1L;

    public HelloRecord hello() {
        return new HelloRecord("Hello from Jakarta EE");
    }

}