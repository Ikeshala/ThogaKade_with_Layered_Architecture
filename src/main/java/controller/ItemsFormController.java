package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import bo.custom.impl.ItemBoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import db.DBConnection;
import dto.ItemsDto;
import dto.tm.ItemsTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.function.Predicate;

public class ItemsFormController {

    @FXML
    private ImageView itemPane;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtItemDescription;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtQuantityOnHand;

    @FXML
    private JFXTextField txtSearchItem;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXTreeTableView<ItemsTm> tblItems;

    @FXML
    private TreeTableColumn colItemCode;

    @FXML
    private TreeTableColumn colItemDescription;

    @FXML
    private TreeTableColumn colUnitPrice;

    @FXML
    private TreeTableColumn colQuantity;

    @FXML
    private TreeTableColumn colOption;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    public void initialize(){
        colItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colItemDescription.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantity"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadItemsTable();

        txtSearchItem.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                String lowerCaseNewValue = newValue.toLowerCase();
                tblItems.setPredicate(new Predicate<TreeItem<ItemsTm>>() {
                    @Override
                    public boolean test(TreeItem<ItemsTm> itemsTmTreeItem) {
                        String lowerCaseCode = itemsTmTreeItem.getValue().getCode().toLowerCase();
                        String lowerCaseDescription = itemsTmTreeItem.getValue().getDescription().toLowerCase();

                        return lowerCaseCode.contains(lowerCaseNewValue) ||
                                lowerCaseDescription.contains(lowerCaseNewValue);
                    }
                });
            }
        });
    }

    private void loadItemsTable() {
        ObservableList<ItemsTm> tmList = FXCollections.observableArrayList();

        try {
            List<ItemsDto> dtoList = itemBo.allItems();

            for (ItemsDto dto : dtoList) {
                JFXButton button = new JFXButton("DELETE");
                button.setFont(Font.font("System", FontWeight.BOLD, 13));
                button.setButtonType(JFXButton.ButtonType.RAISED);
                button.setBlendMode(BlendMode.HARD_LIGHT);
                button.setTextAlignment(TextAlignment.CENTER);
                button.setTextFill(Color.WHITE);
                button.setStyle("-fx-border-color: #043800; -fx-border-radius: 5; -fx-background-color:   #043800;");

                ItemsTm itemsTm = new ItemsTm(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getUnitPrice(),
                        dto.getQuantity(),
                        button
                );

                button.setOnAction(actionEvent -> {
                    deleteItem(itemsTm.getCode());
                });

                tmList.add(itemsTm);
            }
            TreeItem<ItemsTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblItems.setRoot(treeItem);
            tblItems.setShowRoot(false);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        btnUpdate.setDisable(true);
        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setData(newValue != null ? newValue.getValue() : null);
        });
    }

    private void setData(ItemsTm itemsTm) {
        if (itemsTm != null) {
            txtItemCode.setEditable(false);
            btnUpdate.setDisable(false);

            txtItemCode.setText(itemsTm.getCode());
            txtItemDescription.setText(itemsTm.getDescription());
            txtUnitPrice.setText(String.valueOf(itemsTm.getUnitPrice()));
            txtQuantityOnHand.setText(String.valueOf(itemsTm.getQuantity()));
        }
    }

    private void deleteItem(String code) {
        try {
            boolean isDeleted = itemBo.deleteItem(code);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadItemsTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BackButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) itemPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void RefreshButtonOnAction(ActionEvent event) {
        loadItemsTable();
        tblItems.refresh();
        clearFields();
    }

    @FXML
    void SaveButtonOnAction(ActionEvent event) {
        if (txtItemCode.getText().isEmpty() || txtItemDescription.getText().isEmpty() ||
                txtUnitPrice.getText().isEmpty() || txtQuantityOnHand.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields!").show();
            return;
        }

        try {
            ItemsDto itemsDto = new ItemsDto(
                    txtItemCode.getText(),
                    txtItemDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQuantityOnHand.getText())
            );

            boolean isSaved = itemBo.saveItem(itemsDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Item Saved!").show();
                loadItemsTable();
                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR, "Duplicate Entry!").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        tblItems.refresh();

        txtItemCode.clear();
        txtItemDescription.clear();
        txtUnitPrice.clear();
        txtQuantityOnHand.clear();

        txtItemCode.setEditable(true);
    }

    @FXML
    void UpdateButtonOnAction(ActionEvent event) {
        ItemsTm selectedItem = tblItems.getSelectionModel().getSelectedItem().getValue();

        try {
            ItemsDto updatedItem = new ItemsDto(
                    selectedItem.getCode(),
                    txtItemDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQuantityOnHand.getText())
            );

            boolean isUpdated = itemBo.updateItem(updatedItem);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Item Updated!").show();
                loadItemsTable();
                clearFields();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void ReportButtonOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/itemReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
