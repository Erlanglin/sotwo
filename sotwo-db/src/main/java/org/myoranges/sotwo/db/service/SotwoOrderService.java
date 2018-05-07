package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoOrderMapper;
import org.myoranges.sotwo.db.domain.SotwoOrder;
import org.myoranges.sotwo.db.domain.SotwoOrderExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class SotwoOrderService {
    @Resource
    private SotwoOrderMapper orderMapper;

    public int add(SotwoOrder order) {
        return orderMapper.insertSelective(order);
    }

    public List<SotwoOrder> query(Integer userId) {
        SotwoOrderExample example = new SotwoOrderExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return orderMapper.selectByExample(example);
    }

    public int count(Integer userId) {
        SotwoOrderExample example = new SotwoOrderExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return (int)orderMapper.countByExample(example);
    }

    public SotwoOrder findById(Integer orderId) {
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

    public SotwoOrder queryByOrderSn(Integer userId, String orderSn){
        SotwoOrderExample example = new SotwoOrderExample();
        example.or().andUserIdEqualTo(userId).andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return orderMapper.selectOneByExample(example);
    }

    public int countByOrderSn(Integer userId, String orderSn){
        SotwoOrderExample example = new SotwoOrderExample();
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

    public List<SotwoOrder> queryByOrderStatus(Integer userId, List<Short> orderStatus) {
        SotwoOrderExample example = new SotwoOrderExample();
        example.orderBy(SotwoOrder.Column.addTime.desc());
        SotwoOrderExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
        if(orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        criteria.andDeletedEqualTo(false);
        return orderMapper.selectByExample(example);
    }

    public int countByOrderStatus(Integer userId, List<Short> orderStatus) {
        SotwoOrderExample example = new SotwoOrderExample();
        SotwoOrderExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
        if(orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        criteria.andDeletedEqualTo(false);
        return (int)orderMapper.countByExample(example);
    }

    public int update(SotwoOrder order) {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    public List<SotwoOrder> querySelective(Integer userId, String orderSn, Integer page, Integer size, String sort, String order) {
        SotwoOrderExample example = new SotwoOrderExample();
        SotwoOrderExample.Criteria criteria = example.createCriteria();

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
        SotwoOrderExample example = new SotwoOrderExample();
        SotwoOrderExample.Criteria criteria = example.createCriteria();

        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(orderSn)){
            criteria.andOrderSnEqualTo(orderSn);
        }
        criteria.andDeletedEqualTo(false);

        return (int)orderMapper.countByExample(example);
    }

    public void updateById(SotwoOrder order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    public void deleteById(Integer id) {
        SotwoOrder order = orderMapper.selectByPrimaryKey(id);
        if(order == null){
            return;
        }
        order.setDeleted(true);
        orderMapper.updateByPrimaryKey(order);
    }

    public int count() {
        SotwoOrderExample example = new SotwoOrderExample();
        example.or().andDeletedEqualTo(false);
        return (int)orderMapper.countByExample(example);
    }

    public List<SotwoOrder> queryUnpaid() {
        SotwoOrderExample example = new SotwoOrderExample();
        example.or().andOrderStatusEqualTo(org.myoranges.sotwo.db.util.OrderUtil.STATUS_CREATE).andDeletedEqualTo(false);
        return orderMapper.selectByExample(example);
    }

    public List<SotwoOrder> queryUnconfirm() {
        SotwoOrderExample example = new SotwoOrderExample();
        example.or().andOrderStatusEqualTo(org.myoranges.sotwo.db.util.OrderUtil.STATUS_SHIP).andShipEndTimeIsNotNull().andDeletedEqualTo(false);
        return orderMapper.selectByExample(example);
    }

    public SotwoOrder findBySn(String orderSn) {
        SotwoOrderExample example = new SotwoOrderExample();
        example.or().andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return orderMapper.selectOneByExample(example);
    }
}
