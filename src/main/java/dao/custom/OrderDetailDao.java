package dao.custom;

import dao.CrudDao;
import dto.OrderDetailsDto;
import entity.Customer;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDao extends CrudDao<OrderDetail> {
    boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException;
}
