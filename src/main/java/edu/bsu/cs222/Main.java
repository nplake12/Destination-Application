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

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.MapOptions;

public class Main extends Application implements MapComponentInitializedListener{

    private VBox leftSearchArea;
    private HBox placeSearchArea;
    private HBox parent;

    private TableView<Place> table;
    private TableColumn<Place, String> nameColumn;
    private TableColumn<Place, String> addressColumn;
    private TableColumn<Place, String> distanceColumn;
    private TableColumn<Place, String> ratingColumn;

    private GoogleMapView mapView;
    private GoogleMap map;

    private String originLocation;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Destination Application");

        mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);
        leftSearchArea = initializeVBox();

        parent = new HBox();
        parent.getChildren().addAll(leftSearchArea, mapView);

        table = initializeTable();
        GridPane buttonGrid = initializeButton();
        Button button = (Button) buttonGrid.getChildren().get(0);
        setSearchButtonFunctionality(button);

        leftSearchArea.getChildren().addAll(buttonGrid, table);
        Scene homePage = new Scene(parent);
        primaryStage.setScene(homePage);
        primaryStage.show();
    }

    public void mapInitialized() {
    }

    private VBox initializeVBox(){

        leftSearchArea = new VBox();
        leftSearchArea.setPrefWidth(755);
        leftSearchArea.getChildren().add(new Label("Enter what you would like to search for"));
        placeSearchArea = initializeHBox();
        return leftSearchArea;
    }

    private HBox initializeHBox(){

        placeSearchArea = new HBox();
        placeSearchArea.setPadding(new Insets(10));

        final Label locationTextFieldLabel = new Label("Search Entry: ");
        final TextField locationTextField = new TextField();

        final Label radiusTextFieldLabel = new Label(" Radius in Miles: ");
        final TextField radiusTextField = new TextField();

        final Label typeChoiceBoxLabel = new Label(" Place Type: ");
        final ChoiceBox<String> typeChoiceBox = new ChoiceBox<String>();
        typeChoiceBox.getItems().addAll("ATM","Bank","Bar","Bowling Alley","Clothing Store",
                "Doctor","Gas Station","Hospital","Lodging","Park",
                "Parking","Post Office","Restaurant","School",
                "Shopping Mall","University","Zoo");
        typeChoiceBox.setValue("ATM");

        placeSearchArea.getChildren().addAll(locationTextFieldLabel,locationTextField,
                radiusTextFieldLabel, radiusTextField,
                typeChoiceBoxLabel, typeChoiceBox);
        leftSearchArea.getChildren().add(placeSearchArea);
        return placeSearchArea;
    }

    private TableView<Place> initializeTable(){

        final TableView<Place> table = new TableView<Place>();
        table.setPrefHeight(503);

        nameColumn = new TableColumn<Place, String>("Name");
        nameColumn.setPrefWidth(250);

        addressColumn = new TableColumn<Place, String>("Address");
        addressColumn.setPrefWidth(280);

        distanceColumn = new TableColumn<Place, String>("Distance");
        distanceColumn.setPrefWidth(100);

        ratingColumn = new TableColumn<Place, String>("Rating");
        ratingColumn.setPrefWidth(125);

        table.setEditable(true);
        table.getColumns().add(nameColumn);
        table.getColumns().add(addressColumn);
        table.getColumns().add(distanceColumn);
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

    private void setSearchButtonFunctionality(Button searchButton) throws IOException{

        final TextField locationTextField = (TextField) placeSearchArea.getChildren().get(1);
        final TextField radiusTextField = (TextField) placeSearchArea.getChildren().get(3);
        final ChoiceBox typeChoiceBox = (ChoiceBox) placeSearchArea.getChildren().get(5);

        searchButton.setOnAction(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent event) {

                LinkedList<String> applicationInput = new LinkedList<String>();
                if(locationTextField.getText().length() == 0 || radiusTextField.getText().length() == 0){
                    alert("It seems that one or more of the required fields are empty! Please fill them out and try again.");
                    return;
                }
                originLocation = locationTextField.getText().replaceAll(" ","");
                applicationInput.add(originLocation);
                applicationInput.add(Double.toString(Double.parseDouble(radiusTextField.getText()) * 1609.34));
                applicationInput.add(typeChoiceBox.getValue().toString());
                PlaceParser parser = new PlaceParser();
                List<Place> places = new LinkedList<Place>();
                try {

                    parser.constructURL(applicationInput);
                    places = parser.parse();
                    if(places.size() == 0){
                        alert("The radius for this specific place and place type seems to be too small!");
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                }
                ObservableList<Place> placeObservableList = FXCollections.observableList(places);

                nameColumn.setCellValueFactory(
                        new PropertyValueFactory<Place, String>("name")
                );
                addressColumn.setCellValueFactory(
                        new PropertyValueFactory<Place, String>("address")
                );
                distanceColumn.setCellValueFactory(
                        new PropertyValueFactory<Place, String>("distance")
                );
                ratingColumn.setCellValueFactory(
                        new PropertyValueFactory<Place, String>("rating")
                );
                table.setItems(placeObservableList);
                try {
                    setMapMarkers(placeObservableList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setMapMarkers(ObservableList<Place> placeObservableList) throws IOException{

        GeocodeParser locationParser = new GeocodeParser();
        String originCoordinates = locationParser.parse(originLocation);

        MapInitializer mapInitializer = new MapInitializer.Builder()
                .setPlaceObservableList(placeObservableList)
                .build();

        MapOptions mapOptions = mapInitializer.initializeGoogleMap(originCoordinates, placeObservableList);
        map = mapInitializer.addPlacesToGoogleMap(mapView.createMap(mapOptions));
    }

    private void alert(String errorText){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText(errorText);
            errorAlert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}