package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoFootprintMapper;
import org.myoranges.sotwo.db.domain.sotwoFootprint;
import org.myoranges.sotwo.db.domain.sotwoFootprintExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoFootprintService {
    @Resource
    private SotwoFootprintMapper footprintMapper;

    public List<sotwoFootprint> queryByAddTime(Integer userId, Integer page, Integer size) {
        sotwoFootprintExample example = new sotwoFootprintExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        example.setOrderByClause(sotwoFootprint.Column.addTime.desc());
        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }

    public int countByAddTime(Integer userId,Integer page, Integer size) {
        sotwoFootprintExample example = new sotwoFootprintExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return (int)footprintMapper.countByExample(example);
    }

    public sotwoFootprint findById(Integer id) {
        return footprintMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id){
        sotwoFootprint footprint = footprintMapper.selectByPrimaryKey(id);
        if(footprint == null){
            return;
        }
        footprint.setDeleted(true);
        footprintMapper.updateByPrimaryKey(footprint);
    }

    public void add(sotwoFootprint footprint) {
        footprintMapper.insertSelective(footprint);
    }

    public List<sotwoFootprint> querySelective(String userId, String goodsId, Integer page, Integer size, String sort, String order) {
        sotwoFootprintExample example = new sotwoFootprintExample();
        sotwoFootprintExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(goodsId)){
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }

    public int countSelective(String userId, String goodsId, Integer page, Integer size, String sort, String order) {
        sotwoFootprintExample example = new sotwoFootprintExample();
        sotwoFootprintExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(goodsId)){
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        return (int)footprintMapper.countByExample(example);
    }

    public void updateById(sotwoFootprint collect) {
        footprintMapper.updateByPrimaryKeySelective(collect);
    }

}
