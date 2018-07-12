package com.callcenter;

import com.callcenter.domain.Director;
import com.callcenter.domain.EmpleadoCallCenter;
import com.callcenter.domain.Operador;
import com.callcenter.domain.Supervisor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * AtencionLlamada
 */
public class AtencionLlamada {

    private static final Logger logger = Logger.getLogger(AtencionLlamada.class.getSimpleName());

    public EmpleadoCallCenter encontrarEmpleadoDisponible(Collection<EmpleadoCallCenter> lstEmpleados) {
        logger.info("lstEmpleados" + lstEmpleados);
        System.out.println("Buscando empleados dispontibles");
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
        return employee.get();
    }

}
