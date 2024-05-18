package uvg.edu.gt;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Graph graph = new Graph();

        try {
            graph.readGraphFromFile("hdt10\\src\\main\\resources\\guategrafo.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
            return;
        }

        FloydWarshall fw = new FloydWarshall(graph);
        fw.computeShortestPaths();
        fw.printSolution();

        int center = fw.findGraphCenter();

        while (true) {
            System.out.println("Opciones del programa:");
            System.out.println("1. Consultar la ruta más corta entre dos ciudades");
            System.out.println("2. Indicar el nombre de la ciudad que queda en el centro del grafo");
            System.out.println("3. Modificar el grafo");
            System.out.println("4. Finalizar el programa");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Ingrese la ciudad origen:");
                    String originCity = scanner.next();
                    System.out.println("Ingrese la ciudad destino:");
                    String destinationCity = scanner.next();
                    int origin = graph.getCityIndex(originCity);
                    int destination = graph.getCityIndex(destinationCity);

                    if (origin == -1 || destination == -1) {
                        System.out.println("Ciudad no válida.");
                        break;
                    }

                    List<Integer> path = fw.getShortestPath(origin, destination);
                    int distance = fw.getShortestPathDistance(origin, destination);

                    if (path.isEmpty()) {
                        System.out.println("No hay ruta disponible entre " + originCity + " y " + destinationCity);
                    } else {
                        System.out.print("La ruta más corta entre " + originCity + " y " + destinationCity + " es: ");
                        for (int node : path) {
                            System.out.print(graph.getCityName(node) + " ");
                        }
                        System.out.println("\nDistancia: " + distance);
                    }
                    break;
                case 2:
                    if (center != -1) {
                        System.out.println("El centro del grafo es: " + graph.getCityName(center));
                    } else {
                        System.out.println("No se encontró el centro del grafo.");
                    }
                    break;
                case 3:
                    System.out.println("1. Interrupción de tráfico entre un par de ciudades");
                    System.out.println("2. Establecer una conexión entre ciudades con valor de x KM de distancia");
                    int modifyOption = scanner.nextInt();
                    if (modifyOption == 1) {
                        System.out.println("Ingrese la ciudad origen:");
                        originCity = scanner.next();
                        System.out.println("Ingrese la ciudad destino:");
                        destinationCity = scanner.next();
                        origin = graph.getCityIndex(originCity);
                        destination = graph.getCityIndex(destinationCity);

                        if (origin == -1 || destination == -1) {
                            System.out.println("Ciudad no válida.");
                            break;
                        }

                        graph.removeEdge(origin, destination);
                    } else if (modifyOption == 2) {
                        System.out.println("Ingrese la ciudad origen:");
                        originCity = scanner.next();
                        System.out.println("Ingrese la ciudad destino:");
                        destinationCity = scanner.next();
                        System.out.println("Ingrese la distancia:");
                        int distanceValue = scanner.nextInt();

                        origin = graph.getCityIndex(originCity);
                        destination = graph.getCityIndex(destinationCity);

                        if (origin == -1 || destination == -1) {
                            System.out.println("Ciudad no válida.");
                            break;
                        }

                        graph.addEdge(origin, destination, distanceValue);
                    }
                    fw = new FloydWarshall(graph);
                    fw.computeShortestPaths();
                    fw.printSolution();
                    center = fw.findGraphCenter();
                    break;
                case 4:
                    System.out.println("Programa finalizado.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }    
}
