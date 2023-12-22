package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import dto.OrderDetailsDto;
import dto.CartDto;
import dto.OrderDto;
import dto.tm.OrderDetailsTm;
import dto.tm.OrderTm;
import dto.tm.CartTm;
import entity.OrderDetail;
import entity.Orders;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class OrdersFormController {

    @FXML
    private ImageView ordersPane;

    @FXML
    private JFXTreeTableView<OrderTm> tblOrders;

    @FXML
    private TreeTableColumn colOrderId;

    @FXML
    private TreeTableColumn colDate;

    @FXML
    private TreeTableColumn colCustomerName;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private JFXTextField txtSearchOrder;

    @FXML
    private JFXTreeTableView<OrderDetailsTm> tblOrderDetails;

    @FXML
    private TreeTableColumn colItemCode;

    @FXML
    private TreeTableColumn colDescription;

    @FXML
    private TreeTableColumn colQuantity;

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
        tblOrders.refresh();
    }

    public void initialize(){
        colOrderId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colCustomerName.setCellValueFactory(new TreeItemPropertyValueFactory<>("custName"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        colItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantity"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));

        loadOrders();

        tblOrders.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            if (newValue!=null){
                loadOrderDetails(newValue);
            }
        });

        txtSearchOrder.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                String lowerCaseNewValue = newValue.toLowerCase();
                tblOrders.setPredicate(new Predicate<TreeItem<OrderTm>>() {
                    @Override
                    public boolean test(TreeItem<OrderTm> orderTmTreeItem) {
                        String lowerCaseCode = orderTmTreeItem.getValue().getId().toLowerCase();
                        String lowerCaseDescription = orderTmTreeItem.getValue().getCustName().toLowerCase();

                        return lowerCaseCode.contains(lowerCaseNewValue) ||
                                lowerCaseDescription.contains(lowerCaseNewValue);
                    }
                });
            }
        });
    }

    private void loadOrderDetails(TreeItem<OrderTm> newValue) {
        ObservableList<OrderDetailsTm> tmList = FXCollections.observableArrayList();
        try {
            List<OrderDetailsDto> list = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM orderdetail WHERE orderId=?");
            pstm.setString(1,newValue.getValue().getId());
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {

                list.add(new OrderDetailsDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getDouble(4)
                ));
            }

            for (OrderDetailsDto detail:list) {
                pstm = connection.prepareStatement("SELECT description FROM item WHERE code=?");
                pstm.setString(1,detail.getItemCode());
                ResultSet rsSet = pstm.executeQuery();

                rsSet.next();

                tmList.add(new OrderDetailsTm(
                        detail.getItemCode(),
                        rsSet.getString(1),
                        detail.getQuantity(),
                        detail.getUnitPrice()*detail.getQuantity()
                ));
            }

            TreeItem<OrderDetailsTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblOrderDetails.setRoot(treeItem);
            tblOrderDetails.setShowRoot(false);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadOrders() {
        ObservableList<OrderTm> tmList = FXCollections.observableArrayList();
        try {
            List<OrderDto> list = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM orders");
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                list.add(new OrderDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }

            for (OrderDto order:list) {
                JFXButton button = new JFXButton("DELETE");
                button.setFont(Font.font("System", FontWeight.BOLD, 13));
                button.setButtonType(JFXButton.ButtonType.RAISED);
                button.setBlendMode(BlendMode.HARD_LIGHT);
                button.setTextAlignment(TextAlignment.CENTER);
                button.setTextFill(Color.WHITE);
                button.setStyle("-fx-border-color:   #AA530E; -fx-border-radius: 5; -fx-background-color:   #AA530E;");

                button.setOnAction(actionEvent -> {
                    try {
                        PreparedStatement pst = connection.prepareStatement("DELETE FROM orders WHERE id=?");
                        pst.setString(1, order.getOrderId());
                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + order.getOrderId() + " order ? ", ButtonType.YES, ButtonType.NO).showAndWait();
                        if (buttonType.get() == ButtonType.YES){
                            if (pst.executeUpdate()>0){
                                new Alert(Alert.AlertType.INFORMATION,"Order Deleted..!").show();
                                loadOrders();
                            }else{
                                new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                PreparedStatement pt = connection.prepareStatement("SELECT name FROM customer WHERE id=?");
                pt.setString(1,order.getCustomerId());
                ResultSet rst = pt.executeQuery();
                rst.next();

                tmList.add(new OrderTm(
                        order.getOrderId(),
                        order.getDate(),
                        rst.getString(1),
                        button
                ));
            }

            TreeItem<OrderTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblOrders.setRoot(treeItem);
            tblOrders.setShowRoot(false);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
