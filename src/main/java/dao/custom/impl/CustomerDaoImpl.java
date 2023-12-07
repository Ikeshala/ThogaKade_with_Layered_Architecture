package dao.custom.impl;

import db.DBConnection;
import dto.CustomersDto;
import dao.custom.CustomerDao;
import entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public boolean searchCustomer(String id) {
        return false;
    }
    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer VALUES(?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, entity.getId());
        pstm.setString(2, entity.getName());
        pstm.setString(3, entity.getAddress());
        pstm.setDouble(4, entity.getSalary());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET name=?, address=?, salary=? WHERE id=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1,entity.getName());
        pstm.setString(2,entity.getAddress());
        pstm.setDouble(3,entity.getSalary());
        pstm.setString(4,entity.getId());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer WHERE id=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,value);
        return pstm.executeUpdate()>0;
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        PreparedStatement ptsm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();

        while (resultSet.next()) {
            list.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }

        return list;
    }
}
