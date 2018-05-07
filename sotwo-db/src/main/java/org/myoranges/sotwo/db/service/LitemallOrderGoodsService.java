package org.myoranges.sotwo.db.service;

import org.myoranges.sotwo.db.dao.SotwoOrderGoodsMapper;
import org.myoranges.sotwo.db.domain.sotwoOrderGoods;
import org.myoranges.sotwo.db.domain.sotwoOrderGoodsExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoOrderGoodsService {
    @Resource
    private SotwoOrderGoodsMapper orderGoodsMapper;

    public int add(sotwoOrderGoods orderGoods) {
        return orderGoodsMapper.insertSelective(orderGoods);
    }

    public List<sotwoOrderGoods> queryByOid(Integer orderId) {
        sotwoOrderGoodsExample example = new sotwoOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }
    public List<sotwoOrderGoods> findByOidAndGid(Integer orderId, Integer goodsId) {
        sotwoOrderGoodsExample example = new sotwoOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

}
