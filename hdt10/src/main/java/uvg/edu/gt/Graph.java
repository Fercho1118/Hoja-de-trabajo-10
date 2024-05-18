/**
 * Clase Graph
 * Fernando Rueda - 23748
 * Clase que representa un grafo dirigido ponderado usando una matriz de adyacencia.
 */

package uvg.edu.gt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    private int V;
    private int[][] adjMatrix;
    public static final int INF = 99999;
    private Map<String, Integer> cityIndex;
    private String[] cityNames;
    private int currentIndex;

    /**
     * Constructor de la clase Graph.
     * Inicializa los atributos cityIndex y currentIndex.
     */
    public Graph() {
        this.cityIndex = new HashMap<>();
        this.currentIndex = 0;
    }

    /**
     * Inicializa la matriz de adyacencia con el tamaño especificado.
     * 
     * @param V Número de vértices del grafo.
     */
    private void initializeMatrix(int V) {
        this.V = V;
        this.adjMatrix = new int[V][V];
        this.cityNames = new String[V];
        for (int[] row : adjMatrix) {
            Arrays.fill(row, INF);
        }
        for (int i = 0; i < V; i++) {
            adjMatrix[i][i] = 0;
        }
    }

    /**
     * Añade una arista al grafo.
     * 
     * @param src Índice del vértice origen.
     * @param dest Índice del vértice destino.
     * @param weight Peso de la arista.
     */
    public void addEdge(int src, int dest, int weight) {
        adjMatrix[src][dest] = weight;
    }

    /**
     * Elimina una arista del grafo.
     * 
     * @param src Índice del vértice origen.
     * @param dest Índice del vértice destino.
     */
    public void removeEdge(int src, int dest) {
        adjMatrix[src][dest] = INF;
    }

    /**
     * Obtiene la matriz de adyacencia del grafo.
     * 
     * @return La matriz de adyacencia.
     */
    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    /**
     * Obtiene el número de vértices del grafo.
     * 
     * @return Número de vértices.
     */
    public int getNumVertices() {
        return V;
    }

    /**
     * Imprime la matriz de adyacencia del grafo.
     */
    public void printMatrix() {
        System.out.println("Matriz de adyacencia:");
        for (int[] row : adjMatrix) {
            for (int col : row) {
                if (col == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(col + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Lee un grafo desde un archivo.
     * 
     * @param fileName Nombre del archivo que contiene la definición del grafo.
     * @throws FileNotFoundException Si el archivo no se encuentra.
     */
    public void readGraphFromFile(String fileName) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(fileName));
        Set<String> cities = new HashSet<>();
        List<String[]> edges = new ArrayList<>();

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(" ");
            String city1 = parts[0];
            String city2 = parts[1];
            int weight = Integer.parseInt(parts[2]);
            cities.add(city1);
            cities.add(city2);
            edges.add(parts);
        }
        fileScanner.close();

        initializeMatrix(cities.size());

        for (String city : cities) {
            cityIndex.put(city, currentIndex);
            cityNames[currentIndex] = city;
            currentIndex++;
        }

        for (String[] edge : edges) {
            int src = cityIndex.get(edge[0]);
            int dest = cityIndex.get(edge[1]);
            int weight = Integer.parseInt(edge[2]);
            addEdge(src, dest, weight);
        }
    }

    /**
     * Obtiene el nombre de una ciudad dado su índice.
     * 
     * @param index Índice de la ciudad.
     * @return Nombre de la ciudad.
     */
    public String getCityName(int index) {
        return cityNames[index];
    }

    /**
     * Obtiene el índice de una ciudad dado su nombre.
     * 
     * @param name Nombre de la ciudad.
     * @return Índice de la ciudad, o -1 si no se encuentra.
     */
    public int getCityIndex(String name) {
        return cityIndex.getOrDefault(name, -1);
    }  
}
