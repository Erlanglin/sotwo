package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoProductMapper;
import org.myoranges.sotwo.db.domain.sotwoProduct;
import org.myoranges.sotwo.db.domain.sotwoProductExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoProductService {
    @Resource
    private SotwoProductMapper productMapper;

    public List<sotwoProduct> queryByGid(Integer gid) {
        sotwoProductExample example = new sotwoProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return productMapper.selectByExample(example);
    }

    public sotwoProduct findById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    public List<sotwoProduct> querySelective(Integer goodsId, Integer page, Integer size, String sort, String order) {
        sotwoProductExample example = new sotwoProductExample();
        sotwoProductExample.Criteria criteria = example.createCriteria();

        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return productMapper.selectByExample(example);
    }

    public int countSelective(Integer goodsId, Integer page, Integer size, String sort, String order) {
        sotwoProductExample example = new sotwoProductExample();
        sotwoProductExample.Criteria criteria = example.createCriteria();

        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        return (int)productMapper.countByExample(example);
    }

    public void updateById(sotwoProduct product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    public void deleteById(Integer id) {
        sotwoProduct product = productMapper.selectByPrimaryKey(id);
        if(product == null){
            return;
        }
        product.setDeleted(true);
        productMapper.updateByPrimaryKey(product);
    }

    public void add(sotwoProduct product) {
        productMapper.insertSelective(product);
    }

    public int count() {
        sotwoProductExample example = new sotwoProductExample();
        example.or().andDeletedEqualTo(false);

        return (int)productMapper.countByExample(example);
    }
}
