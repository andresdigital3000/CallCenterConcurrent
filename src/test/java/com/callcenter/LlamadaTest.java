/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter;

import java.util.List;
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
public class LlamadaTest {
    
    public LlamadaTest() {
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
     * Test of getDurationInSeconds method, of class Llamada.
     */
    @Test
    public void testGetDurationInSeconds() {
        System.out.println("getDurationInSeconds");
        Integer expResult = 0;
        Llamada instance = new Llamada(expResult);
        Integer result = instance.getDuracionEnSegundos();
        assertEquals(expResult, result);
        assertNotNull(result);
        assertTrue(result > 0);
    }


}
