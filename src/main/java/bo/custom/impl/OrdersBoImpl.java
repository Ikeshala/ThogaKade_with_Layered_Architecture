package bo.custom.impl;

import bo.custom.OrdersBo;
import dao.custom.CartDao;
import dao.custom.impl.CartDaoImpl;
import dto.CartDto;

import java.sql.SQLException;

public class OrdersBoImpl implements OrdersBo {
    private CartDao cartDao = new CartDaoImpl();
    @Override
    public boolean saveOrder(CartDto dto) throws SQLException, ClassNotFoundException {
        return cartDao.save(dto);
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        try {
            String id = cartDao.getLastOrder().getOrderId();
            if (id!=null){
                int num = Integer.parseInt(id.split("[D]")[1]);
                num++;
                return String.format("D%03d",num);
            }else{
                return "D001";
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
