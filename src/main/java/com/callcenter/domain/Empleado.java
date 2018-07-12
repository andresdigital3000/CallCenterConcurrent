package com.callcenter.domain;

import com.callcenter.EmpleadoEstado;
import com.callcenter.Llamada;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

/**
 * Models the Empleado Domain Objects
 */
public class Empleado implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Empleado.class);

    private String nombre;
    private EmpleadoEstado employeeState;

    private ConcurrentLinkedDeque<Llamada> incomingCalls;

    private ConcurrentLinkedDeque<Llamada> attendedCalls;

    public Empleado(String nombre) {
        this.employeeState = EmpleadoEstado.AVAILABLE;
        this.incomingCalls = new ConcurrentLinkedDeque<>();
        this.attendedCalls = new ConcurrentLinkedDeque<>();
        this.nombre = nombre;
    }

    public synchronized EmpleadoEstado getEmployeeState() {
        return employeeState;
    }

    //private synchronized void setEmployeeState(EmpleadoEstado employeeState) {
    public  void setEmployeeState(EmpleadoEstado employeeState) {
        logger.info("Employee " + Thread.currentThread().getName() + " changes its state to " + employeeState);
        this.employeeState = employeeState;
    }


    public synchronized List<Llamada> getAttendedCalls() {
        return new ArrayList<>(attendedCalls);
    }

    /**
     * Queues a call to be attended by the employee
     *
     * @param call call to be attended
     */
    public synchronized void attend(Llamada call) {
        logger.info("Employee " + Thread.currentThread().getName() + " queues a call of " + call.getDuracionEnSegundos() + " seconds");
        this.incomingCalls.add(call);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Empleado{" + "nombre=" + nombre + ", employeeState=" + employeeState + '}';
    }

    
    /**
     * This is the method that runs on the thread.
     * If the incoming calls queue is not empty, then it changes its state from AVAILABLE to BUSY, takes the call
     * and when it finishes it changes its state from BUSY back to AVAILABLE. This allows a Thread Pool to decide
     * to dispatch another call to another employee.
     */
    @Override
    public void run() {
        logger.info("Employee " + Thread.currentThread().getName() + " starts to work");
        while (true) {
            if (!this.incomingCalls.isEmpty()) {
                Llamada call = this.incomingCalls.poll();
                this.setEmployeeState(EmpleadoEstado.BUSY);
                logger.info("Employee " + Thread.currentThread().getName() + " starts working on a call of " + call.getDuracionEnSegundos() + " seconds");
                try {
                    TimeUnit.SECONDS.sleep(call.getDuracionEnSegundos());
                } catch (InterruptedException e) {
                    logger.error("Employee " + Thread.currentThread().getName() + " was interrupted and could not finish call of " + call.getDuracionEnSegundos() + " seconds");
                } finally {
                    this.setEmployeeState(EmpleadoEstado.AVAILABLE);
                }
                this.attendedCalls.add(call);
                logger.info("Employee " + Thread.currentThread().getName() + " finishes a call of " + call.getDuracionEnSegundos() + " seconds");
            }
        }
    }

}
