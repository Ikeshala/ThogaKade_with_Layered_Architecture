package model.impl;

import db.DBConnection;
import dto.CustomersDto;
import model.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModelImpl implements CustomerModel {
    @Override
    public boolean saveCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer VALUES(?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setDouble(4, dto.getSalary());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean updateCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET name=?, address=?, salary=? WHERE id=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1,dto.getName());
        pstm.setString(2,dto.getAddress());
        pstm.setDouble(3,dto.getSalary());
        pstm.setString(4,dto.getId());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean searchCustomer(String id) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer WHERE id=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    @Override
    public List<CustomersDto> allCustomers() throws SQLException, ClassNotFoundException {
        List<CustomersDto> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        PreparedStatement ptsm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();

            while (resultSet.next()) {
                list.add(new CustomersDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)
                ));
            }

        return list;
    }
}
