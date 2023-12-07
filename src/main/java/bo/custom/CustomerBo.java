package bo.custom;

import bo.SuperBo;
import dto.CustomersDto;
import dto.ItemsDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBo<T> extends SuperBo {
    boolean saveCustomer(T dto) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(T dto) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    boolean searchCustomer(T dto);
    List<CustomersDto> allCustomers() throws SQLException, ClassNotFoundException;
}
