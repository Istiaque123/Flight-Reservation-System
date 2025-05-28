package core;

import java.util.*;

public class FlightRouteGraph {

    private HashMap<String, HashMap<String, Integer>> adjacencyListMap;

    public FlightRouteGraph(){
        adjacencyListMap = new HashMap<>();
    }

    public void addRoute(String source, String destination, int distance){
        adjacencyListMap.putIfAbsent(source, new HashMap<>());
        adjacencyListMap.get(source).put(destination, distance);

//        for undirected graph
        adjacencyListMap.putIfAbsent(destination, new HashMap<>());
        adjacencyListMap.get(destination).put(source, distance);
    }

    public ArrayList<String> getShortestPaths(String source, String destination){
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
        while (current != null){
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);

         return path.size() >= 2 && path.get(0).equals(source) ? path : new ArrayList<>();

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
