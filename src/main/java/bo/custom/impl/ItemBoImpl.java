package bo.custom.impl;

import bo.custom.ItemBo;
import dao.custom.ItemDao;
import dao.custom.impl.ItemDaoImpl;
import dto.ItemsDto;
import entity.Customer;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo<ItemsDto> {
    private ItemDao itemDao = new ItemDaoImpl();
    @Override
    public boolean saveItem(ItemsDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.save(new Item(
                dto.getCode(),
                dto.getDescription(),
                dto.getUnitPrice(),
                dto.getQuantity()
        ));
    }

    @Override
    public boolean updateItem(ItemsDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.update(new Item(
                dto.getCode(),
                dto.getDescription(),
                dto.getUnitPrice(),
                dto.getQuantity()
        ));
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDao.delete(code);
    }

    @Override
    public boolean searchItem(ItemsDto dto) {
        return false;
    }

    @Override
    public List<ItemsDto> allItems() throws SQLException, ClassNotFoundException {
        List<Item> entityList = itemDao.getAll();
        List<ItemsDto> list =new ArrayList<>();
        for (Item item:entityList) {
            list.add(new ItemsDto(
                    item.getCode(),
                    item.getDescription(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            ));
        }
        return list;
    }
}
