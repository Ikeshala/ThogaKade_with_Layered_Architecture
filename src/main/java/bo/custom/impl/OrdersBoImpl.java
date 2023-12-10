package bo.custom.impl;

import bo.custom.OrdersBo;
import dao.custom.OrdersDao;
import dao.custom.impl.OrdersDaoImpl;
import dto.OrderDto;

import java.sql.SQLException;

public class OrdersBoImpl implements OrdersBo {
    private OrdersDao ordersDao = new OrdersDaoImpl();
    @Override
    public boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException {
        return ordersDao.save(dto);
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        try {
            String id = ordersDao.getLastOrder().getOrderId();
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
