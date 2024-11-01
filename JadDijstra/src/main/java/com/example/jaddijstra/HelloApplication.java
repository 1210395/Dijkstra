package com.example.jaddijstra;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;
import java.io.*;
import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    public static SingleLinkedList cities=new SingleLinkedList();
    public static MainStage m;

    @Override
    public void start(Stage stage) throws IOException {
        fileReader();
        m=new MainStage();
        m.show();
    }

    public static void fileReader(){
        File file=new FileChooser().showOpenDialog(new Stage());
        try{
            Scanner in=new Scanner(file);
            String [] str=in.nextLine().split(" ");
            int count=1;
            int numOfcities=0;
            int adj=0;
            boolean flag=false;
            try {
                 numOfcities = Integer.parseInt(str[0]);
                 adj = Integer.parseInt(str[1]);
            }catch (Exception e){
                errorStage("Error in the start of the file");
            }
            for(int i=0;i<numOfcities;i++){
                count++;
                if(in.hasNextLine()) {
                    String  line=in.nextLine();
                     str=line.split(" ");
                    if (str.length != 3) {
                        errorStage("Error in Line (Reading Cities): " +count+" "+ line);
                    } else {
                        try {
                            cities.addLast(new CityNode(str[0].trim(), Double.parseDouble(str[1].trim()), Double.parseDouble(str[2].trim())));
                        } catch (Exception e) {
                            errorStage("Error Parsing Line : " +count+" " + line);
                        }
                    }
                }else{
                    if(!flag) {
                        errorStage("Unexpected End of File (Reading Cities)!!");
                        flag=true;
                    }
                }
            }
            boolean flag2=false;
            for(int i=0;i<adj;i++){
                count++;
                if(in.hasNextLine()){
                String  line=in.nextLine();
                 str=line.split("#");
                if(str.length!=2){
                    errorStage("Error in Line (Reading Adj): "+count+" "+line);
                }else{
                    try{
                         CityNode city1=(CityNode)cities.find(str[0]).getData();
                        CityNode city2=(CityNode)cities.find(str[1]).getData();
                        if(city1==null || city2==null){
                            throw new Exception();
                        }else{
                            city1.addAdjDirectional(city2);
                        }
                    }catch (Exception e){
                        errorStage("One or both of the cities Don't exist "+str[0]+" "+str[1]+" "+count+" ");

                    }
                }
                }else{
                   if(!flag2) {
                       errorStage("Unexpected End of File (Reading Adjecents)!!");
                       flag2=true;
                   }
                }
            }
            if(in.hasNextLine()){
                errorStage("The file still has more infromation !!!");
            }
        }catch(Exception e){
            errorStage("Error Reading File!!!");
        }
    }
    public static void errorStage(String message) {
        Stage errorStage = new Stage();
        errorStage.setTitle("Error");

        Label errorLabel = new Label(message);
        errorLabel.setTextFill(Color.WHITE);
        errorLabel.setFont(new Font("Arial Bold", 16));
        errorLabel.setWrapText(true);

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white;");
        closeButton.setFont(new Font("Arial", 14));
        closeButton.setOnAction(e -> errorStage.close());
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: red; -fx-text-fill: white;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white;"));

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(errorLabel, closeButton);
        layout.setPadding(new Insets(20));
        layout.setBackground(new Background(new BackgroundFill(Color.DARKRED, new CornerRadii(10), Insets.EMPTY)));
        layout.setEffect(new DropShadow(10, Color.BLACK));
        layout.setMaxWidth(280);

        Scene scene = new Scene(layout, 320, 180);
        errorStage.setScene(scene);
        errorStage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}