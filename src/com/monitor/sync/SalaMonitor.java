package com.monitor.sync;

import com.monitor.model.Estudiante;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class SalaMonitor {
    private int totalSillas;
    private int sillasOcupadas;
    private boolean monitorDormido;
    private int numeroEstudiantes;

    private Queue<Estudiante> colaEspera;

    private Semaphore mutex;
    private Semaphore estudiantesEsperando;

    public SalaMonitor(int totalSillas, int numeroEstudiantes) {
        this.totalSillas = totalSillas;
        this.numeroEstudiantes = numeroEstudiantes;
        this.sillasOcupadas = 0;
        this.monitorDormido = true;
        this.colaEspera = new LinkedList<>();
        this.mutex = new Semaphore(1);
        this.estudiantesEsperando = new Semaphore(0);
    }

    public void mostrarInicio() {
        System.out.println("==============================================");
        System.out.println("       SIMULACION DEL MONITOR DORMILON");
        System.out.println("==============================================");
        System.out.println("Numero de estudiantes: " + numeroEstudiantes);
        System.out.println("Sillas en el corredor: " + totalSillas);
        System.out.println("==============================================");
    }

    public void solicitarAyuda(Estudiante estudiante) {
        try {
            mutex.acquire();

            if (monitorDormido && colaEspera.isEmpty()) {
                monitorDormido = false;
                colaEspera.add(estudiante);

                System.out.println("Estudiante " + estudiante.getIdEstudiante() + " encontro al monitor dormido y lo desperto.");
                System.out.println("Estudiante " + estudiante.getIdEstudiante() + " entra directamente a recibir ayuda.");

                estudiantesEsperando.release();
                mutex.release();

                estudiante.getLlamadoMonitor().acquire();
                estudiante.getFinAtencion().acquire();

                System.out.println("Estudiante " + estudiante.getIdEstudiante() + " termino de recibir ayuda y vuelve a programar.");
            } else {
                if (sillasOcupadas < totalSillas) {
                    sillasOcupadas++;
                    colaEspera.add(estudiante);

                    System.out.println("Estudiante " + estudiante.getIdEstudiante() + " se sienta a esperar en el corredor.");
                    System.out.println("Sillas ocupadas: " + sillasOcupadas + "/" + totalSillas);

                    estudiantesEsperando.release();
                    mutex.release();

                    estudiante.getLlamadoMonitor().acquire();
                    estudiante.getFinAtencion().acquire();

                    System.out.println("Estudiante " + estudiante.getIdEstudiante() + " termino de recibir ayuda y vuelve a programar.");
                } else {
                    System.out.println("Estudiante " + estudiante.getIdEstudiante() + " no encontro silla libre y regresa a programar.");
                    mutex.release();
                }
            }

        } catch (InterruptedException e) {
            System.out.println("Error con el estudiante " + estudiante.getIdEstudiante());
            Thread.currentThread().interrupt();
        }
    }

    public void atenderSiguienteEstudiante() {
        try {
            estudiantesEsperando.acquire();

            mutex.acquire();

            Estudiante estudiante = colaEspera.poll();

            if (estudiante != null) {
                if (sillasOcupadas > 0) {
                    sillasOcupadas--;
                }

                monitorDormido = false;

                System.out.println("Monitor empieza a atender al estudiante " + estudiante.getIdEstudiante() + ".");
                System.out.println("Sillas ocupadas: " + sillasOcupadas + "/" + totalSillas);
            }

            mutex.release();

            if (estudiante != null) {
                estudiante.getLlamadoMonitor().release();

                int tiempoAtencion = ThreadLocalRandom.current().nextInt(2000, 4001);
                Thread.sleep(tiempoAtencion);

                System.out.println("Monitor termino de atender al estudiante " + estudiante.getIdEstudiante() + ".");
                estudiante.getFinAtencion().release();
            }

            mutex.acquire();

            if (colaEspera.isEmpty()) {
                monitorDormido = true;
                System.out.println("No hay estudiantes esperando. El monitor se duerme.");
            }

            mutex.release();

        } catch (InterruptedException e) {
            System.out.println("Error en el monitor.");
            Thread.currentThread().interrupt();
        }
    }
}