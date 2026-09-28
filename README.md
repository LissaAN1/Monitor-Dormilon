# Taller #3: El Monitor Dormilón (Sistemas Operativos)

Solución al clásico problema de concurrencia **"El Monitor Dormilón"** empleando hilos en Java y semáforos (`java.util.concurrent.Semaphore`).

## Descripción del Problema

El departamento de CSI dispone de un monitor que ayuda a los estudiantes de algoritmos con sus tareas. 
* La oficina cuenta con una silla para el monitor, un computador y una silla de visita.
* Hay **3 sillas en el corredor** para los estudiantes que esperan.
* Si no hay estudiantes esperando, el monitor duerme una siesta.
* Si llega un estudiante y el monitor duerme, debe despertarlo.
* Si el monitor está ocupado, el estudiante espera en una silla del corredor. Si no hay sillas libres, regresa a la sala de cómputo y vuelve más tarde.

---

## Estructura del Proyecto

El proyecto está organizado de manera modular utilizando paquetes de Java para separar la lógica de los hilos y la sincronización:

```text
Monitor-Dormilon/
│
├── src/
│   └── com/
│       └── monitor/
│           ├── Main.java              # Clase principal que inicializa la simulación
│           ├── model/
│               ├── Estudiante.java    # Hilo que representa al estudiante
│               └── Monitor.java       # Hilo que representa al monitor
│           └── sync/
│               └── SalaMonitor.java   # Lógica compartida, semáforos y exclusión mutua
│
├── .gitignore                         # Archivos ignorados por Git
└── README.md                          # Documentación del proyecto
```

---

## Requisitos Previos

* **Java Development Kit (JDK)** instalado (versión 8 o superior recomendada).
* Entorno de desarrollo como **Visual Studio Code**, IntelliJ IDEA o terminal/consola.

---

## Instrucciones de Compilación y Ejecución

### Opción 1: Desde la consola (Terminal)

1. Ubícate en la carpeta raíz del proyecto (`Monitor-Dormilon`).
2. Compila todos los archivos fuente indicando el directorio de salida (`out`):
   ```bash
   javac src/com/monitor/sync/SalaMonitor.java src/com/monitor/model/Estudiante.java src/com/monitor/model/Monitor.java src/com/monitor/Main.java -d out
   ```
3. Ejecuta la clase principal:
   ```bash
   java -cp out com.monitor.Main
   ```

### Opción 2: Usando Visual Studio Code
1. Abre la carpeta raíz completa del proyecto (`Monitor-Dormilon`) en VS Code.
2. Asegúrate de tener instalada la extensión **Extension Pack for Java**.
3. Abre el archivo `Main.java` ubicado en `src/com/monitor/Main.java`.
4. Haz clic en el botón **Run** que aparece sobre el método `main` o presiona `F5`.

