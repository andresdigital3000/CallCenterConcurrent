/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter;

import static com.callcenter.Dispatcher.MAX_HILOS;
import com.callcenter.domain.Director;
import com.callcenter.domain.EmpleadoCallCenter;
import com.callcenter.domain.Operador;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author andresbedoya
 */
public class Main {
    public static void main(String[] args) {
        ExecutorService executorService;

        executorService = Executors.newFixedThreadPool(MAX_HILOS);
        
        ConcurrentLinkedDeque<Llamada> lstLlamadas = new ConcurrentLinkedDeque<>();
        lstLlamadas.add(new Llamada(2));
        lstLlamadas.add(new Llamada(2));
        lstLlamadas.add(new Llamada(2));
        
        List<EmpleadoCallCenter> lstEmpleados = new ArrayList();
        lstEmpleados.add(new Director("Andres"));
        lstEmpleados.add(new Operador("Carlos"));

        Runnable proceso1 = new Dispatcher(lstLlamadas, lstEmpleados);
        
        executorService.execute(proceso1);
        executorService.shutdown();

    }
    
}
