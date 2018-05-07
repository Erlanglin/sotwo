package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoProductMapper;
import org.myoranges.sotwo.db.domain.SotwoProduct;
import org.myoranges.sotwo.db.domain.SotwoProductExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoProductService {
    @Resource
    private SotwoProductMapper productMapper;

    public List<SotwoProduct> queryByGid(Integer gid) {
        SotwoProductExample example = new SotwoProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return productMapper.selectByExample(example);
    }

    public SotwoProduct findById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    public List<SotwoProduct> querySelective(Integer goodsId, Integer page, Integer size, String sort, String order) {
        SotwoProductExample example = new SotwoProductExample();
        SotwoProductExample.Criteria criteria = example.createCriteria();

        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return productMapper.selectByExample(example);
    }

    public int countSelective(Integer goodsId, Integer page, Integer size, String sort, String order) {
        SotwoProductExample example = new SotwoProductExample();
        SotwoProductExample.Criteria criteria = example.createCriteria();

        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        return (int)productMapper.countByExample(example);
    }

    public void updateById(SotwoProduct product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    public void deleteById(Integer id) {
        SotwoProduct product = productMapper.selectByPrimaryKey(id);
        if(product == null){
            return;
        }
        product.setDeleted(true);
        productMapper.updateByPrimaryKey(product);
    }

    public void add(SotwoProduct product) {
        productMapper.insertSelective(product);
    }

    public int count() {
        SotwoProductExample example = new SotwoProductExample();
        example.or().andDeletedEqualTo(false);

        return (int)productMapper.countByExample(example);
    }
}
