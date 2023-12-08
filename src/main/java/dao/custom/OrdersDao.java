package dao.custom;

import dao.CrudDao;
import dto.OrderDto;
import entity.Orders;

import java.sql.SQLException;

public interface OrdersDao extends CrudDao<Orders> {
    OrderDto lastOrder() throws SQLException, ClassNotFoundException;
    boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException;

}
