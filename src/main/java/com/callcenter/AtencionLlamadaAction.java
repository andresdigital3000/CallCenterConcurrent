package com.callcenter;

import com.callcenter.domain.Director;
import com.callcenter.domain.EmpleadoCallCenter;
import com.callcenter.domain.Operador;
import com.callcenter.domain.Supervisor;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AtencionLlamadaAction extends RecursiveAction {

    private static final Logger logger = Logger.getLogger(Dispatcher.class.getSimpleName());
    List<EmpleadoCallCenter> lstEmpleados;
    List<Llamada> lstLlamadas;
    private final int threshold;
    private int start;
    private int end;

    public AtencionLlamadaAction(List<EmpleadoCallCenter> lstEmpleados, List<Llamada> lstLlamadas, int start, int end, int threshold) {
        this.threshold = threshold;
        this.lstEmpleados = lstEmpleados;
        this.lstLlamadas = lstLlamadas;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        logger.info("Efectuando computo");
        if (end - start < threshold) {
            for (int i = start; i <= end; i++) {
                lstLlamadas.get(i).setEmpleadoAsignado(encontrarEmpleadoDisponible(lstLlamadas.get(i)));
                System.out.println("Completado de " + start + ", al: " + end);
            }
        } else {
            int midway = (end - start) / 2 + start;
            AtencionLlamadaAction r1 = new AtencionLlamadaAction(lstEmpleados, lstLlamadas, start, midway, threshold);
            AtencionLlamadaAction r2 = new AtencionLlamadaAction(lstEmpleados, lstLlamadas, midway + 1, end, threshold);
            invokeAll(r1, r2);
        }

    }

    public EmpleadoCallCenter encontrarEmpleadoDisponible(Llamada llamada) {
        List<EmpleadoCallCenter> empleadosDisponibles = lstEmpleados.stream().filter(e -> e.getEstado() == EmpleadoEstado.AVAILABLE).collect(Collectors.toList());
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
        empleadoAsignado.attend(llamada);
        return empleadoAsignado;
    }
}
