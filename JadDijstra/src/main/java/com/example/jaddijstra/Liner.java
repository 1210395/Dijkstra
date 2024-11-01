package com.example.jaddijstra;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Liner {
    private Line line;
    public Liner(Pointer p1,Pointer p2){
        line=new Line();

        line.setStartX(p1.getX());
        line.setStartY(p1.getY()+1);
        line.setEndX(p2.getX());
        line.setEndY(p2.getY()+1);
        line.setStrokeWidth(0.5);
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
