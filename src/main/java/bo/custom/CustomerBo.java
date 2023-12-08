package bo.custom;

import bo.SuperBo;
import dto.CustomersDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBo extends SuperBo {
    boolean saveCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    boolean searchCustomer(CustomersDto dto);
    List<CustomersDto> allCustomers() throws SQLException, ClassNotFoundException;
}
