package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoFootprintMapper;
import org.myoranges.sotwo.db.domain.SotwoFootprint;
import org.myoranges.sotwo.db.domain.SotwoFootprintExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoFootprintService {
    @Resource
    private SotwoFootprintMapper footprintMapper;

    public List<SotwoFootprint> queryByAddTime(Integer userId, Integer page, Integer size) {
        SotwoFootprintExample example = new SotwoFootprintExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        example.setOrderByClause(SotwoFootprint.Column.addTime.desc());
        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }

    public int countByAddTime(Integer userId,Integer page, Integer size) {
        SotwoFootprintExample example = new SotwoFootprintExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return (int)footprintMapper.countByExample(example);
    }

    public SotwoFootprint findById(Integer id) {
        return footprintMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id){
        SotwoFootprint footprint = footprintMapper.selectByPrimaryKey(id);
        if(footprint == null){
            return;
        }
        footprint.setDeleted(true);
        footprintMapper.updateByPrimaryKey(footprint);
    }

    public void add(SotwoFootprint footprint) {
        footprintMapper.insertSelective(footprint);
    }

    public List<SotwoFootprint> querySelective(String userId, String goodsId, Integer page, Integer size, String sort, String order) {
        SotwoFootprintExample example = new SotwoFootprintExample();
        SotwoFootprintExample.Criteria criteria = example.createCriteria();

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
        SotwoFootprintExample example = new SotwoFootprintExample();
        SotwoFootprintExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(goodsId)){
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        return (int)footprintMapper.countByExample(example);
    }

    public void updateById(SotwoFootprint collect) {
        footprintMapper.updateByPrimaryKeySelective(collect);
    }

}
