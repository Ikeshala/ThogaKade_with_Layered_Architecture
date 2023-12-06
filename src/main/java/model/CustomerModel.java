package model;

import dto.CustomersDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    boolean saveCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException;
    boolean searchCustomer(String id);
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    List<CustomersDto> allCustomers() throws SQLException, ClassNotFoundException;
}
