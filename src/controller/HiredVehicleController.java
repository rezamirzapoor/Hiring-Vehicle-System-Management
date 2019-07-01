package controller;

import database.DbConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Bill;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HiredVehicleController extends Controller {


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label totalIncome;
    @FXML
    private TableView<Bill> tbData;

    @FXML
    private TableColumn<Bill, String> userPhone;

    @FXML
    private TableColumn<Bill, Integer> id;

    @FXML
    private TableColumn<Bill, String> model;

    @FXML
    private TableColumn<Bill, String> factory;

    @FXML
    private TableColumn<Bill, Integer> createYear;

    @FXML
    private TableColumn<Bill, String> description;

    @FXML
    private TableColumn<Bill, String> vehicleType;


    @FXML
    private TableColumn<Bill, String> getDate;

    @FXML
    private TableColumn<Bill, String> returnDate;

    @FXML
    private TableColumn<Bill, Integer> incomeMoney;

    @FXML
    private ComboBox<Integer> garageSelector;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    public void initialize(URL location, ResourceBundle resources) {
        filterComboBox.getItems().add("All");
        filterComboBox.setValue("All");
        loadBasicType(filterComboBox);
        loadTable(tbData, totalIncome, filterComboBox.getValue());

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadDashboardScene();
            }
        });

        filterComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadTable(tbData, totalIncome, filterComboBox.getValue());
            }
        });
    }

    private void loadTable(TableView<Bill> tbData, Label totalIncome, String type) {
        int income = 0;
        tbData.getItems().clear();
        userPhone.setCellValueFactory(new PropertyValueFactory<>("userPhone"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        factory.setCellValueFactory(new PropertyValueFactory<>("factory"));
        createYear.setCellValueFactory(new PropertyValueFactory<>("createYear"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        vehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        getDate.setCellValueFactory(new PropertyValueFactory<>("getDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        incomeMoney.setCellValueFactory(new PropertyValueFactory<>("incomeMoney"));

        DbConnection db = new DbConnection();
        for (int i = 0; i < db.getBill(filterComboBox.getValue()).size(); i++) {
            Bill bill = db.getBill(filterComboBox.getValue()).get(i);
            income += bill.getIncomeMoney();
            tbData.getItems().add(bill);
        }
        totalIncome.setText(String.valueOf(income));
        db.close();
        // Set Default Selected Row
        tbData.getSelectionModel().selectFirst();
    }

    private void loadDashboardScene() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/Dashboard.fxml"));
            anchorPane.getChildren().removeAll();
            anchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
