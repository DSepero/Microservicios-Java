package com.mycompany.testbusqueda;

import java.io.File;
import java.util.List;

public class BuscadorArchivos implements Runnable{

      private final List<File> directorios; // Lista sincronizada de directorios
    private final String nombreArchivo; // Nombre del archivo a buscar
    private final List<File> resultados; // Lista sincronizada para almacenar resultados

    public BuscadorArchivos(List<File> directorios, String nombreArchivo, List<File> resultados) {
        this.directorios = directorios;
        this.nombreArchivo = nombreArchivo;
        this.resultados = resultados;
    }

    @Override
    public void run() {
        while (true) {
            File carpeta;
            synchronized (directorios) {
                if (directorios.isEmpty()) {
                    break; // Termina cuando no quedan más directorios por procesar
                }
                carpeta = directorios.remove(0); // Toma un directorio de la lista
            }

            if (carpeta.isDirectory() && carpeta.canRead()) {
                File[] archivos = carpeta.listFiles();
                if (archivos != null) {
                    for (File archivo : archivos) {
                        if (archivo.isDirectory()) {
                            synchronized (directorios) {
                                directorios.add(archivo); // Agrega subdirectorios para ser procesados
                            }
                        } else if (archivo.isFile() && archivo.getName().toLowerCase().contains(nombreArchivo.toLowerCase())) {
                            synchronized (resultados) {
                                resultados.add(archivo); // Agrega el archivo encontrado a la lista
                            }
                        }
                    }
                }
            } else {
                System.err.println("No se puede acceder a: " + carpeta.getAbsolutePath());
            }
        }
    }
}
