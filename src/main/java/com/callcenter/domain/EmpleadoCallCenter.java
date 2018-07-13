package com.callcenter.domain;

import com.callcenter.EmpleadoEstado;
import com.callcenter.Llamada;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;


public class EmpleadoCallCenter {

    private static final Logger logger = Logger.getLogger(EmpleadoCallCenter.class.getSimpleName());

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

    public  void setEstado(EmpleadoEstado estado) {
        this.estado = estado;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
