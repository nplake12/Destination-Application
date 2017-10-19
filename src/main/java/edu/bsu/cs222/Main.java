package edu.bsu.cs222;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox parent = new VBox();
        parent.setPrefWidth(500);
        parent.getChildren().add(new Label("Enter what you would like to search for"));

        HBox placeSearchArea = new HBox(new Label("Search Entry:"));
        placeSearchArea.setPadding(new Insets(10));
        final TextField locationTextField = new TextField();
        placeSearchArea.getChildren().add(locationTextField);


        parent.getChildren().add(placeSearchArea);

        final TableView<Place> table = new TableView<Place>();
        final TableColumn<Place, String> nameColumn = new TableColumn<Place, String>("Name");
        nameColumn.setPrefWidth(200);
        final TableColumn<Place, String> addressColumn = new TableColumn<Place, String>("Address");
        addressColumn.setPrefWidth(250);
        final TableColumn<Place, String> ratingColumn = new TableColumn<Place, String>("Rating");
        ratingColumn.setPrefWidth(50);
        table.setEditable(true);
        table.getColumns().add(nameColumn);
        table.getColumns().add(addressColumn);
        table.getColumns().add(ratingColumn);

        GridPane buttonGrid = new GridPane();
        Button button = new Button("Search");
        buttonGrid.add(button, 0, 2);
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setPadding(new Insets(0,0,10,0));
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String location = locationTextField.getText();
                PlaceParser parser = new PlaceParser();
                List<Place> places = new LinkedList<Place>();
                try {
                    places = parser.parse(location);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ObservableList<Place> list = FXCollections.observableList(places);
                nameColumn.setCellValueFactory(
                        new PropertyValueFactory<Place, String>("name")
                );
                addressColumn.setCellValueFactory(
                        new PropertyValueFactory<Place, String>("address")
                );
                ratingColumn.setCellValueFactory(
                        new PropertyValueFactory<Place, String>("rating")
                );
                table.setItems(list);
            }
        });
        parent.getChildren().addAll(buttonGrid, table);
        Scene homePage = new Scene(parent);
        primaryStage.setScene(homePage);
        primaryStage.show();
    }
}