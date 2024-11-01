package com.example.jaddijstra;

public class Dijkstra {
    private SingleLinkedList graph;

    public Dijkstra(SingleLinkedList graph) {
        this.graph = graph;
    }

    public void execute(CityNode source) {
        source.setDistance(0);
        MinHeapOne minHeap = new MinHeapOne(graph.size());
        minHeap.insert(source);

        while (!minHeap.isEmpty()) {
            CityNode currentNode = minHeap.extractMin();

            for (int i = 0; i < currentNode.getAdjlist().size(); i++) {
                CityNode neighborNode = (CityNode) currentNode.getAdjlist().get(i).getData();
                double distance = currentNode.getDistance() + calculateDistance(currentNode, neighborNode);

                if (distance < neighborNode.getDistance()) {
                    neighborNode.setDistance(distance);
                    neighborNode.setPrevious(currentNode);
                    minHeap.insert(neighborNode);
                }
            }
        }
    }




    public SingleLinkedList getPath(CityNode target,CityNode from) {
        SingleLinkedList path = new SingleLinkedList();
        if (target.getDistance() == Double.MAX_VALUE) {
            return path; // Return an empty path
        }
        for (CityNode node = target; node != null; node = node.getPrevious()) {
            if(node.getCity().equalsIgnoreCase(from.getCity())) {
                return path;
            }

            path.addFirst(node);
        }
        return new SingleLinkedList();

    }
    private double calculateDistance(CityNode node1, CityNode node2) {
        // Use the Haversine formula to calculate the distance between two nodes
        double R = 6371; // Radius of the Earth in kilometers
        double latDistance = Math.toRadians(node2.getLat() - node1.getLat());
        double lonDistance = Math.toRadians(node2.getLon() - node1.getLon());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(node1.getLat())) * Math.cos(Math.toRadians(node2.getLat()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c;

        return distance;
    }

}
