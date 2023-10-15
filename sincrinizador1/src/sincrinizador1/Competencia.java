package sincrinizador1;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Competencia implements Runnable {
    public Competidor competidor;
    public int numCompetencia;
    public CyclicBarrier barrier;
    public int totalCompetidores;

    public Competencia(Competidor competidor, int numCompetencia, CyclicBarrier barrier, int totalCompetidores) {
        this.competidor = competidor;
        this.numCompetencia = numCompetencia;
        this.barrier = barrier;
        this.totalCompetidores = totalCompetidores;
    }
    public void run() {
        Random random = new Random();
        int puntuacion = (int) (random.nextDouble(1000) * competidor.handicap);
        competidor.puntuacionTotal += puntuacion;
        System.out.println("Competidor: " + competidor.nombre + ", País: " + competidor.pais
                + ", Puntuación en competencia " + numCompetencia + ": " + puntuacion
                + ", Puntuación total: " + competidor.puntuacionTotal);
        try {
            competidor.Datos(competidor.nombre, competidor.pais, competidor.puntuacionTotal);
            barrier.await(); // Esperar a que todos los competidores terminen la competencia
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        if (competidor.puntuacionTotal >= totalCompetidores * 1000 * 4) { 
            // Si la puntuación total supera el 80% de la puntuación máxima posible
            System.out.println("Competidor " + competidor.nombre + " de " + competidor.pais + " ha terminado todas las competencias.");
        }
    }

}