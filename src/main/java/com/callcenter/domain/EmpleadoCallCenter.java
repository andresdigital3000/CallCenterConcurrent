package com.callcenter.domain;

import com.callcenter.EmpleadoEstado;
import com.callcenter.Llamada;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedDeque;


public class EmpleadoCallCenter {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoCallCenter.class);

    private String nombre;
    private EmpleadoEstado estado;

    private ConcurrentLinkedDeque<Llamada> incomingCalls;


    public EmpleadoCallCenter(String nombre) {
        this.estado = EmpleadoEstado.AVAILABLE;
        this.incomingCalls = new ConcurrentLinkedDeque<>();
        this.nombre = nombre;
    }

    public synchronized EmpleadoEstado getEstado() {
        return estado;
    }

    //private synchronized void setEmployeeState(EmpleadoEstado estado) {
    public  void setEstado(EmpleadoEstado estado) {
        logger.info("Employee " + Thread.currentThread().getName() + " changes its state to " + estado);
        this.estado = estado;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    
    /**
     * Asumiendo que cada asesor pudiera tener una lista de llamadas asignada.
     * @param llamada 
     */
    public void atenderLlamada(Llamada llamada){
        this.setEstado(EmpleadoEstado.BUSY);
        this.esperarXsegundos(llamada.getDuracionEnSegundos()); 
        /*for (int i = 0; i < llamada.getPreguntas().size(); i++) { 
            logger.info("Atendiendo la pregunta " + (i + 1));
        }*/
        this.setEstado(EmpleadoEstado.AVAILABLE);
    }
    @Override
    public String toString() {
        return "EmpleadoCallCenter{" + "nombre=" + nombre + ", estado=" + estado + '}';
    }
    
    
    private void esperarXsegundos(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}
