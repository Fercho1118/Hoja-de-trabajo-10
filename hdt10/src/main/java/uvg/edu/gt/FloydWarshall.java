/**
 * Clase FloydWarshall
 * Fernando Rueda - 23748
 * Clase que implementa el algoritmo de Floyd-Warshall para encontrar las rutas más cortas en un grafo dirigido ponderado.
 */

package uvg.edu.gt;

import java.util.ArrayList;
import java.util.List;

public class FloydWarshall {
    private int[][] dist;
    private int[][] next;
    private int V;

    /**
     * Constructor de la clase FloydWarshall.
     * 
     * @param graph El grafo sobre el cual se ejecutará el algoritmo de Floyd-Warshall.
     */
    public FloydWarshall(Graph graph) {
        this.V = graph.getNumVertices();
        this.dist = new int[V][V];
        this.next = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (graph.getAdjMatrix()[i][j] != Graph.INF) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
                dist[i][j] = graph.getAdjMatrix()[i][j];
            }
        }
    }

    /**
     * Computa las rutas más cortas entre todos los pares de vértices usando el algoritmo de Floyd-Warshall.
     */
    public void computeShortestPaths() {
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];  
                    }
                }
            }
        }
    }

    /**
     * Imprime la matriz de distancias más cortas entre cada par de vértices.
     */
    public void printSolution() {
        System.out.println("Matriz de distancias más cortas entre cada par de vértices:");
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (dist[i][j] == Graph.INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(dist[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Encuentra el centro del grafo, que es el vértice con la menor excentricidad máxima.
     * 
     * @return El índice del vértice que es el centro del grafo.
     */
    public int findGraphCenter() {
        int minEccentricity = Graph.INF;
        int center = -1;

        for (int i = 0; i < V; i++) {
            int maxDist = 0;
            for (int j = 0; j < V; j++) {
                if (dist[i][j] > maxDist) {
                    maxDist = dist[i][j];
                }
            }
            if (maxDist < minEccentricity) {
                minEccentricity = maxDist;
                center = i;
            }
        }

        if (center != -1) {
            System.out.println("El centro del grafo es el vértice: " + center + " con excentricidad: " + minEccentricity);
        } else {
            System.out.println("No se encontró el centro del grafo.");
        }
        return center;
    }  

    /**
     * Obtiene la ruta más corta entre dos vértices.
     * 
     * @param src Índice del vértice origen.
     * @param dest Índice del vértice destino.
     * @return Una lista de enteros que representa la ruta más corta desde src hasta dest.
     */
    public List<Integer> getShortestPath(int src, int dest) {
        List<Integer> path = new ArrayList<>();
        if (next[src][dest] == -1) {
            return path;
        }
        path.add(src);
        while (src != dest) {
            src = next[src][dest];
            path.add(src);
        }
        return path;
    }
    
    /**
     * Obtiene la distancia de la ruta más corta entre dos vértices.
     * 
     * @param src Índice del vértice origen.
     * @param dest Índice del vértice destino.
     * @return La distancia de la ruta más corta entre src y dest.
     */
    public int getShortestPathDistance(int src, int dest) {
        return dist[src][dest];
    }
}
