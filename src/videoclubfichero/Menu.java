/*
 * Clase de intercambio de información con el usuario (vista)
 */
package videoclubfichero;

import java.util.ArrayList;
import utilidades.EntradaDatos;
import utilidades.Fichero;

/**
 *
 * @author mfontana
 */
public class Menu {

    private ListaPeliculas misPeliculas;
    private Fichero miFichero;

    public Menu() {
        // Le indicamos el nombre del fichero
        miFichero = new Fichero("videoclub.xml");
        // Cargo las películas del fichero
        misPeliculas = (ListaPeliculas) miFichero.leer();
        // Si no hay fichero todavía
        if (misPeliculas == null) {
            // Inicializo la lista como una lista nueva (vacía)
            misPeliculas = new ListaPeliculas();
        }
        int opcion;
        do {
            mostrarMenu();
            opcion = EntradaDatos.pedirEntero("Escoge una opción");
            switch (opcion) {
                case 1:
                    altaPelicula();
                    break;
                case 2:
                    listadoPeliculas();
                    break;
                case 3:
                    peliculasGenero();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    modificarPelicula();
                    break;
                case 7:
                    borrarPelicula();
                    break;
                case 0:
                    System.out.println("Hasta la proxima!");
                    break;
                default:
                    System.out.println("Opción incorrecta.");
            }

        } while (opcion != 0);
    }

    private void borrarPelicula() {
        String codigo = EntradaDatos.pedirCadena("Código de la película que quieres borrar");
        Pelicula p = misPeliculas.peliculaPorCodigo(codigo);
        if (p == null) {
            System.out.println("No existe ninguna película con ese código.");
        } else {
            String confirmacion;
            do {
                System.out.println("Datos de la película");
                System.out.println(p);
                confirmacion = EntradaDatos.pedirCadena("¿Estás seguro de que quieres borrar la película? (S/N)");
                if (confirmacion.equalsIgnoreCase("s")) {
                    misPeliculas.borrarPelicula(p);
                    miFichero.grabar(misPeliculas);
                    System.out.println("Película borrada.");
                } else if (confirmacion.equalsIgnoreCase("n")) {
                    System.out.println("Vale, pues no la borramos.");
                } else {
                    System.out.println("Respuesta incorrecta. Debes indicar S o N.");
                }
            } while (!confirmacion.equalsIgnoreCase("s") && !confirmacion.equalsIgnoreCase("n"));
        }
    }

    private void modificarPelicula() {
        // Pedimos el código de la película que quiere modificar
        String codigo = EntradaDatos.pedirCadena("Código de la película que quieres modificar");
        Pelicula p = misPeliculas.peliculaPorCodigo(codigo);
        if (p == null) {
            System.out.println("No existe ninguna película con ese código.");
        } else {
            // Podemos modificar la película
            // Mostramos los datos al usuario para que escoja qué modificar
            System.out.println("Datos de la película con código " + p.getCodigo());
            System.out.println("1. Título: " + p.getTitulo());
            System.out.println("2. Duración: " + p.getDuracion());
            System.out.println("3. Género: " + p.getGenero());
            System.out.println("4. Valoración: " + p.getValoracion());
            System.out.print("5. Visto / no visto: ");
            if (p.isVisto()) {
                System.out.println("Película vista.");
            } else {
                System.out.println("Película por ver.");
            }
            int dato = EntradaDatos.pedirEntero("¿Qué dato quieres cambiar?");
            switch (dato) {
                case 1:
                    String titulo = EntradaDatos.pedirCadena("Nuevo título");
                    p.setTitulo(titulo);
                    System.out.println("Título modificado.");
                    break;
                case 2:
                    int duracion;
                    do {
                        duracion = EntradaDatos.pedirEntero("Nueva duración");
                        if (duracion < 1) {
                            System.out.println("La duración no puede ser menor de 1");
                        } else {
                            p.setDuracion(duracion);
                            System.out.println("Duración modificada.");
                        }
                    } while (duracion < 1);
                    break;
                case 3:
                    String genero = EntradaDatos.pedirCadena("Nuevo género");
                    p.setGenero(genero);
                    System.out.println("Genero modificado.");
                    break;
                case 4:
                    int valoracion;
                    do {
                        valoracion = EntradaDatos.pedirEntero("Nueva valoración");
                        if (valoracion < 0 || valoracion > 10) {
                            System.out.println("Incorrecta. Debe estar entre 0 y 10.");
                        } else {
                            p.setValoracion(valoracion);
                            System.out.println("Valoración modificada.");
                        }
                    } while (valoracion < 0 || valoracion > 10);
                    break;
                case 5:
                    String pregunta;
                    // Si la película estaba como vista
                    if (p.isVisto()) {
                        System.out.println("Habías indicado que habías visto la película");
                        pregunta = "¿Quieres indicar que no la has visto (S/N)?";
                    } else {
                        pregunta = "¿Has visto la película (S/N)?";
                    }
                    String respuesta;
                    do {
                        respuesta = EntradaDatos.pedirCadena(pregunta);
                        if (respuesta.equalsIgnoreCase("s")) {
                            p.setVisto(!p.isVisto()); // Ponemos lo contrario de lo que había
                            System.out.println("Dato modificado.");
                        } else if (respuesta.equalsIgnoreCase("n")) {
                            System.out.println("Entonces no modificamos el dato. A ver si te aclaras!!");
                        } else {
                            System.out.println("Repuesta incorrecta. Debes indicar S o N.");
                        }
                    } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
                    break;
                default:
                    System.out.println("Nº de dato incorrecto (1-5). A ver si leemos!!!");
            }
            // Grabamos en disco los cambios efectuados
            miFichero.grabar(misPeliculas);
        }
    }

    private void peliculasGenero() {
        // Cantidad de películas que tenemos
        int cantidad = misPeliculas.cantidad();
        if (cantidad == 0) {
            System.out.println("No hay películas para mostrar.");
        } else {
            // Pedimos al usuario género del que quiere ver las películas
            String genero = EntradaDatos.pedirCadena("¿De qué género quieres ver las películas?");
            // variable para saber si se ha encontrado alguna pelicula
            boolean encontrado = false;
            for (int i = 0; i < cantidad; i++) {
                Pelicula actual = misPeliculas.obtenerPelicula(i);
                if (genero.equalsIgnoreCase(actual.getGenero())) {
                    System.out.println(actual);
                    encontrado = true;
                }
            }
            if (!encontrado) {                              // encontrado == false
                System.out.println("No tenemos películas del género indicado.");
            }
        }
    }

    // Opción B que no utilizo pero dejo para que veáis otro ejemplo
    private void peliculasGeneroObcionB() {
        String genero = EntradaDatos.pedirCadena("¿De qué genero quieres ver las películas?");
        // Lista auxiliar con el resultado de la consulta
        ArrayList<Pelicula> lasPelisDelGenero = misPeliculas.peliculasPorGenero(genero);
        if (lasPelisDelGenero.isEmpty()) {
            System.out.println("No hay películas del género indicado");
        } else {
            System.out.println("Listado de películas del género: " + genero);
            for (Pelicula p : lasPelisDelGenero) {
                System.out.println(p);
            }
        }
    }

    private void listadoPeliculas() {
        // Cantidad de películas que hay en misPeliculas
        int cantidad = misPeliculas.cantidad();
        // Si no hay películas doy msg
        if (cantidad == 0) {
            System.out.println("No hay películas");
        } else {
            System.out.println("***** LISTADO DE PELÍCULAS *****");
            // Recorro todas las películas para mostrarlas
            for (int i = 0; i < cantidad; i++) {
                // Obtenemos la película que está en la posición i
                Pelicula actual = misPeliculas.obtenerPelicula(i);
                // Imprimimos datos por pantallas (toString está sobre escrito)
                System.out.println(actual);
            }
        }
    }

    private void altaPelicula() {
        boolean existe = false;
        String codigo;
        do {
            codigo = EntradaDatos.pedirCadena("Código de la película: ");
            // Creamos una película auxiliar con el código introducido por el usuario
            // Para comprobar si ya hay una en la lista con el mismo código
            Pelicula auxiliar = new Pelicula();
            auxiliar.setCodigo(codigo);
            existe = misPeliculas.existe(auxiliar);
            if (existe) {
                System.out.println("Ya existe una película con ese código.");
            }
        } while (existe);                           // existe == true
        String titulo = EntradaDatos.pedirCadena("Título");
        int duracion;
        do {
            duracion = EntradaDatos.pedirEntero("Duración (en minutos)");
            if (duracion < 1) {
                System.out.println("La duración no puede ser menor a un minuto");
            }
        } while (duracion < 1);
        String genero = EntradaDatos.pedirCadena("Género");
        int valoracion;
        do {
            valoracion = EntradaDatos.pedirEntero("Valoración");
            if (valoracion < 0 || valoracion > 10) {
                System.out.println("La valoración tiene que estar entre 0 y 10.");
            }
        } while (valoracion < 0 || valoracion > 10);
        boolean visto = false;
        String respuesta = "";
        do {
            respuesta = EntradaDatos.pedirCadena("¿La has visto (S/N))?");
            if (respuesta.equalsIgnoreCase("s")) {
                visto = true;
            } else if (respuesta.equalsIgnoreCase("n")) {
                visto = false;
            } else {
                System.out.println("Respuesta incorrecta.");
            }
        } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
        // Creamos la película
        Pelicula p = new Pelicula(codigo, titulo, duracion, genero, valoracion, visto);
        // Añadimos la película a la lista
        misPeliculas.altaPelicula(p);
        // Grabamos en fichero
        miFichero.grabar(misPeliculas);
    }

    private void mostrarMenu() {
        System.out.println("Gestión de películas");
        System.out.println("1. Nueva película");
        System.out.println("2. Listado de todas las películas");
        System.out.println("3. Listado de películas de un género determinado");
        System.out.println("4. Película favorita");
        System.out.println("5. Totales");
        System.out.println("6. Modificar película");
        System.out.println("7. Borrar película.");
        System.out.println("0. Salir");
    }
}
