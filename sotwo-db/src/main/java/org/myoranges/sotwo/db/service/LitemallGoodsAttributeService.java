package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoGoodsAttributeMapper;
import org.myoranges.sotwo.db.domain.sotwoGoodsAttribute;
import org.myoranges.sotwo.db.domain.sotwoGoodsAttributeExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoGoodsAttributeService {
    @Resource
    private SotwoGoodsAttributeMapper goodsAttributeMapper;

    public List<sotwoGoodsAttribute> queryByGid(Integer goodsId) {
        sotwoGoodsAttributeExample example = new sotwoGoodsAttributeExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return goodsAttributeMapper.selectByExample(example);
    }

    public List<sotwoGoodsAttribute> querySelective(Integer goodsId, Integer page, Integer size, String sort, String order) {
        sotwoGoodsAttributeExample example = new sotwoGoodsAttributeExample();
        sotwoGoodsAttributeExample.Criteria criteria = example.createCriteria();

        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return goodsAttributeMapper.selectByExample(example);
    }

    public int countSelective(Integer goodsId, Integer page, Integer size, String sort, String order) {
        sotwoGoodsAttributeExample example = new sotwoGoodsAttributeExample();
        sotwoGoodsAttributeExample.Criteria criteria = example.createCriteria();

        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        return (int)goodsAttributeMapper.countByExample(example);
    }

    public void updateById(sotwoGoodsAttribute goodsAttribute) {
        goodsAttributeMapper.updateByPrimaryKeySelective(goodsAttribute);
    }

    public void deleteById(Integer id) {
        sotwoGoodsAttribute goodsAttribute = goodsAttributeMapper.selectByPrimaryKey(id);
        if(goodsAttribute == null){
            return;
        }
        goodsAttribute.setDeleted(true);
        goodsAttributeMapper.updateByPrimaryKey(goodsAttribute);
    }

    public void add(sotwoGoodsAttribute goodsAttribute) {
        goodsAttributeMapper.insertSelective(goodsAttribute);
    }

    public sotwoGoodsAttribute findById(Integer id) {
        return goodsAttributeMapper.selectByPrimaryKey(id);
    }
}
