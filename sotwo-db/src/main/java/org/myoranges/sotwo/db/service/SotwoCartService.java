package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;

import org.myoranges.sotwo.db.dao.SotwoCartMapper;
import org.myoranges.sotwo.db.domain.SotwoCart;
import org.myoranges.sotwo.db.domain.SotwoCartExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoCartService {
    @Resource
    private SotwoCartMapper cartMapper;

    public SotwoCart queryExist(Integer goodsId, Integer productId, Integer userId) {
        SotwoCartExample example = new SotwoCartExample();
        example.or().andGoodsIdEqualTo(goodsId).andProductIdEqualTo(productId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectOneByExample(example);
    }

    public void add(SotwoCart cart) {
        cartMapper.insertSelective(cart);
    }

    public void update(SotwoCart cart) {
        cartMapper.updateByPrimaryKey(cart);
    }

    public List<SotwoCart> queryByUid(int userId) {
        SotwoCartExample example = new SotwoCartExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }


    public List<SotwoCart> queryByUidAndChecked(Integer userId) {
        SotwoCartExample example = new SotwoCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }

    public List<SotwoCart> queryByUidAndSid(int userId, String sessionId) {
        SotwoCartExample example = new SotwoCartExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }

    public int delete(List<Integer> productIdList, int userId) {
        SotwoCartExample example = new SotwoCartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(productIdList);
        SotwoCart cart = new SotwoCart();
        cart.setDeleted(true);
        return cartMapper.updateByExampleSelective(cart, example);
    }

    public SotwoCart findById(Integer id) {
        return cartMapper.selectByPrimaryKey(id);
    }

    public int updateCheck(Integer userId, List<Integer> idsList, Boolean checked) {
        SotwoCartExample example = new SotwoCartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(idsList).andDeletedEqualTo(false);
        SotwoCart cart = new SotwoCart();
        cart.setChecked(checked);
        return cartMapper.updateByExampleSelective(cart, example);
    }

    public void clearGoods(Integer userId) {
        SotwoCartExample example = new SotwoCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true);
        SotwoCart cart = new SotwoCart();
        cart.setDeleted(true);
        cartMapper.updateByExampleSelective(cart, example);
    }

    public List<SotwoCart> querySelective(Integer userId, Integer goodsId, Integer page, Integer limit, String sort, String order) {
        SotwoCartExample example = new SotwoCartExample();
        SotwoCartExample.Criteria criteria = example.createCriteria();

        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, limit);
        return cartMapper.selectByExample(example);
    }

    public int countSelective(Integer userId, Integer goodsId, Integer page, Integer limit, String sort, String order) {
        SotwoCartExample example = new SotwoCartExample();
        SotwoCartExample.Criteria criteria = example.createCriteria();

        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        return (int)cartMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        SotwoCart cart = cartMapper.selectByPrimaryKey(id);
        if(cart == null){
            return;
        }
        cart.setDeleted(true);
        cartMapper.updateByPrimaryKey(cart);
    }
}
