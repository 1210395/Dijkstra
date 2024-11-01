package com.example.jaddijstra;

public class CityNode {
    private SingleLinkedList adjlist;
    private String city;
    private double lon;
    private double lat;
    private CityNode previous;
    private double distance;
    public CityNode(String city, double lat, double lon) {
        this.city = city;
        this.lat = lat;
        this.lon = lon;
        this.previous = null;

        this.distance=Double.MAX_VALUE;
        this.adjlist=new SingleLinkedList();
    }
    public void addAdjUnDirectional(CityNode node){
        this.adjlist.addLast(node);
        node.adjlist.addLast(this);
    }
    public void setDistance(double distance){
        this.distance=distance;
    }
    public void addAdjDirectional(CityNode node){
        this.adjlist.addLast(node);
    }

    public CityNode getPrevious() {
        return previous;
    }

    public void setPrevious(CityNode previous) {
        this.previous = previous;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "CityNode{" +
                "adjlist=" + adjlist +
                ", city='" + city + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public SingleLinkedList getAdjlist() {
        return adjlist;
    }

    public void setAdjlist(SingleLinkedList adjlist) {
        this.adjlist = adjlist;
    }
}
