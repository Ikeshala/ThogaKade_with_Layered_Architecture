package model.impl;

import db.DBConnection;
import dto.ItemsDto;
import model.ItemModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModelImpl implements ItemModel {
    @Override
    public boolean saveItem(ItemsDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO item VALUES(?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, dto.getCode());
        pstm.setString(2, dto.getDescription());
        pstm.setDouble(3, dto.getUnitPrice());
        pstm.setInt(4, dto.getQuantity());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean updateItem(ItemsDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, dto.getDescription());
        pstm.setDouble(2, dto.getUnitPrice());
        pstm.setInt(3, dto.getQuantity());
        pstm.setString(4, dto.getCode());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean searchItem(String code) {
        return false;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM item WHERE code=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,code);
        return pstm.executeUpdate()>0;
    }

    @Override
    public ItemsDto getItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM item WHERE code=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,code);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return new ItemsDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }

    @Override
    public List<ItemsDto> allItems() throws SQLException, ClassNotFoundException {
        List<ItemsDto> list = new ArrayList<>();
        String sql = "SELECT * FROM item";

        PreparedStatement ptsm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();

            while (resultSet.next()) {
                list.add(new ItemsDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                ));
            }

        return list;
    }
}
