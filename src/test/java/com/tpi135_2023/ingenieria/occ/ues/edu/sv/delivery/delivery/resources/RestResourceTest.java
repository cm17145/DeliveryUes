/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.resources;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import com.tpi135_2023.ingenieria.occ.ues.edu.sv.delivery.delivery.control.HelloRecordProvider;

/**
 *
 * @author Luis
 */
public class RestResourceTest {
    
    public RestResourceTest() {
    }

    /**
     * Test of hello method, of class RestResource.
     */
    @org.junit.jupiter.api.Test
    public void testHello() {
        System.out.println("hello");
        HelloRecordProvider hrpMOCK = Mockito.mock(HelloRecordProvider.class);
        HelloRecord expResult = new HelloRecord("Hello from Jakarta EE");
        Mockito.when(hrpMOCK.hello()).thenReturn(expResult);
        RestResource cut = new RestResource();
        cut.hrp = hrpMOCK;
        HelloRecord result = cut.hello();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
}
