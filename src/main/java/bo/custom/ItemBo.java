package bo.custom;

import bo.SuperBo;
import dto.ItemsDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemBo extends SuperBo {
    boolean saveItem(ItemsDto dto) throws SQLException, ClassNotFoundException;
    boolean updateItem(ItemsDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteItem(String code) throws SQLException, ClassNotFoundException;
    boolean searchItem(ItemsDto dto);
    List<ItemsDto> allItems() throws SQLException, ClassNotFoundException;
}
