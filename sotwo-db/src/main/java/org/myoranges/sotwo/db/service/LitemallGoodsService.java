package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.domain.sotwoGoods;
import org.myoranges.sotwo.db.domain.sotwoGoods.Column;
import org.myoranges.sotwo.db.dao.SotwoGoodsMapper;
import org.myoranges.sotwo.db.domain.sotwoGoodsExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class sotwoGoodsService {
    @Resource
    private SotwoGoodsMapper goodsMapper;

    public List<sotwoGoods> queryByHot(int offset, int limit) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        example.or().andIsHotEqualTo(true).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public List<sotwoGoods> queryByNew(int offset, int limit) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        example.or().andIsNewEqualTo(true).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public List<sotwoGoods> queryByCategory(List<Integer> catList, int offset, int limit) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        example.or().andCategoryIdIn(catList).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public int countByCategory(List<Integer> catList, int offset, int limit) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        example.or().andCategoryIdIn(catList).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<sotwoGoods> queryByCategory(Integer catId, int offset, int limit) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        example.or().andCategoryIdEqualTo(catId).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public int countByCategory(Integer catId, Integer page, Integer size) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        example.or().andCategoryIdEqualTo(catId).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<sotwoGoods> querySelective(Integer catId, Integer brandId, String keyword, Integer isHot, Integer isNew, Integer offset, Integer limit, String sort) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        sotwoGoodsExample.Criteria criteria = example.createCriteria();

        if(catId != null && catId != 0){
            criteria.andCategoryIdEqualTo(catId);
        }
        if(brandId != null){
            criteria.andBrandIdEqualTo(brandId);
        }
        if(isNew != null){
            criteria.andIsNewEqualTo(isNew.intValue() == 1);
        }
        if(isHot != null){
            criteria.andIsHotEqualTo(isHot.intValue() == 1);
        }
        if(keyword != null){
            criteria.andKeywordsLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        if(sort != null){
            example.setOrderByClause(sort);
        }
        if(limit != null && offset != null) {
            PageHelper.startPage(offset, limit);
        }

        Column[] columns = new Column[]{Column.id, Column.name, Column.listPicUrl, Column.retailPrice};
        return goodsMapper.selectByExampleSelective(example ,columns);
    }

    public int countSelective(Integer catId, Integer brandId, String keyword, Integer isHot, Integer isNew, Integer offset, Integer limit, String sort) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        sotwoGoodsExample.Criteria criteria = example.createCriteria();

        if(catId != null){
            criteria.andCategoryIdEqualTo(catId);
        }
        if(brandId != null){
            criteria.andBrandIdEqualTo(brandId);
        }
        if(isNew != null){
            criteria.andIsNewEqualTo(isNew.intValue() == 1);
        }
        if(isHot != null){
            criteria.andIsHotEqualTo(isHot.intValue() == 1);
        }
        if(keyword != null){
            criteria.andKeywordsLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)goodsMapper.countByExample(example);
    }

    public sotwoGoods findById(Integer id) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return goodsMapper.selectOneByExampleWithBLOBs(example);
    }


    public List<sotwoGoods> queryByIds(List<Integer> relatedGoodsIds) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        example.or().andIdIn(relatedGoodsIds).andDeletedEqualTo(false);
        return goodsMapper.selectByExample(example);
    }

    public Integer queryOnSale() {
        sotwoGoodsExample example = new sotwoGoodsExample();
        example.or().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<sotwoGoods> querySelective(String goodsSn, String name, Integer page, Integer size, String sort, String order) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        sotwoGoodsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(goodsSn)){
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return goodsMapper.selectByExample(example);
    }

    public int countSelective(String goodsSn, String name, Integer page, Integer size, String sort, String order) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        sotwoGoodsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(goodsSn)){
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)goodsMapper.countByExample(example);
    }

    public void updateById(sotwoGoods goods) {
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    public void deleteById(Integer id) {
        sotwoGoods goods = goodsMapper.selectByPrimaryKey(id);
        if(goods == null){
            return;
        }
        goods.setDeleted(true);
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    public void add(sotwoGoods goods) {
        goodsMapper.insertSelective(goods);
    }

    public int count() {
        sotwoGoodsExample example = new sotwoGoodsExample();
        example.or().andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<Integer> getCatIds(Integer brandId, String keyword, Integer isHot, Integer isNew) {
        sotwoGoodsExample example = new sotwoGoodsExample();
        sotwoGoodsExample.Criteria criteria = example.createCriteria();

        if(brandId != null){
            criteria.andBrandIdEqualTo(brandId);
        }
        if(isNew != null){
            criteria.andIsNewEqualTo(isNew.intValue() == 1);
        }
        if(isHot != null){
            criteria.andIsHotEqualTo(isHot.intValue() == 1);
        }
        if(keyword != null){
            criteria.andKeywordsLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        List<sotwoGoods> goodsList = goodsMapper.selectByExampleSelective(example, Column.categoryId);
        List<Integer> cats = new ArrayList<Integer>();
        for(sotwoGoods goods : goodsList){
            cats.add(goods.getCategoryId());
        }
        return cats;
    }
}
