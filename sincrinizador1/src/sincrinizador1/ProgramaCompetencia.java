package sincrinizador1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ProgramaCompetencia {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3); // Ajustado para tu número de competidores

        List<Competidor> competidores = obtenerCompetidoresDesdeArchivo("competidores.txt");
        
        for (int i = 0; i < 5; i++) {
            System.out.println("Iniciando competencia " + (i + 1));
            List<Thread> threads = new ArrayList<>();
            for (Competidor competidor : competidores) {
                Thread thread = new Thread(new Competencia(competidor, i + 1, barrier, competidores.size()));
                threads.add(thread);
                thread.start();
            }
            try {
                barrier.await(); // Esperar a que todos los hilos estén listos
                System.out.println("Todos los competidores han completado la competencia " + (i + 1) + "\n");
                // Ordenar a los competidores por puntuación total de mayor a menor
                Collections.sort(competidores, Comparator.comparingInt((Competidor c) -> c.puntuacionTotal).reversed());
                // Retornar los datos en forma de arreglo

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        // Mostrar puntuaciones finales y el país de cada competidor
        System.out.println("Puntuaciones finales:");
        for (Competidor competidor : competidores) {
            System.out.println("Competidor: " + competidor.nombre + ", País: " + competidor.pais
                    + ", Puntuación total: " + competidor.puntuacionTotal);
        }
        String[] datosCompetidores = obtenerDatosCompetidores(competidores);
        // Chi aqui pinta el arreglo de los competidores
        pintarDatosCompetidores(datosCompetidores);
        //Ejemplo de uno por uno
        System.out.println(datosCompetidores[1]);
    }
    public static void pintarDatosCompetidores(String[] datosCompetidores) {
        System.out.println("Datos de los competidores:");
        for (String datos : datosCompetidores) {
            System.out.println(datos);
        }
        System.out.println();
    }

    public static List<Competidor> obtenerCompetidoresDesdeArchivo(String nombreArchivo) {
        List<Competidor> competidores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String nombre = partes[0];
                    String pais = partes[1];
                    double handicap = Double.parseDouble(partes[2]);
                    competidores.add(new Competidor(nombre, pais, handicap, 0));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return competidores;
    }

    public static String[] obtenerDatosCompetidores(List<Competidor> competidores) {
        String[] datosCompetidores = new String[competidores.size()];
        for (int i = 0; i < competidores.size(); i++) {
            Competidor competidor = competidores.get(i);
            datosCompetidores[i] = competidor.nombre + "," + competidor.pais + "," + competidor.puntuacionTotal;
        }
        // Aqui los devuelve los datos de los competidores ya final ya despues de todo y ordenados
        return datosCompetidores;
    }
}