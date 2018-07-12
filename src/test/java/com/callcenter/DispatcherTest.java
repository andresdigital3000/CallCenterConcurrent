/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter;

import com.callcenter.domain.Director;
import com.callcenter.domain.EmpleadoCallCenter;
import com.callcenter.domain.Operador;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andresbedoya
 */
public class DispatcherTest {
    
    public DispatcherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }



    /**
     * Test of getActive method, of class Dispatcher.
     */
    @Test
    public void testGetActive() {
        System.out.println("getActive");
        Dispatcher instance = new Dispatcher();
        Boolean expResult = true;
        Boolean result = instance.getActive();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of encontrarEmpleadoDisponible method, of class Dispatcher.
     */
    @Test
    public void testEncontrarEmpleadoDisponible() {
        ConcurrentLinkedDeque<Llamada> lstLlamadas = new ConcurrentLinkedDeque<>();
        lstLlamadas.add(new Llamada(2));
        lstLlamadas.add(new Llamada(2));
        lstLlamadas.add(new Llamada(2));
        
        List<EmpleadoCallCenter> lstEmpleados = new ArrayList();
        lstEmpleados.add(new Director("Andres"));
        lstEmpleados.add(new Operador("Carlos"));

        Runnable proceso1 = new Dispatcher(lstLlamadas, lstEmpleados);
        assertNotNull(lstLlamadas);
        assertNotNull(lstEmpleados);
    }
    

}
