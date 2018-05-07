package org.myoranges.sotwo.db.service;


import org.myoranges.sotwo.db.dao.SotwoOrderGoodsMapper;
import org.myoranges.sotwo.db.domain.SotwoOrderGoods;
import org.myoranges.sotwo.db.domain.SotwoOrderGoodsExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoOrderGoodsService {
    @Resource
    private SotwoOrderGoodsMapper orderGoodsMapper;

    public int add(SotwoOrderGoods orderGoods) {
        return orderGoodsMapper.insertSelective(orderGoods);
    }

    public List<SotwoOrderGoods> queryByOid(Integer orderId) {
        SotwoOrderGoodsExample example = new SotwoOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }
    public List<SotwoOrderGoods> findByOidAndGid(Integer orderId, Integer goodsId) {
        SotwoOrderGoodsExample example = new SotwoOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

}
