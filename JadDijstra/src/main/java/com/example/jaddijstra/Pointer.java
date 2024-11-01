package com.example.jaddijstra;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class Pointer {
    private Circle c;
    private Label label;
    private double x;
    private double y;
    private HBox group;
    private CityNode cityNode;

    public CityNode getCityNode() {
        return cityNode;
    }

    public void setCityNode(CityNode cityNode) {
        this.cityNode = cityNode;
    }



    public Pointer(CityNode cityNode) {
        // Initialize Circle and Label
        this.c = new Circle();
        this.c.setRadius(1);
        this.cityNode=cityNode;
        this.label = new Label(cityNode.getCity());

        // Create VBox to hold Circle and Label
        this.group = new HBox(5, c, label);
        this.x = calcX(cityNode.getLon())-(225+label.getWidth());
        this.y = calcY(cityNode.getLat())+15;
        // Set properties for Circle
        this.c.setLayoutX(x);
        this.c.setLayoutY(y);
        this.c.setFill(Color.RED);
        this.c.setStroke(Color.RED);
        this.c.setStrokeType(StrokeType.INSIDE);
        this.group.setLayoutX(x);
        this.group.setLayoutY(y);
        this.group.setAlignment(Pos.CENTER);
        // Set properties for Label
        this.label.setStyle("-fx-font-size: 2px;  -fx-text-fill: black;");
        this.label.setLayoutX(x - label.getWidth() / 2);
        this.label.setLayoutY(y - 15);

        // Set event handlers for group (VBox)
        this.group.setOnMouseEntered(e -> highlight());
        this.group.setOnMouseExited(e -> unhighlight());
        this.group.setOnMouseClicked(e->{
            select();
        });
    }
    public void select(){

            if (HelloApplication.m.from.getSelectionModel().getSelectedItem() == null) {
                HelloApplication.m.from.getSelectionModel().select(this.cityNode.getCity());
            } else if (HelloApplication.m.to.getSelectionModel().getSelectedItem() == null) {
                HelloApplication.m.to.getSelectionModel().select(this.cityNode.getCity());
            } else {
                HelloApplication.m.from.getSelectionModel().select(this.cityNode.getCity());
                HelloApplication.m.to.getSelectionModel().clearSelection();
            }

    }
    public Circle getC() {
        return c;
    }

    public void setC(Circle c) {
        this.c = c;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public HBox getGroup() {
        return group;
    }

    public void setGroup(HBox group) {
        this.group = group;
    }

    private void highlight() {
        c.setFill(Color.GREEN);
        c.setStroke(Color.GREEN);
        label.setTextFill(Color.RED);
    }

    private void unhighlight() {
        c.setFill(Color.RED);
        c.setStroke(Color.RED);
        label.setTextFill(Color.BLACK);

    }

    private double calcY(double lat) {
        double imageHeight = 600;
        return imageHeight * (1 - ((lat + 90) / 180.0));
    }
    private double calcX(double lon) {
        double imageWidth = 800;
        return imageWidth * (1 + (lon / 360.0));
    }
}
