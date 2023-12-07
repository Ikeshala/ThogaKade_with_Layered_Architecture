package dao.custom;

import dao.CrudDao;
import dto.CustomersDto;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao extends CrudDao<Customer> {
    boolean searchCustomer(String id);
//    boolean saveCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException;
//    boolean updateCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException;
//    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
//    List<CustomersDto> allCustomers() throws SQLException, ClassNotFoundException;
}
