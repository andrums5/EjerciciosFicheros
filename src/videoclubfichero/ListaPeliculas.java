/*
 * JavaBean que implementa el ArrayList<Pelicula>
 */
package videoclubfichero;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author mfontana
 */
public class ListaPeliculas implements Serializable {

    private ArrayList<Pelicula> lista;

    public ListaPeliculas() {
        lista = new ArrayList<>();
    }
    
    // Método para añadir una Película a la lista
    public void altaPelicula(Pelicula p) {
        lista.add(p);
    }
    
    // Método para borrar una Película de la lista
    public void borrarPelicula(Pelicula p) {
        lista.remove(p);
    }
    
    // Método que devuelve la cantidad de elementos que tiene el array
    public int cantidad() {
        return lista.size();
    }
    
    // Método que devuelve la película que hay en una posición pasada
    // como parámetro
    public Pelicula obtenerPelicula(int posicion) {
        return lista.get(posicion);
    }
    
    // Método que a partir de un código devuelve la película de la lista
    // Si no existe devolvemos null
    public Pelicula peliculaPorCodigo(String codigo) {
        for (Pelicula p : lista) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }
    
    // Opción B del método anterior usando los métodos de ArrayList
    public Pelicula peliculaPorCodigoObcionB(String codigo) {
        // Creo una película auxiliar con el código
        Pelicula aux = new Pelicula();
        aux.setCodigo(codigo);
        int posicion = lista.indexOf(aux);
        if (posicion == -1) { // Significa q la película no existe
            return null;
        } else {
            return lista.get(posicion);
        }
    }
    
    // Método que devuelve un ArrayList<Pelicula> de un género pasado
    // como parámetro
    public ArrayList<Pelicula> peliculasPorGenero(String genero) {
        ArrayList<Pelicula> pelisPorGenero = new ArrayList<>();
        for (Pelicula p : lista) {
            if (p.getGenero().equalsIgnoreCase(genero)) {
                pelisPorGenero.add(p);
            }
        }
        return pelisPorGenero;
    }
    
    // Función que comprueba si existe una película con el mismo código 
    // que p (parámetro) en el ArrayList
    public boolean existe(Pelicula p) {
//        for (Pelicula aux : lista) {
//            if (p.getCodigo().equalsIgnoreCase(aux.getCodigo())) {
//                return true;
//            }
//        }
//        return false;
        return lista.contains(p);
    }
    
    public ArrayList<Pelicula> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Pelicula> lista) {
        this.lista = lista;
    }

}
