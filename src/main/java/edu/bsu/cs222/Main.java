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

    private VBox parent;
    private HBox placeSearchArea;
    private TableView<Place> table;
    private TableColumn<Place, String> nameColumn;
    private TableColumn<Place, String> addressColumn;
    private TableColumn<Place, String> ratingColumn;

    @Override
    public void start(Stage primaryStage) throws Exception {

        parent = initializeVBox();

        table = initializeTable();

        GridPane buttonGrid = initializeButton();
        Button button = (Button) buttonGrid.getChildren().get(0);
        setSearchButtonFunctionality(button);

        parent.getChildren().addAll(buttonGrid, table);
        Scene homePage = new Scene(parent);
        primaryStage.setScene(homePage);
        primaryStage.show();
    }

    private VBox initializeVBox(){
        parent = new VBox();
        parent.setPrefWidth(500);
        parent.getChildren().add(new Label("Enter what you would like to search for"));
        placeSearchArea = initializeHBox();
        return parent;
    }

    private HBox initializeHBox(){
        placeSearchArea = new HBox(new Label("Search Entry:"));
        placeSearchArea.setPadding(new Insets(10));
        final TextField locationTextField = new TextField();
        final Label radiusTextFieldLabel = new Label("Radius:");
        final TextField radiusTextField = new TextField();
        placeSearchArea.getChildren().addAll(locationTextField, radiusTextFieldLabel, radiusTextField);
        parent.getChildren().add(placeSearchArea);
        return placeSearchArea;
    }

    private TableView<Place> initializeTable(){
        final TableView<Place> table = new TableView<Place>();
        nameColumn = new TableColumn<Place, String>("Name");
        nameColumn.setPrefWidth(200);
        addressColumn = new TableColumn<Place, String>("Address");
        addressColumn.setPrefWidth(250);
        ratingColumn = new TableColumn<Place, String>("Rating");
        ratingColumn.setPrefWidth(50);

        table.setEditable(true);
        table.getColumns().add(nameColumn);
        table.getColumns().add(addressColumn);
        table.getColumns().add(ratingColumn);

        return table;
    }

    private GridPane initializeButton(){
        GridPane buttonGrid = new GridPane();
        Button button = new Button("Search");
        buttonGrid.add(button, 0, 2);
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setPadding(new Insets(0,0,10,0));
        return buttonGrid;
    }

    private void setSearchButtonFunctionality(Button searchButton){
        final TextField locationTextField = (TextField) placeSearchArea.getChildren().get(1);
        final TextField radiusTextField = (TextField) placeSearchArea.getChildren().get(3);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                LinkedList<String> applicationInput = new LinkedList<String>();
                applicationInput.add(locationTextField.getText());
                applicationInput.add(radiusTextField.getText());
                PlaceParser parser = new PlaceParser();
                List<Place> places = new LinkedList<Place>();
                try {
                    parser.constructURL(applicationInput);
                    places = parser.parse();
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
    }
}