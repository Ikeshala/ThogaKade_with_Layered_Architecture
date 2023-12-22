package dao.custom;

import dao.CrudDao;
import dto.CartDto;

import java.sql.SQLException;

public interface CartDao extends CrudDao<CartDto> {
    CartDto getLastOrder() throws SQLException, ClassNotFoundException;
}
