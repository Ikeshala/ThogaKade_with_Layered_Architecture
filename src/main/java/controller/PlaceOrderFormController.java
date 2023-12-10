package controller;

import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrdersBo;
import bo.custom.impl.CustomerBoImpl;
import bo.custom.impl.ItemBoImpl;
import bo.custom.impl.OrdersBoImpl;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.custom.OrdersDao;
import dao.custom.impl.OrdersDaoImpl;
import dto.CustomersDto;
import dto.ItemsDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormController {

    @FXML
    private AnchorPane placeOrderPane;

    @FXML
    private JFXComboBox<?> cmbCustomerID;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXComboBox<?> cmbItemCode;

    @FXML
    private JFXTextField txtItemDescription;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtBuyingQuantity;

    @FXML
    private JFXTreeTableView<OrderTm> tblOrders;

    @FXML
    private TreeTableColumn colItemCode;

    @FXML
    private TreeTableColumn colItemDescription;

    @FXML
    private TreeTableColumn colAmount;

    @FXML
    private TreeTableColumn colQuantity;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblOrderId;
    private double total = 0;
    private List<CustomersDto> customers;
    private List<ItemsDto> items;
    private CustomerBo customerBo = new CustomerBoImpl();
    private OrdersBo ordersBo = new OrdersBoImpl();
    private ItemBo itemBo = new ItemBoImpl();
    private OrdersDao ordersDao = new OrdersDaoImpl();
    private ObservableList<OrderTm> tmList = FXCollections.observableArrayList();
    public void initialize(){
        colItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colItemDescription.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantity"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        loadCustomerIds();
        loadItemCodes();
        generateId();

        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, customerId) ->
            {
                for (CustomersDto dto:customers) {
                    if (dto.getId().equals(customerId)){
                        txtCustomerName.setText(dto.getName());
                    }

                }
            }
        );

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, itemCode) ->
            {
                for (ItemsDto dto:items) {
                    if (dto.getCode().equals(itemCode)){
                        txtItemDescription.setText(dto.getDescription());
                        txtUnitPrice.setText(String.format("%.2f",dto.getUnitPrice()));
                    }

                }
            }
        );
    }

    private void loadItemCodes() {
        try {
            items = itemBo.allItems();
            ObservableList list = FXCollections.observableArrayList();
            for (ItemsDto dto: items) {
                list.add(dto.getCode());
            }
            cmbItemCode.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        try {
            customers = customerBo.allCustomers();
            ObservableList list = FXCollections.observableArrayList();
            for (CustomersDto dto: customers) {
                list.add(dto.getId());
            }
            cmbCustomerID.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void AddToCartButtonOnAction(ActionEvent event) {
        JFXButton button = new JFXButton("DELETE");
        button.setFont(Font.font("System", FontWeight.BOLD, 13));
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setBlendMode(BlendMode.HARD_LIGHT);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-border-color:   #6B240C; -fx-border-radius: 5; -fx-background-color:  #6B240C;");

        OrderTm orderTm = new OrderTm(
                cmbItemCode.getValue().toString(),
                txtItemDescription.getText(),
                Integer.parseInt(txtBuyingQuantity.getText()),
                Double.parseDouble(txtUnitPrice.getText())*Integer.parseInt(txtBuyingQuantity.getText()),
                button
        );

        button.setOnAction(actionEvent -> {
            tmList.remove(orderTm);
            tblOrders.refresh();
            total -= orderTm.getAmount();
            lblTotal.setText(String.format("%.2f",total));
        });

        boolean isExist = false;

        for (OrderTm order:tmList) {
            if (order.getCode().equals(orderTm.getCode())){
                order.setQuantity(order.getQuantity() + orderTm.getQuantity());
                order.setAmount(order.getAmount() + orderTm.getAmount());
                isExist = true;
                total += orderTm.getAmount();
            }
        }

        if (!isExist){
            tmList.add(orderTm);
            total += orderTm.getAmount();
        }
        lblTotal.setText(String.format("%.2f",total));


        TreeItem<OrderTm> treeObject = new RecursiveTreeItem<OrderTm>(tmList, RecursiveTreeObject::getChildren);
        tblOrders.setRoot(treeObject);
        tblOrders.setShowRoot(false);
    }

    @FXML
    void BackButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) placeOrderPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateId(){
        try {
            lblOrderId.setText(ordersBo.generateId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void PlaceOrderButtonOnAction(ActionEvent event) {
        List<OrderDetailsDto> list = new ArrayList<>();
        for (OrderTm tm:tmList) {
            list.add(new OrderDetailsDto(
                    lblOrderId.getText(),
                    tm.getCode(),
                    tm.getQuantity(),
                    tm.getAmount()/ tm.getQuantity()
            ));

        }
        boolean isSaved = false;
        try {
            isSaved = ordersBo.saveOrder(new OrderDto(
                    lblOrderId.getText(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    cmbCustomerID.getValue().toString(),
                    list
            ));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (isSaved){
            new Alert(Alert.AlertType.INFORMATION,"Order Saved!").show();
            clearFields();
            
        }else {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }
    }

    private void clearFields() {
        cmbCustomerID.getSelectionModel().clearSelection();
        txtCustomerName.clear();
        cmbItemCode.getSelectionModel().clearSelection();
        txtItemDescription.clear();
        txtUnitPrice.clear();
        txtBuyingQuantity.clear();
        tblOrders.getRoot().getChildren().clear();
        lblTotal.setText("0.00");
        generateId();
    }
}
