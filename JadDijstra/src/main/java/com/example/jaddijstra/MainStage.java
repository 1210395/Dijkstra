package com.example.jaddijstra;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainStage {
    private double mouseX, mouseY;
    private double imageX, imageY;
    ComboBox<String> from = new ComboBox<>();
    ComboBox<String> to = new ComboBox<>();
    private SingleLinkedList points;
    private SingleLinkedList lines;
    private SingleLinkedList pathes;
    private  AnchorPane imageContainer;
    private Stage stage;
    public MainStage(){
        rootMaker();
    }
    private   Dijkstra dijkstra ;
    private TextArea path = new TextArea();
    private TextField cost = new TextField();
    public void addPoints(){
        points=new SingleLinkedList();
        Node current=HelloApplication.cities.getFirstNode();
        while(current!=null){
            points.addLast(new Pointer((CityNode)current.getData()));
            current=current.getNextNode();
        }
        pinPoints();
    }
    public void addLines(){
        lines=new SingleLinkedList();

        Node current=pathes.getFirstNode();
        while(current!=null && current.getNextNode()!=null){
            Pointer point=new Pointer((CityNode) current.getData());

            current=current.getNextNode();
            Pointer point2=new Pointer((CityNode) current.getData());
            lines.addLast(new Liner(point,point2));

        }
        pinLines();
    }
    public void pinLines() {
        // Iterate through the points and add them to imageContainer
        Node current = lines.getFirstNode();
        while (current != null) {
            Liner l= (Liner) current.getData();
            // Add the group containing Circle and Label to imageContainer
            imageContainer.getChildren().add(l.getLine());
            current = current.getNextNode();
        }
        imageContainer.requestLayout();
    }
    public void unpinLines() {
        if(lines!=null) {
            Node current = lines.getFirstNode();
            while (current != null) {
                imageContainer.getChildren().remove(((Liner) current.getData()).getLine());
                current = current.getNextNode();
            }
            imageContainer.requestLayout();
        }
    }
    public void pinPoints() {
        // Iterate through the points and add them to imageContainer
        Node current = points.getFirstNode();
        while (current != null) {
            Pointer p = (Pointer) current.getData();
            // Add the group containing Circle and Label to imageContainer
            imageContainer.getChildren().add(p.getGroup());
            current = current.getNextNode();
        }
        imageContainer.requestLayout();
    }
    public void fillCombos(){
        Node current=HelloApplication.cities.getFirstNode();
        while(current!=null){
            from.getItems().add(((CityNode)current.getData()).getCity());
            to.getItems().add(((CityNode)current.getData()).getCity());
            current=current.getNextNode();
        }

    }
    public void rootMaker() {
        // Creating Objects
        ImageView img=new ImageView(new Image("world-map-pro.jpg"));
        Label fromlbl = new Label("From:");
        Label tolbl = new Label("To:");
        Label pathlbl = new Label("Path taken:");
        Label costlbl = new Label("The Cost in Kilometers:");
        Button calc=new Button("Calculate");

        Button reset = new Button("Reset Full Scene");
        VBox pathbox = new VBox(10, pathlbl, path);
        VBox costbox = new VBox(10, costlbl, cost);
        VBox frombox = new VBox(10, fromlbl, from);
        HBox buttonsbox = new HBox(10, reset);
        VBox calcbox=new VBox(10,calc);
        VBox tobox = new VBox(10, tolbl, to);
        BorderPane root = new BorderPane();
        VBox controlsContainer = new VBox(10,frombox,tobox,calcbox, pathbox, costbox, buttonsbox );
         imageContainer = new AnchorPane();
        StackPane mainstack=new StackPane();
        // Styling
        pathbox.setAlignment(Pos.CENTER);
        costbox.setAlignment(Pos.CENTER);
        calcbox.setAlignment(Pos.CENTER);
        frombox.setAlignment(Pos.CENTER);
        buttonsbox.setAlignment(Pos.CENTER);
        tobox.setAlignment(Pos.CENTER);
        controlsContainer.setAlignment(Pos.CENTER);
        controlsContainer.setMaxWidth(250);root.setStyle("-fx-background-color: #f0f0f0;");
        controlsContainer.setStyle("-fx-background-color: #ffffff; -fx-padding: 10px;");
        fromlbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        tolbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        pathlbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        costlbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        reset.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        calc.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        path.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        cost.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        from.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        to.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        img.setFitWidth(800);
        img.setFitHeight(600);
        img.setPickOnBounds(true);
        img.setPreserveRatio(false);
        img.setSmooth(true);
        imageContainer.setScaleX(1.5);
        imageContainer.setScaleY(1.5);
        img.setTranslateX(200);
        fillCombos();
        //events
        imageContainer.setOnScroll(this::handleScrollEvent);
        imageContainer.setOnMousePressed(this::handleMousePressed);
        imageContainer.setOnMouseDragged(this::handleMouseDragged);
        reset.setOnAction(e->{
            this.close();
          HelloApplication.m=new MainStage();
            HelloApplication.m.show();
        });

        calc.setOnAction(e->{
            if(this.from.getSelectionModel().getSelectedItem()!=null && this.to.getSelectionModel().getSelectedItem()!=null){
                unpinLines();
                path.setText(findShortestPath(this.from.getSelectionModel().getSelectedItem(),this.to.getSelectionModel().getSelectedItem()).split(",")[0]);
                cost.setText(findShortestPath(this.from.getSelectionModel().getSelectedItem(),this.to.getSelectionModel().getSelectedItem()).split(",")[1]);
                addLines();

            }
        });
        // Filling containers
        imageContainer.getChildren().addAll(img); // Add controlsContainer to imageContainer
        StackPane.setAlignment(img, Pos.CENTER_LEFT); // Position controlsContainer
        mainstack.getChildren().addAll(imageContainer,controlsContainer);
        StackPane.setAlignment(controlsContainer, Pos.CENTER_RIGHT); // Position controlsContainer
        // Root setup
        root.setCenter(mainstack);
        stage = new Stage();
        stage.setScene(new Scene(root, 1000, 800));
        addPoints();
    }
    private double calculateTotalDistance(SingleLinkedList path) {
        double totalDistance = 0;
        Node currentNode = path.getFirstNode();
        while (currentNode != null) {
            CityNode cityNode = (CityNode) currentNode.getData();
            totalDistance += cityNode.getDistance();
            currentNode = currentNode.getNextNode();
        }
        return totalDistance;
    }
    private String findShortestPath(String fromCity, String toCity) {

        if (fromCity == null || toCity == null) {
            HelloApplication.errorStage("Please select both From and To cities.");
            return null;
        }

        CityNode fromNode = (CityNode) HelloApplication.cities.find(fromCity).getData();
        CityNode toNode =  (CityNode) HelloApplication.cities.find(toCity).getData();

        if (fromNode == null || toNode == null) {
            HelloApplication.errorStage("One or both of the selected cities do not exist.");
            return null;
        }

        dijkstra= new Dijkstra(HelloApplication.cities);
        dijkstra.execute(fromNode);
        SingleLinkedList shortestPath = dijkstra.getPath(toNode,fromNode);
        pathes=shortestPath;
        pathes.addFirst(fromNode);
        if (shortestPath.size()==1) {
            path.setText("No path found");
            cost.setText("Can't Define cost");
            return null;
        }

        StringBuilder pathString = new StringBuilder();
        Node currentNode = shortestPath.getFirstNode();
        while (currentNode != null) {
            CityNode cityNode = (CityNode) currentNode.getData();
            pathString.append(cityNode.getCity()).append(" -> ");
            currentNode = currentNode.getNextNode();
        }
        return pathString.toString().substring(0,pathString.length()-4)+","+calculateTotalDistance(dijkstra.getPath(toNode,fromNode));
    }
    public  void show(){
        stage.show();
    }
    public void close(){
        stage.close();
    }
    private void handleScrollEvent(ScrollEvent event) {

        double delta = event.getDeltaY();

        // Zoom factor
        double zoomFactor = delta > 0 ? 1.1 : delta < 0 ? 0.9 : 1.0;
            // Calculate the scaling factor
            double newScaleX = imageContainer.getScaleX() * zoomFactor;
            double newScaleY = imageContainer.getScaleY() * zoomFactor;

        // Limit zoom out to original size
        if (delta < 0) { // Zoom out
            double originalScaleX = 1.5;
            double originalScaleY = 1.5;
            if (newScaleX < originalScaleX || newScaleY < originalScaleY) {//Reset
                newScaleX = originalScaleX;
                newScaleY = originalScaleY;

                // Reset translation to original position
                imageContainer.setTranslateX(200);
                imageContainer.setTranslateY(0);
            }
        }
        if (delta > 0) { // Zoom out
            double originalScaleX =10 ;
            double originalScaleY = 10;
            if (newScaleX > originalScaleX || newScaleY > originalScaleY) {//Limit Zoom out
                newScaleX = originalScaleX;
                newScaleY = originalScaleY;


            }
        }
        // Apply the scaling factor
        imageContainer.setScaleX(newScaleX);
        imageContainer.setScaleY(newScaleY);

    }
    private void handleMousePressed(javafx.scene.input.MouseEvent event) {
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
        imageX = imageContainer.getTranslateX();
        imageY = imageContainer.getTranslateY();
    }
    private void handleMouseDragged(javafx.scene.input.MouseEvent event) {
        double offsetX = event.getSceneX() - mouseX;
        double offsetY = event.getSceneY() - mouseY;
        double newTranslateX = imageX + offsetX;
        double newTranslateY = imageY + offsetY;
        if(newTranslateX<(150*(imageContainer.getScaleX()*1.5)) && newTranslateX>(-150*(imageContainer.getScaleX()*1.5))){
            imageContainer.setTranslateX(newTranslateX);

        }
        if(newTranslateY<(150*imageContainer.getScaleY())  && newTranslateY>(-150*imageContainer.getScaleY())){
            imageContainer.setTranslateY(newTranslateY);

        }

    }
}
