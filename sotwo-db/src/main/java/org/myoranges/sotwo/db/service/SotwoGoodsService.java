package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoGoodsMapper;
import org.myoranges.sotwo.db.domain.SotwoGoods;
import org.myoranges.sotwo.db.domain.SotwoGoodsExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SotwoGoodsService {
    @Resource
    private SotwoGoodsMapper goodsMapper;

    public List<SotwoGoods> queryByHot(int offset, int limit) {
        SotwoGoodsExample example = new SotwoGoodsExample();
        example.or().andIsHotEqualTo(true).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public List<SotwoGoods> queryByNew(int offset, int limit) {
        SotwoGoodsExample example = new SotwoGoodsExample();
        example.or().andIsNewEqualTo(true).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public List<SotwoGoods> queryByCategory(List<Integer> catList, int offset, int limit) {
        SotwoGoodsExample example = new SotwoGoodsExample();
        example.or().andCategoryIdIn(catList).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public int countByCategory(List<Integer> catList, int offset, int limit) {
        SotwoGoodsExample example = new SotwoGoodsExample();
        example.or().andCategoryIdIn(catList).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<SotwoGoods> queryByCategory(Integer catId, int offset, int limit) {
        SotwoGoodsExample example = new SotwoGoodsExample();
        example.or().andCategoryIdEqualTo(catId).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public int countByCategory(Integer catId, Integer page, Integer size) {
        SotwoGoodsExample example = new SotwoGoodsExample();
        example.or().andCategoryIdEqualTo(catId).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<SotwoGoods> querySelective(Integer catId, Integer brandId, String keyword, Integer isHot, Integer isNew, Integer offset, Integer limit, String sort) {
        SotwoGoodsExample example = new SotwoGoodsExample();
        SotwoGoodsExample.Criteria criteria = example.createCriteria();

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

        SotwoGoods.Column[] columns = new SotwoGoods.Column[]{SotwoGoods.Column.id, SotwoGoods.Column.name, SotwoGoods.Column.listPicUrl, SotwoGoods.Column.retailPrice};
        return goodsMapper.selectByExampleSelective(example ,columns);
    }

    public int countSelective(Integer catId, Integer brandId, String keyword, Integer isHot, Integer isNew, Integer offset, Integer limit, String sort) {
        SotwoGoodsExample example = new SotwoGoodsExample();
        SotwoGoodsExample.Criteria criteria = example.createCriteria();

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

    public SotwoGoods findById(Integer id) {
        SotwoGoodsExample example = new SotwoGoodsExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return goodsMapper.selectOneByExampleWithBLOBs(example);
    }


    public List<SotwoGoods> queryByIds(List<Integer> relatedGoodsIds) {
        SotwoGoodsExample example = new SotwoGoodsExample();
        example.or().andIdIn(relatedGoodsIds).andDeletedEqualTo(false);
        return goodsMapper.selectByExample(example);
    }

    public Integer queryOnSale() {
        SotwoGoodsExample example = new SotwoGoodsExample();
        example.or().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<SotwoGoods> querySelective(String goodsSn, String name, Integer page, Integer size, String sort, String order) {
        SotwoGoodsExample example = new SotwoGoodsExample();
        SotwoGoodsExample.Criteria criteria = example.createCriteria();

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
        SotwoGoodsExample example = new SotwoGoodsExample();
        SotwoGoodsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(goodsSn)){
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)goodsMapper.countByExample(example);
    }

    public void updateById(SotwoGoods goods) {
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    public void deleteById(Integer id) {
        SotwoGoods goods = goodsMapper.selectByPrimaryKey(id);
        if(goods == null){
            return;
        }
        goods.setDeleted(true);
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    public void add(SotwoGoods goods) {
        goodsMapper.insertSelective(goods);
    }

    public int count() {
        SotwoGoodsExample example = new SotwoGoodsExample();
        example.or().andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<Integer> getCatIds(Integer brandId, String keyword, Integer isHot, Integer isNew) {
        SotwoGoodsExample example = new SotwoGoodsExample();
        SotwoGoodsExample.Criteria criteria = example.createCriteria();

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

        List<SotwoGoods> goodsList = goodsMapper.selectByExampleSelective(example, SotwoGoods.Column.categoryId);
        List<Integer> cats = new ArrayList<Integer>();
        for(SotwoGoods goods : goodsList){
            cats.add(goods.getCategoryId());
        }
        return cats;
    }
}
