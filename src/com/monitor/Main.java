package com.monitor;

import com.monitor.model.Estudiante;
import com.monitor.model.Monitor;
import com.monitor.sync.SalaMonitor;

public class Main {
    public static void main(String[] args) {
        int numeroEstudiantes = 5;
        int numeroSillas = 3;

        SalaMonitor sala = new SalaMonitor(numeroSillas, numeroEstudiantes);

        Monitor monitor = new Monitor(sala);
        monitor.start();

        for (int i = 1; i <= numeroEstudiantes; i++) {
            Estudiante estudiante = new Estudiante(i, sala);
            estudiante.start();
        }
    }
}