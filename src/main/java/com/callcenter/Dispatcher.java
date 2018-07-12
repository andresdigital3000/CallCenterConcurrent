package com.callcenter;

import com.callcenter.domain.Director;
import com.callcenter.domain.EmpleadoCallCenter;
import com.callcenter.domain.Operador;
import com.callcenter.domain.Supervisor;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Dispatcher implements Runnable {

    private static final Logger logger = Logger.getLogger(Dispatcher.class.getSimpleName());

    public static final Integer MAX_HILOS = 10;

    private Boolean active;

    private List<EmpleadoCallCenter> empleados;

    private ConcurrentLinkedDeque<Llamada> llamadasEntrantes;

    public Dispatcher(ConcurrentLinkedDeque<Llamada> llamadasEntrantes, List<EmpleadoCallCenter> empleados) {
        this.llamadasEntrantes = llamadasEntrantes;
        this.empleados = empleados;
        active = true;

    }

    public Dispatcher() {
        this.llamadasEntrantes = new ConcurrentLinkedDeque<>();
        this.empleados = new ArrayList<>();
        active = true;

    }

    public synchronized Boolean getActive() {
        return active;
    }

    public synchronized EmpleadoCallCenter encontrarEmpleadoDisponible() {
        List<EmpleadoCallCenter> empleadosDisponibles = empleados.stream().filter(e -> e.getEstado() == EmpleadoEstado.AVAILABLE).collect(Collectors.toList());
        Optional<EmpleadoCallCenter> employee = empleadosDisponibles.stream().filter(e -> e instanceof Operador).findAny();
        if (!employee.isPresent()) {
            employee = empleadosDisponibles.stream().filter(e -> e instanceof Supervisor).findAny();
            if (!employee.isPresent()) {
                employee = empleadosDisponibles.stream().filter(e -> e instanceof Director).findAny();
                if (!employee.isPresent()) {
                    return null;
                }
            }
        }
        EmpleadoCallCenter empleadoAsignado = employee.get();
        return empleadoAsignado;
    }

    @Override
    public void run() {
        while (this.llamadasEntrantes != null && this.llamadasEntrantes.size() > 0) {
            if (this.llamadasEntrantes.isEmpty()) {
                continue;
            } else {
                EmpleadoCallCenter employee = this.encontrarEmpleadoDisponible();
                if (employee == null) {
                    continue;
                }
                Llamada llamada = this.llamadasEntrantes.poll();
                try {
                    employee.atenderLlamada(llamada);
                } catch (Exception e) {
                    logger.info(e.getMessage());
                    this.llamadasEntrantes.addFirst(llamada);
                }
            }
        }
    }

}
