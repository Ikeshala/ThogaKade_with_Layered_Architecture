package dao.custom.impl;

import db.DBConnection;
import dto.OrderDetailsDto;
import dao.custom.OrderDetailDao;
import entity.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
        boolean isdetailsSaved = true;
        for (OrderDetailsDto dto:list) {
            String sql = "INSERT INTO orderdetail VALUES (?,?,?,?)";

            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getOrderId());
            pstm.setString(2,dto.getItemCode());
            pstm.setInt(3,dto.getQuantity());
            pstm.setDouble(4,dto.getUnitPrice());

            if(!(pstm.executeUpdate()>0)){
                isdetailsSaved = false;
            }
        }
        return isdetailsSaved;
    }

    @Override
    public boolean save(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
