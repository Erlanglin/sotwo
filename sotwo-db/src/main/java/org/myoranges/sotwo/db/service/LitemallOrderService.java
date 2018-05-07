package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoOrderMapper;
import org.myoranges.sotwo.db.domain.sotwoOrder;
import org.myoranges.sotwo.db.domain.sotwoOrderExample;
import org.myoranges.sotwo.db.util.OrderUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class sotwoOrderService {
    @Resource
    private SotwoOrderMapper orderMapper;

    public int add(sotwoOrder order) {
        return orderMapper.insertSelective(order);
    }

    public List<sotwoOrder> query(Integer userId) {
        sotwoOrderExample example = new sotwoOrderExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return orderMapper.selectByExample(example);
    }

    public int count(Integer userId) {
        sotwoOrderExample example = new sotwoOrderExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return (int)orderMapper.countByExample(example);
    }

    public sotwoOrder findById(Integer orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    private String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public sotwoOrder queryByOrderSn(Integer userId, String orderSn){
        sotwoOrderExample example = new sotwoOrderExample();
        example.or().andUserIdEqualTo(userId).andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return orderMapper.selectOneByExample(example);
    }

    public int countByOrderSn(Integer userId, String orderSn){
        sotwoOrderExample example = new sotwoOrderExample();
        example.or().andUserIdEqualTo(userId).andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return (int)orderMapper.countByExample(example);
    }

    // TODO 这里应该产生一个唯一的订单，但是实际上这里仍然存在两个订单相同的可能性
    public String generateOrderSn(Integer userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String orderSn = now + getRandomNum(6);
        while(countByOrderSn(userId, orderSn) != 0){
            orderSn = getRandomNum(6);
        }
        return orderSn;
    }

    public List<sotwoOrder> queryByOrderStatus(Integer userId, List<Short> orderStatus) {
        sotwoOrderExample example = new sotwoOrderExample();
        example.orderBy(sotwoOrder.Column.addTime.desc());
        sotwoOrderExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
        if(orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        criteria.andDeletedEqualTo(false);
        return orderMapper.selectByExample(example);
    }

    public int countByOrderStatus(Integer userId, List<Short> orderStatus) {
        sotwoOrderExample example = new sotwoOrderExample();
        sotwoOrderExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
        if(orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        criteria.andDeletedEqualTo(false);
        return (int)orderMapper.countByExample(example);
    }

    public int update(sotwoOrder order) {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    public List<sotwoOrder> querySelective(Integer userId, String orderSn, Integer page, Integer size, String sort, String order) {
        sotwoOrderExample example = new sotwoOrderExample();
        sotwoOrderExample.Criteria criteria = example.createCriteria();

        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(orderSn)){
            criteria.andOrderSnEqualTo(orderSn);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return orderMapper.selectByExample(example);
    }

    public int countSelective(Integer userId, String orderSn, Integer page, Integer size, String sort, String order) {
        sotwoOrderExample example = new sotwoOrderExample();
        sotwoOrderExample.Criteria criteria = example.createCriteria();

        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(orderSn)){
            criteria.andOrderSnEqualTo(orderSn);
        }
        criteria.andDeletedEqualTo(false);

        return (int)orderMapper.countByExample(example);
    }

    public void updateById(sotwoOrder order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    public void deleteById(Integer id) {
        sotwoOrder order = orderMapper.selectByPrimaryKey(id);
        if(order == null){
            return;
        }
        order.setDeleted(true);
        orderMapper.updateByPrimaryKey(order);
    }

    public int count() {
        sotwoOrderExample example = new sotwoOrderExample();
        example.or().andDeletedEqualTo(false);
        return (int)orderMapper.countByExample(example);
    }

    public List<sotwoOrder> queryUnpaid() {
        sotwoOrderExample example = new sotwoOrderExample();
        example.or().andOrderStatusEqualTo(OrderUtil.STATUS_CREATE).andDeletedEqualTo(false);
        return orderMapper.selectByExample(example);
    }

    public List<sotwoOrder> queryUnconfirm() {
        sotwoOrderExample example = new sotwoOrderExample();
        example.or().andOrderStatusEqualTo(OrderUtil.STATUS_SHIP).andShipEndTimeIsNotNull().andDeletedEqualTo(false);
        return orderMapper.selectByExample(example);
    }

    public sotwoOrder findBySn(String orderSn) {
        sotwoOrderExample example = new sotwoOrderExample();
        example.or().andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return orderMapper.selectOneByExample(example);
    }
}
