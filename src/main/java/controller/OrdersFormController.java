package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class OrdersFormController {

    @FXML
    private ImageView ordersPane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    private JFXTreeTableView<?> tblOrders;

    @FXML
    private TreeTableColumn colOrderId;

    @FXML
    private TreeTableColumn colDate;

    @FXML
    private TreeTableColumn colCustomerId;

    @FXML
    private TreeTableColumn colCustomerName;

    @FXML
    private TreeTableColumn colTotal;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private JFXTextField txtSearchOrder;

    @FXML
    private JFXTreeTableView<?> tblOrderDetails;

    @FXML
    private TreeTableColumn colOrderIdDetailTable;

    @FXML
    private TreeTableColumn colItemCode;

    @FXML
    private TreeTableColumn colDescription;

    @FXML
    private TreeTableColumn colQuantity;

    @FXML
    private TreeTableColumn colUnitPrice;

    @FXML
    private TreeTableColumn colAmount;

    @FXML
    void BackButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ordersPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void RefreshButtonOnAction(ActionEvent event) {
        loadOrdersTable();
        tblOrders.refresh();
        clearFields();
    }

    private void clearFields() {
    }

    private void loadOrdersTable() {

    }

}
