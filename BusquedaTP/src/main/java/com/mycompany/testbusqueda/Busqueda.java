package com.mycompany.testbusqueda;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Busqueda {

    public static void main(String[] args) {
        
        Scanner teclado = new Scanner(System.in);

        System.out.println("Que archivo desea buscar?");
        String archivo = teclado.nextLine();
        System.out.println("En que carpeta o directorio desea buscar?");
        String directorio = teclado.nextLine();

        File directorioInicial = new File(directorio);
        List<File> directorios = Collections.synchronizedList(new ArrayList<>());
        List<File> resultados = Collections.synchronizedList(new ArrayList<>());

        if (directorioInicial.exists() && directorioInicial.isDirectory()) {
            directorios.add(directorioInicial); // Agrega el directorio inicial a la lista

            // Crear y lanzar los hilos
            int numHilos = 5; // Número de hilos
            Thread[] hilos = new Thread[numHilos];
            for (int i = 0; i < numHilos; i++) {
                hilos[i] = new Thread(new BuscadorArchivos(directorios, archivo, resultados));
                hilos[i].start();
            }

            // Esperar a que todos los hilos terminen
            for (Thread hilo : hilos) {
                try {
                    hilo.join();
                } catch (InterruptedException e) {
                    System.err.println("Error al esperar un hilo: " + e.getMessage());
                }
            }

            // Mostrar resultados al final
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron archivos con el nombre especificado.");
            } else {
                System.out.println("Archivos encontrados:");
                for (File archivoEncontrado : resultados) {
                    System.out.println(archivoEncontrado.getAbsolutePath());
                }
            }
        } else {
            System.err.println("El directorio especificado no existe o no es válido.");
        }
    }
}