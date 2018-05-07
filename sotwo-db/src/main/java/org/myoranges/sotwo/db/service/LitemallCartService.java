package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoCartMapper;
import org.myoranges.sotwo.db.domain.sotwoCart;
import org.myoranges.sotwo.db.domain.sotwoCartExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoCartService {
    @Resource
    private SotwoCartMapper cartMapper;

    public sotwoCart queryExist(Integer goodsId, Integer productId, Integer userId) {
        sotwoCartExample example = new sotwoCartExample();
        example.or().andGoodsIdEqualTo(goodsId).andProductIdEqualTo(productId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectOneByExample(example);
    }

    public void add(sotwoCart cart) {
        cartMapper.insertSelective(cart);
    }

    public void update(sotwoCart cart) {
        cartMapper.updateByPrimaryKey(cart);
    }

    public List<sotwoCart> queryByUid(int userId) {
        sotwoCartExample example = new sotwoCartExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }


    public List<sotwoCart> queryByUidAndChecked(Integer userId) {
        sotwoCartExample example = new sotwoCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }

    public List<sotwoCart> queryByUidAndSid(int userId, String sessionId) {
        sotwoCartExample example = new sotwoCartExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }

    public int delete(List<Integer> productIdList, int userId) {
        sotwoCartExample example = new sotwoCartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(productIdList);
        sotwoCart cart = new sotwoCart();
        cart.setDeleted(true);
        return cartMapper.updateByExampleSelective(cart, example);
    }

    public sotwoCart findById(Integer id) {
        return cartMapper.selectByPrimaryKey(id);
    }

    public int updateCheck(Integer userId, List<Integer> idsList, Boolean checked) {
        sotwoCartExample example = new sotwoCartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(idsList).andDeletedEqualTo(false);
        sotwoCart cart = new sotwoCart();
        cart.setChecked(checked);
        return cartMapper.updateByExampleSelective(cart, example);
    }

    public void clearGoods(Integer userId) {
        sotwoCartExample example = new sotwoCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true);
        sotwoCart cart = new sotwoCart();
        cart.setDeleted(true);
        cartMapper.updateByExampleSelective(cart, example);
    }

    public List<sotwoCart> querySelective(Integer userId, Integer goodsId, Integer page, Integer limit, String sort, String order) {
        sotwoCartExample example = new sotwoCartExample();
        sotwoCartExample.Criteria criteria = example.createCriteria();

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
        sotwoCartExample example = new sotwoCartExample();
        sotwoCartExample.Criteria criteria = example.createCriteria();

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
        sotwoCart cart = cartMapper.selectByPrimaryKey(id);
        if(cart == null){
            return;
        }
        cart.setDeleted(true);
        cartMapper.updateByPrimaryKey(cart);
    }
}
