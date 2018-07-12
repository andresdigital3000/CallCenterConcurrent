package com.callcenter;


import com.callcenter.domain.EmpleadoCallCenter;
import com.callcenter.domain.Preguntas;
import java.util.List;


public class Llamada {
    List<Preguntas> preguntas;


    private final Integer duracionEnSegundos;
    private EmpleadoCallCenter empleadoAsignado;

    public EmpleadoCallCenter getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    public void setEmpleadoAsignado(EmpleadoCallCenter empleadoAsignado) {
        this.empleadoAsignado = empleadoAsignado;
    }

    public Llamada(Integer durationInSeconds) {
        this.duracionEnSegundos = durationInSeconds;
    }

    public Integer getDuracionEnSegundos() {
        return duracionEnSegundos;
    }

    public List<Preguntas> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Preguntas> preguntas) {
        this.preguntas = preguntas;
    }

    
    @Override
    public String toString() {
        return "Llamada{" + "duracionEnSegundos=" + duracionEnSegundos + ", empleadoAsignado=" + empleadoAsignado + '}';
    }
}
