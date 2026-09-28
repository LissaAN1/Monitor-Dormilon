package com.monitor.model;

import com.monitor.sync.SalaMonitor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Estudiante extends Thread {
    private int id;
    private SalaMonitor sala;
    private Semaphore llamadoMonitor;
    private Semaphore finAtencion;

    public Estudiante(int id, SalaMonitor sala) {
        this.id = id;
        this.sala = sala;
        this.llamadoMonitor = new Semaphore(0);
        this.finAtencion = new Semaphore(0);
    }

    public int getIdEstudiante() {
        return id;
    }

    public Semaphore getLlamadoMonitor() {
        return llamadoMonitor;
    }

    public Semaphore getFinAtencion() {
        return finAtencion;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int tiempoProgramando = ThreadLocalRandom.current().nextInt(2000, 5001);
                System.out.println("Estudiante " + id + " esta programando por " + tiempoProgramando + " ms.");
                Thread.sleep(tiempoProgramando);

                sala.solicitarAyuda(this);

            } catch (InterruptedException e) {
                System.out.println("Estudiante " + id + " fue interrumpido.");
                return;
            }
        }
    }
}