package model;

import dto.ItemsDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemModel {
    boolean saveItem(ItemsDto dto) throws SQLException, ClassNotFoundException;
    boolean updateItem(ItemsDto dto) throws SQLException, ClassNotFoundException;
    boolean searchItem(String code);
    boolean deleteItem(String code) throws SQLException, ClassNotFoundException;
    ItemsDto getItem(String code) throws SQLException, ClassNotFoundException;
    List<ItemsDto> allItems() throws SQLException, ClassNotFoundException;
}
