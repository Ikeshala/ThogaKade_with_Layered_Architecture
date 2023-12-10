package dao.custom;

import dao.CrudDao;
import dto.OrderDto;

import java.sql.SQLException;

public interface OrdersDao extends CrudDao<OrderDto> {
    OrderDto getLastOrder() throws SQLException, ClassNotFoundException;
}
