package bo.custom;

import bo.SuperBo;
import dto.CartDto;

import java.sql.SQLException;

public interface OrdersBo extends SuperBo {
    boolean saveOrder(CartDto dto) throws SQLException, ClassNotFoundException;
    String generateId() throws SQLException, ClassNotFoundException;
}
