package com.example.structure2025;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    public static DoubleNode current;

    @Override
    public void start(Stage stage) throws IOException {
        DoubleLinkedList list = new DoubleLinkedList();
        try {
            try {
                File filemajor = new FileChooser().showOpenDialog(new Stage());
                Scanner inputmajor = new Scanner(filemajor);
                //inputmajor.nextLine();
                while (inputmajor.hasNextLine()) {
                    String[] data = null;
                    try {
                        data = inputmajor.nextLine().split(":");
                    } catch (Exception E) {
                        Label error = new Label("missing attributes");
                        Stage Estage = new Stage();
                        BorderPane eroot = new BorderPane();
                        eroot.setCenter(error);
                        Estage.setScene(new Scene(eroot, 300, 300));
                        Estage.show();
                    }
                    String name = data[0];
                    if (name.trim().isBlank()) {
                        Label error = new Label("No name");
                        Stage Estage = new Stage();
                        BorderPane eroot = new BorderPane();
                        eroot.setCenter(error);
                        Estage.setScene(new Scene(eroot, 300, 300));
                        Estage.show();
                    }
                    try {
                        double ag = Double.parseDouble(data[1]);
                        double tw = Double.parseDouble(data[2]);
                        double pw = Double.parseDouble(data[3]);
                        list.addSorted(new Major(name, ag, tw, pw));
                    } catch (Exception E) {
                        Label error = new Label("error Parsing double");
                        Stage Estage = new Stage();
                        BorderPane eroot = new BorderPane();
                        eroot.setCenter(error);
                        Estage.setScene(new Scene(eroot, 300, 300));
                        Estage.show();
                    }
                }
            } catch (Exception e) {
                Label error = new Label("error reading file");
                Stage Estage = new Stage();
                BorderPane eroot = new BorderPane();
                eroot.setCenter(error);
                Estage.setScene(new Scene(eroot, 300, 300));
                Estage.show();

            }
            File file = new FileChooser().showOpenDialog(new Stage());
            Scanner input = new Scanner(file);
           // input.nextLine();
            while (input.hasNextLine()) {
                String[] data = null;
                try {
                    data = input.nextLine().split(":");
                } catch (Exception e) {
                    Label error = new Label("Missing attributes");
                    Stage Estage = new Stage();
                    BorderPane eroot = new BorderPane();
                    eroot.setCenter(error);
                    Estage.setScene(new Scene(eroot, 300, 300));
                    Estage.show();


                }
                String id = data[0];
                String name = data[1];
                double tg = 0;
                double pt = 0;
                try {
                    tg = Double.parseDouble(data[2]);
                    pt = Double.parseDouble(data[3]);
                } catch (Exception E) {
                    Label error = new Label("error parsing double");
                    Stage Estage = new Stage();
                    BorderPane eroot = new BorderPane();
                    eroot.setCenter(error);
                    Estage.setScene(new Scene(eroot, 300, 300));
                    Estage.show();
                    continue;
                }
                String major = data[4];
                Student s = new Student(id, name, tg, pt, major);
                if (list.find(major) == null) {
                    Label error = new Label("Major not found" + major);
                    Stage Estage = new Stage();
                    BorderPane eroot = new BorderPane();
                    eroot.setCenter(error);
                    Estage.setScene(new Scene(eroot, 300, 300));
                    Estage.show();
                    if (list.find("Major not found") == null) {
                        list.addSorted("Major not found");
                        s.setMajor("Major not found");
                        s.setAdmark(0);
                        ((Major) list.find("Major not found").getData()).getList().addSorted(s);
                    }
                } else {
                    boolean flag = ((Major) list.find(major).getData()).addSorted(s);
                    if (!flag) {
                        boolean flag2 = false;
                        for (int i = 0; i < list.size(); i++) {
                            flag2 = ((Major) list.get(i).getData()).addSorted(s);
                            if (flag2) {
                                break;
                            }
                        }
                        if (!flag2) {
                            if (list.find("Not Accepted") == null)
                                list.addSorted(new Major("Not Accepted", 0, 0, 0));
                            s.setMajor("Not Accepted");
                            s.setAdmark(0);
                            ((Major) list.find("Not Accepted").getData()).getList().addSorted(s);
                        }
                    } else {

                    }
                }

            }
        } catch (Exception e) {
            Label error = new Label("Error reading file");
            Stage Estage = new Stage();
            BorderPane eroot = new BorderPane();
            eroot.setCenter(error);
            Estage.setScene(new Scene(eroot, 300, 300));
            Estage.show();

        }
        Button majorscreen = new Button("Majors");
        Button statistics = new Button("Statistics");
        Button savetoFile = new Button("Save to File");
        VBox box = new VBox(10, majorscreen, statistics, savetoFile);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box, 600, 600);
        stage.setScene(scene);
        stage.show();
        Button next = new Button("Next Major");
        Button prev = new Button("Prev Major");
        majorscreen.setOnAction(e -> {
            current = list.getFirstNode();
            Label Major = new Label("Major : " + ((Major) current.getData()).getName() + "Total number of Accepted :" + ((Major) current.getData()).accepted + " \n Total number of rejected by tawjihi low mark :" + ((Major) current.getData()).rejectedtw + " \n total number rejected by too low Placement test mark : " + ((Major) current.getData()).rejectedpt);
            Button insert = new Button("Insert new Major");
            insert.setOnAction(ev -> {
                Stage insertstage = new Stage();
                GridPane grid = new GridPane();
                Label major = new Label("Major:");
                Label ag = new Label("Acceptance Grade: ");
                Label tw = new Label("Tawjihi weight: ");
                Label pt = new Label("Placement Test Grade: ");
                TextField majort = new TextField();
                TextField agt = new TextField();
                TextField twt = new TextField();
                TextField ptt = new TextField();
                Button ok = new Button("Confrim");
                Button cancel = new Button("Cancel");
                cancel.setOnAction(eve -> {
                    insertstage.close();
                });
                ok.setOnAction(eve -> {
                    String name = majort.getText().trim();
                    if (name.trim().isBlank()) {
                        Label error = new Label("no Name");
                        Stage Estage = new Stage();
                        BorderPane eroot = new BorderPane();
                        eroot.setCenter(error);
                        Estage.setScene(new Scene(eroot, 300, 300));
                        Estage.show();

                    } else {
                        try {
                            double agd = Double.parseDouble(agt.getText().trim());
                            double twd = Double.parseDouble(twt.getText().trim());
                            double pwd = Double.parseDouble(ptt.getText().trim());
                            if (twd > 1 || pwd > 1) {
                                Label error = new Label("Can't be greater than 1");
                                Stage Estage = new Stage();
                                BorderPane eroot = new BorderPane();
                                eroot.setCenter(error);
                                Estage.setScene(new Scene(eroot, 300, 300));
                                Estage.show();
                            } else {
                                if (list.find(name) == null) {
                                    list.addSorted(new Major(name, agd, twd, pwd));
                                    current = list.getFirstNode();
                                    Major.setText("Major : " + ((Major) current.getData()).getName() + "Total number of Accepted :" + ((Major) current.getData()).accepted + " \n Total number of rejected by tawjihi low mark :" + ((Major) current.getData()).rejectedtw + " \n total number rejected by too low Placement test mark : " + ((Major) current.getData()).rejectedpt);
                                    insertstage.close();
                                } else {
                                    Label error = new Label("Already exists");
                                    Stage Estage = new Stage();
                                    BorderPane eroot = new BorderPane();
                                    eroot.setCenter(error);
                                    Estage.setScene(new Scene(eroot, 300, 300));
                                    Estage.show();
                                }
                            }
                        } catch (Exception E) {
                            Label error = new Label("error Parsing double");
                            Stage Estage = new Stage();
                            BorderPane eroot = new BorderPane();
                            eroot.setCenter(error);
                            Estage.setScene(new Scene(eroot, 300, 300));
                            Estage.show();
                        }
                    }
                });
                grid.setAlignment(Pos.CENTER);
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.add(major, 0, 1);
                grid.add(majort, 1, 1);
                grid.add(ag, 0, 2);
                grid.add(agt, 1, 2);
                grid.add(tw, 0, 3);
                grid.add(twt, 1, 3);
                grid.add(pt, 0, 4);
                grid.add(ptt, 1, 4);
                grid.add(ok, 0, 5);
                grid.add(cancel, 1, 5);
                Scene scene1 = new Scene(grid, 800, 600);
                insertstage.setScene(scene1);
                insertstage.show();

            });
            Button delete = new Button("Delete Current Major");
            delete.setOnAction(eve -> {
                Stage stage1 = new Stage();
                Button ok = new Button("OK");
                Button cancel = new Button("Cancel");
                Label label = new Label("Delete Major " + ((Major) current.getData()).getName());
                HBox hbox = new HBox(10, ok, cancel);
                VBox vbox = new VBox(10, label, hbox);
                hbox.setAlignment(Pos.CENTER);
                vbox.setAlignment(Pos.CENTER);
                cancel.setOnAction(even -> {
                    stage1.close();
                });
                ok.setOnAction(even -> {
                    Stage sure = new Stage();
                    Label surelbl = new Label("Are you sure?");
                    Button surebutton = new Button("Sure");
                    Button cancelsure = new Button("Cancel");
                    HBox hboxsure = new HBox(10, surebutton, cancelsure);
                    BorderPane sureroot = new BorderPane();
                    sureroot.setCenter(surelbl);
                    sureroot.setBottom(hboxsure);
                    hboxsure.setAlignment(Pos.CENTER);
                    sure.setScene(new Scene(sureroot, 400, 400));
                    sure.show();
                    surebutton.setOnAction(event -> {
                        list.remove(current.getData());
                        current = list.getFirstNode();
                        Major.setText("Major : " + ((Major) current.getData()).getName() + "Total number of Accepted :" + ((Major) current.getData()).accepted + " \n Total number of rejected by tawjihi low mark :" + ((Major) current.getData()).rejectedtw + " \n total number rejected by too low Placement test mark : " + ((Major) current.getData()).rejectedpt);
                        prev.setDisable(true);
                        next.setDisable(false);
                        stage1.close();
                        sure.close();
                    });
                    cancelsure.setOnAction(event -> {
                        sure.close();
                        stage1.close();
                    });

                });
                Scene scene1 = new Scene(vbox, 600, 600);
                stage1.setScene(scene1);
                stage1.show();
            });
            Button update = new Button("Update current Major");
            Button search = new Button("Search for a Major");
            TextField setxt = new TextField();
            search.setOnAction(ev -> {
                String name = setxt.getText().trim();
                if (name.isEmpty()) {
                    Label error = new Label("Empty");
                    Stage Estage = new Stage();
                    BorderPane eroot = new BorderPane();
                    eroot.setCenter(error);
                    Estage.setScene(new Scene(eroot, 300, 300));
                    Estage.show();
                } else {
                    if (list.find(name) == null) {
                        Label error = new Label("not found");
                        Stage Estage = new Stage();
                        BorderPane eroot = new BorderPane();
                        eroot.setCenter(error);
                        Estage.setScene(new Scene(eroot, 300, 300));
                        Estage.show();
                    } else {
                        current = list.find(name);
                        Major.setText("Major : " + ((Major) current.getData()).getName() + "Total number of Accepted :" + ((Major) current.getData()).accepted + " \n Total number of rejected by tawjihi low mark :" + ((Major) current.getData()).rejectedtw + " \n total number rejected by too low Placement test mark : " + ((Major) current.getData()).rejectedpt);
                    }
                }
            });
            Button open = new Button("Open Student page");
            update.setOnAction(ev -> {
                Stage insertstage = new Stage();
                GridPane grid = new GridPane();
                Label major = new Label("Major:");
                Label ag = new Label("Acceptance Grade: ");
                Label tw = new Label("Tawjihi weight: ");
                Label pt = new Label("Placement Test Grade: ");
                TextField majort = new TextField();
                majort.setText(((Major) current.getData()).getName());
                TextField agt = new TextField();
                agt.setText(((Major) current.getData()).getAg() + "");
                TextField twt = new TextField();
                twt.setText(((Major) current.getData()).getTw() + "");
                TextField ptt = new TextField();
                ptt.setText(((Major) current.getData()).getPt() + "");
                Button ok = new Button("Confrim");
                Button cancel = new Button("Cancel");
                cancel.setOnAction(eve -> {
                    insertstage.close();
                });
                ok.setOnAction(eve -> {
                    String name = majort.getText().trim();
                    if (name.trim().isBlank()) {
                        Label error = new Label("no Name");
                        Stage Estage = new Stage();
                        BorderPane eroot = new BorderPane();
                        eroot.setCenter(error);
                        Estage.setScene(new Scene(eroot, 300, 300));
                        Estage.show();

                    } else {
                        try {
                            double agd = Double.parseDouble(agt.getText().trim());
                            double twd = Double.parseDouble(twt.getText().trim());
                            double pwd = Double.parseDouble(ptt.getText().trim());
                            Major m = new Major();
                            Stage sure = new Stage();
                            Label surelbl = new Label("Are you sure ?");
                            Button surebutton = new Button("Sure");
                            Button cancelsure = new Button("Cancel");
                            HBox hboxsure = new HBox(10, surebutton, cancelsure);
                            BorderPane sureroot = new BorderPane();
                            sureroot.setCenter(surelbl);
                            sureroot.setBottom(hboxsure);
                            hboxsure.setAlignment(Pos.CENTER);
                            sure.setScene(new Scene(sureroot, 400, 400));
                            sure.show();
                            surebutton.setOnAction(even -> {
                                m.setName(name);
                                m.setAg(agd);
                                m.setTw(twd);
                                m.setPt(pwd);
                                for (int i = 0; i < ((Major) current.getData()).getList().size(); i++) {
                                    m.addSorted((Student) ((Major) current.getData()).getList().get(i).getData());
                                }
                                list.remove(current.getData());
                                list.addSorted(m);

                                current = list.getFirstNode();
                                Major.setText("Major : " + ((Major) current.getData()).getName() + "Total number of Accepted :" + ((Major) current.getData()).accepted + " \n Total number of rejected by tawjihi low mark :" + ((Major) current.getData()).rejectedtw + " \n total number rejected by too low Placement test mark : " + ((Major) current.getData()).rejectedpt);
                                insertstage.close();
                                sure.close();
                            });
                            cancelsure.setOnAction(even -> {
                                sure.close();
                                insertstage.close();
                            });


                        } catch (Exception E) {
                            Label error = new Label("error parsing");
                            Stage Estage = new Stage();
                            BorderPane eroot = new BorderPane();
                            eroot.setCenter(error);
                            Estage.setScene(new Scene(eroot, 300, 300));
                            Estage.show();
                        }
                    }
                });
                grid.setAlignment(Pos.CENTER);
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.add(major, 0, 1);
                grid.add(majort, 1, 1);
                grid.add(ag, 0, 2);
                grid.add(agt, 1, 2);
                grid.add(tw, 0, 3);
                grid.add(twt, 1, 3);
                grid.add(pt, 0, 4);
                grid.add(ptt, 1, 4);
                grid.add(ok, 0, 5);
                grid.add(cancel, 1, 5);
                Scene scene1 = new Scene(grid, 800, 600);
                insertstage.setScene(scene1);
                insertstage.show();

            });
            open.setOnAction(eve -> {
                Stage stage1 = new Stage();
                TableView<Student> tableView = new TableView<>();
                TableColumn<Student, String> idColumn = new TableColumn<>("ID");
                idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                TableColumn<Student, Double> tgColumn = new TableColumn<>("Tawjihi Grade");
                tgColumn.setCellValueFactory(new PropertyValueFactory<>("tg"));
                TableColumn<Student, Double> ptColumn = new TableColumn<>("Placement Test Grade");
                ptColumn.setCellValueFactory(new PropertyValueFactory<>("pt"));
                TableColumn<Student, String> majorColumn = new TableColumn<>("Major");
                majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
                TableColumn<Student, Double> admarkColumn = new TableColumn<>("Admission Mark ");
                admarkColumn.setCellValueFactory(new PropertyValueFactory<>("admark"));
                Button insertStudent = new Button("Insert Student");
                Button deleteStudent = new Button("Delete Student");
                Button updateStudent = new Button("Update Studnet");
                Button searchStudent = new Button("Search Student");
                Label major = new Label(((Major) current.getData()).getName());
                TextField searchStudenttxt = new TextField();
                searchStudent.setOnAction(even -> {
                    String idtext = searchStudenttxt.getText().trim();
                    for (int i = 0; i < list.size(); i++) {
                        Node s = ((Major) list.get(i).getData()).getList().find(idtext.trim());
                        if (s != null) {
                            current = list.get(i);
                            tableView.getItems().clear();
                            for (int j = 0; j < ((Major) current.getData()).getList().size(); j++)
                                tableView.getItems().add(((Student) ((Major) current.getData()).getList().get(j).getData()));
                            major.setText((((Major) current.getData()).getName()));
                            tableView.getSelectionModel().select((Student) s.getData());
                            break;

                        }
                    }
                });
                tableView.getItems().clear();
                for (int i = 0; i < ((Major) current.getData()).getList().size(); i++)
                    tableView.getItems().add(((Student) ((Major) current.getData()).getList().get(i).getData()));

                HBox hbox1 = new HBox(10, insertStudent, deleteStudent, updateStudent, searchStudenttxt, searchStudent);
                BorderPane vbox1 = new BorderPane();
                vbox1.setBottom(hbox1);
                vbox1.setCenter(tableView);
                vbox1.setTop(major);
                major.setAlignment(Pos.CENTER);
                hbox1.setAlignment(Pos.CENTER);
                tableView.getColumns().addAll(idColumn, nameColumn, tgColumn, ptColumn, majorColumn, admarkColumn);
                Scene scene12 = new Scene(vbox1, 600, 600);
                stage1.setScene(scene12);
                stage1.show();
                insertStudent.setOnAction(ev -> {
                    Stage insertStage = new Stage();
                    Label name = new Label("Name:");
                    Label ID = new Label("ID");
                    Label tg = new Label("Tawjihi grade");
                    Label pt = new Label("P grade");
                    Label majorl = new Label("Major");
                    TextField majort = new TextField();
                    TextField namet = new TextField();
                    TextField idt = new TextField();
                    TextField tgt = new TextField();
                    TextField ptt = new TextField();
                    Button generate = new Button("Generate Suggestions");
                    generate.setOnAction(even -> {
                        String nameS = namet.getText().trim();
                        if (nameS.isEmpty()) {
                            Label error = new Label("Empty");
                            Stage Estage = new Stage();
                            BorderPane eroot = new BorderPane();
                            eroot.setCenter(error);
                            Estage.setScene(new Scene(eroot, 300, 300));
                            Estage.show();
                        } else {
                            String idS = idt.getText().trim();
                            if (idS.isEmpty()) {
                                Label error = new Label("Empty ID");
                                Stage Estage = new Stage();
                                BorderPane eroot = new BorderPane();
                                eroot.setCenter(error);
                                Estage.setScene(new Scene(eroot, 300, 300));
                                Estage.show();
                            } else {
                                if (list.isValidStudent(idS)) {
                                    try {
                                        double tgS = Double.parseDouble(tgt.getText().trim());
                                        double ptS = Double.parseDouble(ptt.getText().trim());
                                        ComboBox<String> suggestedMajors = new ComboBox<>();
                                        Label title = new Label("Suggested Majors for student: " + nameS);
                                        VBox vbox2 = new VBox(10, title, suggestedMajors);
                                        Button ok1 = new Button("Confrim");
                                        DoubleLinkedList suggests = new DoubleLinkedList();
                                        for (int i = 0; i < list.size(); i++) {
                                            if (((Major) list.get(i).getData()).checkStudent(new Student(idS, nameS, tgS, ptS, ""))) {
                                                suggests.addSorted(list.get(i).getData());
                                            }
                                        }
                                        for (int i = 0; i < suggests.size(); i++)
                                            suggestedMajors.getItems().add(((Major) suggests.get(i).getData()).getName());
                                        Button cancel1 = new Button("Dont add student");
                                        Stage sug = new Stage();
                                        HBox hbox = new HBox(10, ok1, cancel1);
                                        VBox vbox3 = new VBox(20, vbox2, hbox);
                                        Scene scene2 = new Scene(vbox3, 400, 400);
                                        sug.setScene(scene2);
                                        sug.show();
                                        ok1.setOnAction(event -> {
                                            if (suggestedMajors.getSelectionModel().getSelectedItem() == null) {
                                                Label error = new Label("Nothing selected");
                                                Stage Estage = new Stage();
                                                BorderPane eroot = new BorderPane();
                                                eroot.setCenter(error);
                                                Estage.setScene(new Scene(eroot, 300, 300));
                                                Estage.show();
                                            } else {
                                                ((Major) list.find(suggestedMajors.getSelectionModel().getSelectedItem().trim()).getData()).addSorted(new Student(idS, nameS, tgS, ptS, ""));
                                                insertStage.close();
                                                sug.close();
                                                suggestedMajors.getItems().clear();
                                                for (int i = 0; i < suggests.size(); i++)
                                                    suggests.removeLast();
                                                tableView.getItems().clear();
                                                for (int i = 0; i < ((Major) current.getData()).getList().size(); i++)
                                                    tableView.getItems().add(((Student) ((Major) current.getData()).getList().get(i).getData()));

                                            }
                                        });
                                        cancel1.setOnAction(event -> {
                                            sug.close();
                                        });


                                        insertStage.close();

                                    } catch (Exception E) {
                                        Label error = new Label("error parsing");
                                        Stage Estage = new Stage();
                                        BorderPane eroot = new BorderPane();
                                        eroot.setCenter(error);
                                        Estage.setScene(new Scene(eroot, 300, 300));
                                        Estage.show();
                                    }
                                } else {
                                    Label error = new Label("ID already taken");
                                    Stage Estage = new Stage();
                                    BorderPane eroot = new BorderPane();
                                    eroot.setCenter(error);
                                    Estage.setScene(new Scene(eroot, 300, 300));
                                    Estage.show();
                                }
                            }

                        }

                    });
                    Button ok = new Button("OK");
                    Button cancel = new Button("Cancel");
                    GridPane grid = new GridPane();
                    grid.add(ID, 0, 0);
                    grid.add(idt, 1, 0);
                    grid.add(name, 0, 1);
                    grid.add(namet, 1, 1);
                    grid.add(tg, 0, 2);
                    grid.add(tgt, 1, 2);
                    grid.add(pt, 0, 3);
                    grid.add(ptt, 1, 3);
                    grid.add(majorl, 0, 4);
                    grid.add(majort, 1, 4);
                    grid.add(ok, 0, 5);
                    grid.add(cancel, 1, 5);
                    grid.add(generate, 2, 4);
                    Scene scene1 = new Scene(grid, 400, 400);
                    insertStage.setScene(scene1);
                    insertStage.show();
                    cancel.setOnAction(even -> {
                        insertStage.close();
                    });
                    ok.setOnAction(even -> {
                        String nameS = namet.getText().trim();
                        if (nameS.isEmpty()) {
                            Label error = new Label("Empty");
                            Stage Estage = new Stage();
                            BorderPane eroot = new BorderPane();
                            eroot.setCenter(error);
                            Estage.setScene(new Scene(eroot, 300, 300));
                            Estage.show();
                        } else {
                            String idS = idt.getText().trim();
                            if (idS.isEmpty()) {
                                Label error = new Label("Empty");
                                Stage Estage = new Stage();
                                BorderPane eroot = new BorderPane();
                                eroot.setCenter(error);
                                Estage.setScene(new Scene(eroot, 300, 300));
                                Estage.show();
                            } else {
                                if (list.isValidStudent(idS)) {
                                    try {
                                        double tgS = Double.parseDouble(tgt.getText().trim());
                                        double ptS = Double.parseDouble(ptt.getText().trim());
                                        DoubleNode majorN = list.find(majort.getText().trim());
                                        if (majorN != null) {
                                            boolean flag = ((Major) majorN.getData()).addSorted(new Student(idS, nameS, tgS, ptS, ((Major) current.getData()).getName()));
                                            if (flag == false) {
                                                ComboBox<String> suggestedMajors = new ComboBox<>();
                                                Label title = new Label("Suggested Majors for student: " + nameS);
                                                VBox vbox2 = new VBox(10, title, suggestedMajors);
                                                Button ok1 = new Button("Confrim");
                                                DoubleLinkedList suggests = new DoubleLinkedList();
                                                for (int i = 0; i < list.size(); i++) {
                                                    if (((Major) list.get(i).getData()).checkStudent(new Student(idS, nameS, tgS, ptS, ""))) {
                                                        suggests.addSorted(list.get(i).getData());
                                                    }
                                                }
                                                for (int i = 0; i < suggests.size(); i++)
                                                    suggestedMajors.getItems().add(((Major) suggests.get(i).getData()).getName());
                                                Button cancel1 = new Button("Dont add student");
                                                Stage sug = new Stage();
                                                HBox hbox = new HBox(10, ok1, cancel1);
                                                VBox vbox3 = new VBox(20, vbox2, hbox);
                                                Scene scene2 = new Scene(vbox3, 400, 400);
                                                sug.setScene(scene2);
                                                sug.show();
                                                ok1.setOnAction(event -> {
                                                    if (suggestedMajors.getSelectionModel().getSelectedItem() == null) {
                                                        Label error = new Label("Nothing selected");
                                                        Stage Estage = new Stage();
                                                        BorderPane eroot = new BorderPane();
                                                        eroot.setCenter(error);
                                                        Estage.setScene(new Scene(eroot, 300, 300));
                                                        Estage.show();
                                                    } else {
                                                        ((Major) list.find(suggestedMajors.getSelectionModel().getSelectedItem().trim()).getData()).addSorted(new Student(idS, nameS, tgS, ptS, ""));
                                                        insertStage.close();
                                                        sug.close();
                                                        suggestedMajors.getItems().clear();
                                                        for (int i = 0; i < suggests.size(); i++)
                                                            suggests.removeLast();
                                                        tableView.getItems().clear();
                                                        for (int i = 0; i < ((Major) current.getData()).getList().size(); i++)
                                                            tableView.getItems().add(((Student) ((Major) current.getData()).getList().get(i).getData()));

                                                    }
                                                });
                                                cancel1.setOnAction(event -> {
                                                    sug.close();
                                                });
                                            } else {
                                                tableView.getItems().clear();
                                                for (int i = 0; i < ((Major) current.getData()).getList().size(); i++)
                                                    tableView.getItems().add(((Student) ((Major) current.getData()).getList().get(i).getData()));

                                            }

                                            insertStage.close();
                                        }
                                    } catch (Exception E) {
                                        Label error = new Label("error parsing");
                                        Stage Estage = new Stage();
                                        BorderPane eroot = new BorderPane();
                                        eroot.setCenter(error);
                                        Estage.setScene(new Scene(eroot, 300, 300));
                                        Estage.show();
                                    }
                                } else {
                                    Label error = new Label("ID alrady taken");
                                    Stage Estage = new Stage();
                                    BorderPane eroot = new BorderPane();
                                    eroot.setCenter(error);
                                    Estage.setScene(new Scene(eroot, 300, 300));
                                    Estage.show();
                                }
                            }

                        }

                    });
                });
                deleteStudent.setOnAction(even -> {
                    if (tableView.getSelectionModel().getSelectedItem() == null) {
                        Label error = new Label("nothing selected");
                        Stage Estage = new Stage();
                        BorderPane eroot = new BorderPane();
                        eroot.setCenter(error);
                        Estage.setScene(new Scene(eroot, 300, 300));
                        Estage.show();
                    } else {
                        ((Major) current.getData()).getList().remove(tableView.getSelectionModel().getSelectedItem().getId());
                        tableView.getItems().clear();
                        for (int i = 0; i < ((Major) current.getData()).getList().size(); i++)
                            tableView.getItems().add(((Student) ((Major) current.getData()).getList().get(i).getData()));
                    }
                });
                updateStudent.setOnAction(ev -> {
                    if (tableView.getSelectionModel().getSelectedItem() == null) {
                        Label error = new Label("nothing selected");
                        Stage Estage = new Stage();
                        BorderPane eroot = new BorderPane();
                        eroot.setCenter(error);
                        Estage.setScene(new Scene(eroot, 300, 300));
                        Estage.show();
                    } else {
                        Stage insertStage = new Stage();
                        Label name = new Label("Name:");
                        Label ID = new Label("ID");
                        Label tg = new Label("Tawjihi grade");
                        Label pt = new Label("P grade");
                        Label majorl = new Label("Major");
                        TextField majort = new TextField();
                        Student s = tableView.getSelectionModel().getSelectedItem();
                        ((Major) current.getData()).getList().remove(s.getId());
                        majort.setText(s.getMajor());
                        TextField namet = new TextField();
                        namet.setText(s.getName());
                        TextField idt = new TextField();
                        idt.setText(s.getId());
                        TextField tgt = new TextField();
                        tgt.setText(s.getTg() + "");
                        TextField ptt = new TextField();
                        ptt.setText(s.getPt() + "");
                        Button generate = new Button("Generate Suggestions");
                        generate.setOnAction(even -> {
                            String nameS = namet.getText().trim();
                            if (nameS.isEmpty()) {
                                Label error = new Label("Empty");
                                Stage Estage = new Stage();
                                BorderPane eroot = new BorderPane();
                                eroot.setCenter(error);
                                Estage.setScene(new Scene(eroot, 300, 300));
                                Estage.show();
                            } else {
                                String idS = idt.getText().trim();
                                if (idS.isEmpty()) {
                                    Label error = new Label("Empty");
                                    Stage Estage = new Stage();
                                    BorderPane eroot = new BorderPane();
                                    eroot.setCenter(error);
                                    Estage.setScene(new Scene(eroot, 300, 300));
                                    Estage.show();
                                } else {
                                    if (list.isValidStudent(idS)) {
                                        try {
                                            double tgS = Double.parseDouble(tgt.getText().trim());
                                            double ptS = Double.parseDouble(ptt.getText().trim());
                                            boolean flag = ((Major) current.getData()).addSorted(new Student(idS, nameS, tgS, ptS, ((Major) current.getData()).getName()));
                                            if (flag == false) {
                                                ComboBox<String> suggestedMajors = new ComboBox<>();
                                                Label title = new Label("Suggested Majors for student: " + nameS);
                                                VBox vbox2 = new VBox(10, title, suggestedMajors);
                                                Button ok1 = new Button("Confrim");
                                                DoubleLinkedList suggests = new DoubleLinkedList();
                                                for (int i = 0; i < list.size(); i++) {
                                                    if (((Major) list.get(i).getData()).checkStudent(new Student(idS, nameS, tgS, ptS, ""))) {

                                                        suggests.addSorted(list.get(i).getData());
                                                    }
                                                }
                                                for (int i = 0; i < suggests.size(); i++)
                                                    suggestedMajors.getItems().add(((Major) suggests.get(i).getData()).getName());
                                                Button cancel1 = new Button("Dont add student");
                                                Stage sug = new Stage();
                                                HBox hbox = new HBox(10, ok1, cancel1);
                                                VBox vbox3 = new VBox(20, vbox2, hbox);
                                                Scene scene2 = new Scene(vbox3, 400, 400);
                                                sug.setScene(scene2);
                                                sug.show();
                                                ok1.setOnAction(event -> {
                                                    if (suggestedMajors.getSelectionModel().getSelectedItem() == null) {
                                                        Label error = new Label("nothing selected");
                                                        Stage Estage = new Stage();
                                                        BorderPane eroot = new BorderPane();
                                                        eroot.setCenter(error);
                                                        Estage.setScene(new Scene(eroot, 300, 300));
                                                        Estage.show();
                                                    } else {
                                                        ((Major) list.find(suggestedMajors.getSelectionModel().getSelectedItem().trim()).getData()).addSorted(new Student(idS, nameS, tgS, ptS, ""));
                                                        insertStage.close();
                                                        suggestedMajors.getItems().clear();
                                                        for (int i = 0; i < suggests.size(); i++)
                                                            suggests.removeLast();
                                                        tableView.getItems().clear();
                                                        for (int i = 0; i < ((Major) current.getData()).getList().size(); i++)
                                                            tableView.getItems().add(((Student) ((Major) current.getData()).getList().get(i).getData()));

                                                    }
                                                });
                                                cancel1.setOnAction(event -> {
                                                    ((Major) current.getData()).addSorted(s);
                                                    sug.close();
                                                });
                                            } else {

                                                tableView.getItems().clear();
                                                for (int i = 0; i < ((Major) current.getData()).getList().size(); i++)
                                                    tableView.getItems().add(((Student) ((Major) current.getData()).getList().get(i).getData()));

                                            }

                                            insertStage.close();

                                        } catch (Exception E) {
                                            Label error = new Label("error parsing");
                                            Stage Estage = new Stage();
                                            BorderPane eroot = new BorderPane();
                                            eroot.setCenter(error);
                                            Estage.setScene(new Scene(eroot, 300, 300));
                                            Estage.show();
                                        }
                                    } else {
                                        Label error = new Label("ID already taken");
                                        Stage Estage = new Stage();
                                        BorderPane eroot = new BorderPane();
                                        eroot.setCenter(error);
                                        Estage.setScene(new Scene(eroot, 300, 300));
                                        Estage.show();
                                    }
                                }

                            }

                        });
                        Button ok = new Button("OK");
                        Button cancel = new Button("Cancel");
                        GridPane grid = new GridPane();
                        grid.add(ID, 0, 0);
                        grid.add(idt, 1, 0);
                        grid.add(name, 0, 1);
                        grid.add(namet, 1, 1);
                        grid.add(tg, 0, 2);
                        grid.add(tgt, 1, 2);
                        grid.add(pt, 0, 3);
                        grid.add(ptt, 1, 3);
                        grid.add(majorl, 0, 4);
                        grid.add(majort, 1, 4);
                        grid.add(ok, 0, 5);
                        grid.add(cancel, 1, 5);
                        grid.add(generate, 2, 4);
                        Scene scene1 = new Scene(grid, 400, 400);
                        insertStage.setScene(scene1);
                        insertStage.show();
                        cancel.setOnAction(even -> {
                            insertStage.close();
                        });
                        ok.setOnAction(even -> {
                            String nameS = namet.getText().trim();
                            if (nameS.isEmpty()) {
                                Label error = new Label("Empty");
                                Stage Estage = new Stage();
                                BorderPane eroot = new BorderPane();
                                eroot.setCenter(error);
                                Estage.setScene(new Scene(eroot, 300, 300));
                                Estage.show();
                            } else {
                                String idS = idt.getText().trim();
                                if (idS.isEmpty()) {
                                    Label error = new Label("Empty");
                                    Stage Estage = new Stage();
                                    BorderPane eroot = new BorderPane();
                                    eroot.setCenter(error);
                                    Estage.setScene(new Scene(eroot, 300, 300));
                                    Estage.show();
                                } else {
                                    if (list.isValidStudent(idS)) {
                                        try {
                                            double tgS = Double.parseDouble(tgt.getText().trim());
                                            double ptS = Double.parseDouble(ptt.getText().trim());
                                            DoubleNode majorN = list.find(majort.getText().trim());
                                            if (majorN != null) {
                                                boolean flag = ((Major) majorN.getData()).addSorted(new Student(idS, nameS, tgS, ptS, ((Major) current.getData()).getName()));
                                                if (flag == false) {
                                                    ComboBox<String> suggestedMajors = new ComboBox<>();
                                                    Label title = new Label("Suggested Majors for student: " + nameS);
                                                    VBox vbox2 = new VBox(10, title, suggestedMajors);
                                                    Button ok1 = new Button("Confrim");
                                                    DoubleLinkedList suggests = new DoubleLinkedList();
                                                    for (int i = 0; i < list.size(); i++) {
                                                        if (((Major) list.get(i).getData()).checkStudent(new Student(idS, nameS, tgS, ptS, ""))) {
                                                            suggests.addSorted(list.get(i).getData());
                                                        }
                                                    }
                                                    for (int i = 0; i < suggests.size(); i++)
                                                        suggestedMajors.getItems().add(((Major) suggests.get(i).getData()).getName());
                                                    Button cancel1 = new Button("Dont add student");
                                                    Stage sug = new Stage();
                                                    HBox hbox = new HBox(10, ok1, cancel1);
                                                    VBox vbox3 = new VBox(20, vbox2, hbox);
                                                    Scene scene2 = new Scene(vbox3, 400, 400);
                                                    sug.setScene(scene2);
                                                    sug.show();
                                                    ok1.setOnAction(event -> {
                                                        if (suggestedMajors.getSelectionModel().getSelectedItem() == null) {
                                                            Label error = new Label("Nothing selected");
                                                            Stage Estage = new Stage();
                                                            BorderPane eroot = new BorderPane();
                                                            eroot.setCenter(error);
                                                            Estage.setScene(new Scene(eroot, 300, 300));
                                                            Estage.show();
                                                        } else {
                                                            ((Major) list.find(suggestedMajors.getSelectionModel().getSelectedItem().trim()).getData()).addSorted(new Student(idS, nameS, tgS, ptS, ""));
                                                            insertStage.close();
                                                            suggestedMajors.getItems().clear();
                                                            for (int i = 0; i < suggests.size(); i++)
                                                                suggests.removeLast();
                                                            tableView.getItems().clear();
                                                            for (int i = 0; i < ((Major) current.getData()).getList().size(); i++)
                                                                tableView.getItems().add(((Student) ((Major) current.getData()).getList().get(i).getData()));

                                                        }
                                                    });
                                                    cancel1.setOnAction(event -> {
                                                        sug.close();
                                                    });
                                                } else {
                                                    tableView.getItems().clear();
                                                    for (int i = 0; i < ((Major) current.getData()).getList().size(); i++)
                                                        tableView.getItems().add(((Student) ((Major) current.getData()).getList().get(i).getData()));

                                                }

                                                insertStage.close();
                                            }
                                        } catch (Exception E) {
                                            Label error = new Label("error parsing");
                                            Stage Estage = new Stage();
                                            BorderPane eroot = new BorderPane();
                                            eroot.setCenter(error);
                                            Estage.setScene(new Scene(eroot, 300, 300));
                                            Estage.show();
                                        }
                                    } else {
                                        Label error = new Label("ID already taken");
                                        Stage Estage = new Stage();
                                        BorderPane eroot = new BorderPane();
                                        eroot.setCenter(error);
                                        Estage.setScene(new Scene(eroot, 300, 300));
                                        Estage.show();
                                    }
                                }

                            }

                        });
                    }
                });
            });
            HBox vbox = new HBox(next, insert, delete, update, setxt, search, open, prev);


            vbox.setAlignment(Pos.CENTER);
            next.setOnAction(ev -> {
                if (current.getNextNode() == null) {
                    next.setDisable(true);
                    prev.setDisable(false);
                } else {
                    current = current.getNextNode();
                    prev.setDisable(false);
                    Major.setText("Major : " + ((Major) current.getData()).getName() + "Total number of Accepted :" + ((Major) current.getData()).accepted + " \n Total number of rejected by tawjihi low mark :" + ((Major) current.getData()).rejectedtw + " \n total number rejected by too low Placement test mark : " + ((Major) current.getData()).rejectedpt);
                }
            });
            prev.setOnAction(ev -> {
                if (current.getPrevNode() == null) {
                    next.setDisable(false);
                    prev.setDisable(true);

                } else {
                    current = current.getPrevNode();
                    next.setDisable(false);
                    Major.setText("Major : " + ((Major) current.getData()).getName() + "Total number of Accepted :" + ((Major) current.getData()).accepted + " \n Total number of rejected by tawjihi low mark :" + ((Major) current.getData()).rejectedtw + " \n total number rejected by too low Placement test mark : " + ((Major) current.getData()).rejectedpt);
                }
            });

            vbox.setSpacing(10);
            BorderPane root = new BorderPane();
            root.setBottom(vbox);
            Major.setAlignment(Pos.CENTER);
            root.setTop(Major);
            Scene scenemajor = new Scene(root, 800, 600);
            Stage stagemajor = new Stage();
            stagemajor.setScene(scenemajor);
            stagemajor.show();

        });
        statistics.setOnAction(ev -> {
            Stage stage1 = new Stage();
            TextArea area = new TextArea();
            int accepted = 0;
            int rejected = 0;
            for (int i = 0; i < list.size(); i++) {
                accepted += ((Major) list.get(i).getData()).accepted;
                rejected += ((Major) list.get(i).getData()).rejectedtw;
                rejected += ((Major) list.get(i).getData()).rejectedpt;
            }
            double accrate = ((double) accepted / (double) (accepted + rejected)) * 100.0;
            ComboBox<String> combo = new ComboBox<>();
            TextField field = new TextField();
            Button get = new Button("Get top students");
            area.setText("Total accepted studented : " + accepted + "\nTotal rejected students : " + rejected + "\nAcceptance Rate : " + accrate + "\n ");

            HBox hbox = new HBox(10, combo, field, get);
            hbox.setAlignment(Pos.CENTER);
            BorderPane roo2t = new BorderPane();
            roo2t.setCenter(area);
            roo2t.setBottom(hbox);
            stage1.setScene(new Scene(roo2t, 600, 600));
            stage1.show();
            for (int i = 0; i < list.size(); i++)
                combo.getItems().add(((Major) list.get(i).getData()).getName());
            get.setOnAction(eve -> {
                if (combo.getSelectionModel().getSelectedItem() == null) {
                    System.out.println("Nothing selected");
                } else {
                    try {
                        int count = Integer.parseInt(field.getText().trim().toString());
                        String str = new String();
                        for (int i = 0; i < count && i < ((Major) list.find(combo.getSelectionModel().getSelectedItem()).getData()).getList().size(); i++)
                            str += ((Major) list.find(combo.getSelectionModel().getSelectedItem()).getData()).getList().get(i).toString();
                        area.appendText(str);
                    } catch (Exception E) {
                        System.out.println("error parsing");
                    }
                }
            });
        });
    savetoFile.setOnAction(e->{
            try {
                FileWriter writer = new FileWriter(new File("Students.txt"));
                FileWriter writeMajor = new FileWriter(new File("Majors.txt"));
                for (int i = 0; i < list.size(); i++) {
                    writeMajor.write(list.get(i).getData().toString() + "\n");
                    for (int j = 0; j < ((Major) list.get(i).getData()).getList().size(); j++)
                        writer.write(((Major) list.get(i).getData()).getList().get(j).getData().toString() + "\n");
                }
                writer.close();
                ;
                writeMajor.close();
            } catch (IOException ex) {

            }
    });

    }


    public static void main(String[] args) {
        launch();
    }
}