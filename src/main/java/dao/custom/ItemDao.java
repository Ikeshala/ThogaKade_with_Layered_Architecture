package dao.custom;

import dao.CrudDao;
import dto.ItemsDto;
import entity.Item;

import java.sql.SQLException;

public interface ItemDao extends CrudDao<Item> {
    ItemsDto getItem(String code) throws SQLException, ClassNotFoundException;
    boolean searchItem(String code);
//    boolean saveItem(ItemsDto dto) throws SQLException, ClassNotFoundException;
//    boolean updateItem(ItemsDto dto) throws SQLException, ClassNotFoundException;
//    boolean deleteItem(String code) throws SQLException, ClassNotFoundException;
//    List<ItemsDto> allItems() throws SQLException, ClassNotFoundException;
}
