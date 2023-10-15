package sincrinizador1;

public class Competidor {
    String nombre;
    String pais;
    double handicap;
    int puntuacionTotal;

    public Competidor(String nombre, String pais, double handicap, int puntuacionTotal) {
        this.nombre = nombre;
        this.pais = pais;
        this.handicap = handicap;
        this.puntuacionTotal = puntuacionTotal;
    }
    public String[] Datos(String nombre, String pais,int puntuacionTotal){
         String[] datos = new String[3];
         datos[0]= nombre;
         datos[1]= pais;
         datos[2]= String.valueOf(puntuacionTotal);
         System.out.println(datos[0]+ datos[2]);
         //Toca agarra este son los datos de cada competencia sin ordenar
         //retorna un arreglo tipo string debes que agregarlo a cada elemento de cada agreglo
        return datos;
    }
   
}