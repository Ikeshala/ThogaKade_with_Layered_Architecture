package dao.custom.impl;

import dao.custom.OrderDao;
import db.DBConnection;
import dto.CartDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    @Override
    public boolean save(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<OrderDto> getAllOrders() throws SQLException, ClassNotFoundException {
        List<OrderDto> orders = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance().getConnection()) {
            String sql = "SELECT * FROM orders";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String orderId = resultSet.getString("order_id");
                    String date = resultSet.getString("date");
                    String customerId = resultSet.getString("customer_id");

                    List<OrderDetailsDto> orderDetails = getOrderDetailsByOrderId(orderId);

                    OrderDto orderDto = new OrderDto(orderId, date, customerId, orderDetails);
                    orders.add(orderDto);
                }
            }
        }

        return orders;
    }

    private List<OrderDetailsDto> getOrderDetailsByOrderId(String orderId) throws SQLException, ClassNotFoundException {
        List<OrderDetailsDto> orderDetailsList = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance().getConnection()) {
            String sql = "SELECT * FROM order_details WHERE order_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, orderId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String itemCode = resultSet.getString("item_code");
                        int quantity = resultSet.getInt("quantity");
                        double unitPrice = resultSet.getDouble("unit_price");

                        OrderDetailsDto orderDetailsDto = new OrderDetailsDto(orderId, itemCode, quantity, unitPrice);
                        orderDetailsList.add(orderDetailsDto);
                    }
                }
            }
        }

        return orderDetailsList;
    }
}