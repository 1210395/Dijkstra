package com.example.structure2025;

import java.util.Objects;

public class Major {
    String name;
    double ag;
    double pt;
    double tw;
    int accepted;
    int rejectedpt;
    int rejectedtw;
    SingleLinkedList list=new SingleLinkedList();
    public Major(String name, double ag, double tw, double pt) {
        this.name = name;
        this.ag = ag;
        this.tw = tw;
        this.pt = pt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Major major = (Major) o;
        return Objects.equals(name, major.name);
    }

    public Major(){
        this.list=new SingleLinkedList();
    }
    public boolean addSorted(Student s){
        double admark=(s.getTg()*this.tw)+(s.getPt()*this.pt);
        if(admark<ag){
            if((s.getTg()*this.tw)<(s.getPt()*this.pt))
                rejectedtw++;
            else
                rejectedpt++;
            return false;
        }else{
            accepted++;
            s.setAdmark(admark);
            s.setMajor(this.name);
            list.addSorted(s);
            return true;
        }
    }
    public boolean checkStudent(Student s){
        double admark=(s.getTg()*this.tw)+(s.getPt()*this.pt);
        if(admark<ag){
            return false;
        }else{
            return true;
        }
    }
    public SingleLinkedList getList() {
        return list;
    }

    public void setList(SingleLinkedList list) {
        this.list = list;
    }

    public double getPt() {
        return pt;
    }

    public void setPt(double pt) {
        this.pt = pt;
    }

    public double getAg() {
        return ag;
    }

    public void setAg(double ag) {
        this.ag = ag;
    }

    public double getTw() {
        return tw;
    }

    public void setTw(double tw) {
        this.tw = tw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ':' + ag + ":" + pt + ":" + tw ;
    }
}
