package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {

    public ImageView pane;
    @FXML
    private JFXButton btnCustomers;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private JFXButton btnItems;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;
    public void initialize() {
        // Display current date
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        lblDate.setText(formattedDate);

        // Display current time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            lblTime.setText(formattedTime);
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    @FXML
    void CustomersButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"))));
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("Customers Form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ItemsButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemsForm.fxml"))));
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("Items Form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OrdersButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrdersForm.fxml"))));
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("Orders Form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void PlaceOrderButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrderForm.fxml"))));
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("Place Order Form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
