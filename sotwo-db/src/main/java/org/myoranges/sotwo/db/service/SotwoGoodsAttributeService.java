package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoGoodsAttributeMapper;
import org.myoranges.sotwo.db.domain.SotwoGoodsAttribute;
import org.myoranges.sotwo.db.domain.SotwoGoodsAttributeExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoGoodsAttributeService {
    @Resource
    private SotwoGoodsAttributeMapper goodsAttributeMapper;

    public List<SotwoGoodsAttribute> queryByGid(Integer goodsId) {
        SotwoGoodsAttributeExample example = new SotwoGoodsAttributeExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return goodsAttributeMapper.selectByExample(example);
    }

    public List<SotwoGoodsAttribute> querySelective(Integer goodsId, Integer page, Integer size, String sort, String order) {
        SotwoGoodsAttributeExample example = new SotwoGoodsAttributeExample();
        SotwoGoodsAttributeExample.Criteria criteria = example.createCriteria();

        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return goodsAttributeMapper.selectByExample(example);
    }

    public int countSelective(Integer goodsId, Integer page, Integer size, String sort, String order) {
        SotwoGoodsAttributeExample example = new SotwoGoodsAttributeExample();
        SotwoGoodsAttributeExample.Criteria criteria = example.createCriteria();

        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        return (int)goodsAttributeMapper.countByExample(example);
    }

    public void updateById(SotwoGoodsAttribute goodsAttribute) {
        goodsAttributeMapper.updateByPrimaryKeySelective(goodsAttribute);
    }

    public void deleteById(Integer id) {
        SotwoGoodsAttribute goodsAttribute = goodsAttributeMapper.selectByPrimaryKey(id);
        if(goodsAttribute == null){
            return;
        }
        goodsAttribute.setDeleted(true);
        goodsAttributeMapper.updateByPrimaryKey(goodsAttribute);
    }

    public void add(SotwoGoodsAttribute goodsAttribute) {
        goodsAttributeMapper.insertSelective(goodsAttribute);
    }

    public SotwoGoodsAttribute findById(Integer id) {
        return goodsAttributeMapper.selectByPrimaryKey(id);
    }
}
