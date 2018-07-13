package com.callcenter.domain;

import com.callcenter.EmpleadoEstado;
import com.callcenter.Llamada;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

public class Empleado{

    private static final Logger logger = Logger.getLogger(Empleado.class.getSimpleName());

    private String nombre;
    private EmpleadoEstado employeeState;

    private ConcurrentLinkedDeque<Llamada> incomingCalls;


    public Empleado(String nombre) {
        this.employeeState = EmpleadoEstado.AVAILABLE;
        this.incomingCalls = new ConcurrentLinkedDeque<>();
        this.nombre = nombre;
    }

    public synchronized EmpleadoEstado getEmployeeState() {
        return employeeState;
    }

    //private synchronized void setEmployeeState(EmpleadoEstado employeeState) {
    public  void setEmployeeState(EmpleadoEstado employeeState) {
        this.employeeState = employeeState;
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

    

}
