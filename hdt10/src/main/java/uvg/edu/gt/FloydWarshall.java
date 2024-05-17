package uvg.edu.gt;

public class FloydWarshall {
    private int[][] dist;
    private int V;

    public FloydWarshall(Graph graph) {
        this.V = graph.getNumVertices();
        this.dist = new int[V][V];
        for (int i = 0; i < V; i++) {
            System.arraycopy(graph.getAdjMatrix()[i], 0, dist[i], 0, V);
        }
    }

    public void computeShortestPaths() {
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
    }

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

        System.out.println("El centro del grafo es el vértice: " + center + " con excentricidad: " + minEccentricity);
        return center;
    }  
}
