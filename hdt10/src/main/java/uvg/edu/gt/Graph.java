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

    public Graph() {
        this.cityIndex = new HashMap<>();
        this.currentIndex = 0;
    }

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

    public void addEdge(int src, int dest, int weight) {
        adjMatrix[src][dest] = weight;
    }

    public void removeEdge(int src, int dest) {
        adjMatrix[src][dest] = INF;
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    public int getNumVertices() {
        return V;
    }

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

    public String getCityName(int index) {
        return cityNames[index];
    }

    public int getCityIndex(String name) {
        return cityIndex.getOrDefault(name, -1);
    }  
}
