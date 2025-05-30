package core;

import java.util.*;

public class FlightRouteGraph {
    private final HashMap<String, HashMap<String, Integer>> adjacencyListMap;

    public FlightRouteGraph() {
        adjacencyListMap = new HashMap<>();
    }

    public void addRoute(String source, String destination, int distance) {
        adjacencyListMap.putIfAbsent(source, new HashMap<>());
        adjacencyListMap.get(source).put(destination, distance);
        adjacencyListMap.putIfAbsent(destination, new HashMap<>());
        adjacencyListMap.get(destination).put(source, distance);
    }

    public ArrayList<String> getShortestPaths(String source, String destination) {
        HashMap<String, Integer> distance = new HashMap<>();
        HashMap<String, String> previous = new HashMap<>();
        PriorityQueue<Node> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(n -> n.distance)
        );

        for (String city : adjacencyListMap.keySet()) {
            distance.put(city, Integer.MAX_VALUE);
        }
        distance.put(source, 0);
        minHeap.add(new Node(source, 0));

        while (!minHeap.isEmpty()) {
            Node currentNode = minHeap.poll();
            String currentCity = currentNode.city;

            if (currentNode.distance > distance.get(currentCity)) {
                continue;
            }

            for (Map.Entry<String, Integer> neighbor : adjacencyListMap.get(currentCity).entrySet()) {
                String neighborCity = neighbor.getKey();
                int newDistance = distance.get(currentCity) + neighbor.getValue();

                if (newDistance < distance.get(neighborCity)) {
                    distance.put(neighborCity, newDistance);
                    previous.put(neighborCity, currentCity);
                    minHeap.add(new Node(neighborCity, newDistance));
                }
            }
        }

        ArrayList<String> path = new ArrayList<>();
        String current = destination;
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);
        return path.size() >= 2 && path.get(0).equals(source) ? path : new ArrayList<>();
    }

    // New method to find all possible routes
    public ArrayList<ArrayList<String>> getAllRoutes(String source, String destination) {
        ArrayList<ArrayList<String>> allRoutes = new ArrayList<>();
        ArrayList<String> currentRoute = new ArrayList<>();
        HashSet<String> visited = new HashSet<>();
        currentRoute.add(source);
        findAllRoutesDFS(source, destination, visited, currentRoute, allRoutes);
        return allRoutes;
    }

    private void findAllRoutesDFS(String current, String destination, HashSet<String> visited,
                                  ArrayList<String> currentRoute, ArrayList<ArrayList<String>> allRoutes) {
        if (current.equals(destination)) {
            allRoutes.add(new ArrayList<>(currentRoute));
            return;
        }

        visited.add(current);
        HashMap<String, Integer> neighbors = adjacencyListMap.getOrDefault(current, new HashMap<>());
        for (String neighbor : neighbors.keySet()) {
            if (!visited.contains(neighbor)) {
                currentRoute.add(neighbor);
                findAllRoutesDFS(neighbor, destination, visited, currentRoute, allRoutes);
                currentRoute.removeLast();
            }
        }
        visited.remove(current);
    }

    private static class Node {
        String city;
        int distance;

        public Node(String city, int distance) {
            this.city = city;
            this.distance = distance;
        }
    }
}