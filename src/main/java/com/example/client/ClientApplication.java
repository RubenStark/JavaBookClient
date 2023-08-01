package com.example.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClientApplication.class, args);
    ClienteLibro.main(args);
  }
}

class ClienteLibro {

  private static final String BASE_URL = "http://localhost:8090/libros"; // Reemplaza esta URL con la dirección de tu servidor Spring

  public static void main(String[] args) {
    int opcion;

    do {
            System.out.println("Hola Libros!");
            System.out.println("Seleccione una opción: ");
            System.out.println("1. Agregar un libro");
            System.out.println("2. Obtener todos los libros");
            System.out.println("3. Obtener un libro por su ID");
            System.out.println("4. Actualizar un libro");
            System.out.println("5. Eliminar un libro");
            System.out.println("6. Salir");
            System.out.println("Opción: ");

            try {
                opcion = Integer.parseInt(System.console().readLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    agregarLibro();
                    break;
                case 2:
                    obtenerLibros();
                    break;
                case 3:
                    obtenerLibroPorId();
                    break;
                case 4:
                    actualizarLibro();
                    break;
                case 5:
                    eliminarLibro();
                    break;
                case 6:
                    System.out.println("Adiós!");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        } while (opcion != 6);

  }

  private static void actualizarLibro() {
    System.out.println("ID del libro a actualizar: ");
    Long idLibro = 0L;
    try {
      idLibro = Long.parseLong(System.console().readLine());
    } catch (NumberFormatException e) {
      System.out.println("ID invalido");
    }
    RestTemplate restTemplate = new RestTemplate();
    Libro libroEncontrado = restTemplate.getForObject(
      BASE_URL + "/" + idLibro,
      Libro.class
    );
    System.out.println("Libro encontrado por ID: " + libroEncontrado);
    System.out.println("Titulo: ");
    libroEncontrado.setTitulo(System.console().readLine());
    System.out.println("Autor: ");
    libroEncontrado.setAutor(System.console().readLine());
    System.out.println("Anio: ");
    try {
      libroEncontrado.setAnio(Integer.parseInt(System.console().readLine()));
    } catch (NumberFormatException e) {
      System.out.println("Anio invalido");
    }
    HttpEntity<Libro> requestBody = new HttpEntity<>(libroEncontrado);
    restTemplate.exchange(
      BASE_URL + "/" + idLibro,
      HttpMethod.PUT,
      requestBody,
      Libro.class
    );
    System.out.println("Libro actualizado");
  }

  private static void eliminarLibro() {
    System.out.println("ID del libro a eliminar: ");
    Long idLibro = 0L;
    try {
      idLibro = Long.parseLong(System.console().readLine());
    } catch (NumberFormatException e) {
      System.out.println("ID invalido");
    }
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.delete(BASE_URL + "/" + idLibro);
    System.out.println("Libro eliminado");
  }

  private static void obtenerLibroPorId() {
    System.out.println("ID del libro: ");
    Long idLibro = 0L;
    try {
      idLibro = Long.parseLong(System.console().readLine());
    } catch (NumberFormatException e) {
      System.out.println("ID invalido");
    }
    RestTemplate restTemplate = new RestTemplate();
    Libro libroEncontrado = restTemplate.getForObject(
      BASE_URL + "/" + idLibro,
      Libro.class
    );
    System.out.println("Libro encontrado por ID: " + libroEncontrado);
  }

  private static void obtenerLibros() {
    RestTemplate restTemplate = new RestTemplate();
    Libro[] libros = restTemplate.getForObject(BASE_URL, Libro[].class);
    System.out.println("Lista de libros:");
    for (Libro libro : libros) {
      System.out.println(libro);
    }
  }

  private static void agregarLibro() {
    Libro nuevoLibro = new Libro();

    System.out.println("Titulo: ");
    nuevoLibro.setTitulo(System.console().readLine());
    System.out.println("Autor: ");
    nuevoLibro.setAutor(System.console().readLine());
    System.out.println("Anio: ");
    try {
      nuevoLibro.setAnio(Integer.parseInt(System.console().readLine()));
    } catch (NumberFormatException e) {
      System.out.println("Anio invalido");
    }

    RestTemplate restTemplate = new RestTemplate();
    Libro libroAgregado = restTemplate.postForObject(
      BASE_URL,
      nuevoLibro,
      Libro.class
    );
    System.out.println("Libro agregado: " + libroAgregado);
  }
}
