package uvg.edu.gt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Graph {
    private final int V;
    private final int[][] adjMatrix;
    public static final int INF = 99999;
    private final Map<String, Integer> cityIndex;
    private final String[] cityNames;
    private int currentIndex;

    public Graph(int V) {
        this.V = V;
        adjMatrix = new int[V][V];
        cityIndex = new HashMap<>();
        cityNames = new String[V];
        currentIndex = 0;

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
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(" ");
            String city1 = parts[0];
            String city2 = parts[1];
            int weight = Integer.parseInt(parts[2]);

            if (!cityIndex.containsKey(city1)) {
                cityIndex.put(city1, currentIndex);
                cityNames[currentIndex] = city1;
                currentIndex++;
            }
            if (!cityIndex.containsKey(city2)) {
                cityIndex.put(city2, currentIndex);
                cityNames[currentIndex] = city2;
                currentIndex++;
            }

            int src = cityIndex.get(city1);
            int dest = cityIndex.get(city2);
            addEdge(src, dest, weight);
        }
        fileScanner.close();
    }

    public String getCityName(int index) {
        return cityNames[index];
    }

    public int getCityIndex(String name) {
        return cityIndex.getOrDefault(name, -1);
    }  
}
