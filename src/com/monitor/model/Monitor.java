package com.monitor.model;

import com.monitor.sync.SalaMonitor;

public class Monitor extends Thread {
    private SalaMonitor sala;

    public Monitor(SalaMonitor sala) {
        this.sala = sala;
    }

    @Override
    public void run() {
        sala.mostrarInicio();
        while (true) {
            sala.atenderSiguienteEstudiante();
        }
    }
}