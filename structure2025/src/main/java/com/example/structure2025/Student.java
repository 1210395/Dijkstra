package com.example.structure2025;

public class Student {
    private String id;
    private String name;
    private double tg;
    private double pt;
    private String major;
private double admark;
    public Student(String id, String name, double tg, double pt, String major) {
        this.id = id;
        this.name = name;
        this.tg = tg;
        this.pt = pt;
        this.major = major;
    }

    @Override
    public String toString() {
        return id + ':' + name + ':' + tg + ":" + pt + ":" + major ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTg() {
        return tg;
    }

    public void setTg(double tg) {
        this.tg = tg;
    }

    public double getPt() {
        return pt;
    }

    public void setPt(double pt) {
        this.pt = pt;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getAdmark() {
        return admark;
    }

    public void setAdmark(double admark) {
        this.admark = admark;
    }
}
