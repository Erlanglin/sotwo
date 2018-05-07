package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoCollectMapper;
import org.myoranges.sotwo.db.domain.SotwoCollect;
import org.myoranges.sotwo.db.domain.SotwoCollectExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoCollectService {
    @Resource
    private SotwoCollectMapper collectMapper;

    public int count(int uid, Integer gid) {
        SotwoCollectExample example = new SotwoCollectExample();
        example.or().andUserIdEqualTo(uid).andValueIdEqualTo(gid).andDeletedEqualTo(false);
        return (int)collectMapper.countByExample(example);
    }

    public List<SotwoCollect> queryByType(Integer userId, Integer typeId, Integer page, Integer size) {
        SotwoCollectExample example = new SotwoCollectExample();
        example.or().andUserIdEqualTo(userId).andTypeIdEqualTo(typeId).andDeletedEqualTo(false);
        example.setOrderByClause(SotwoCollect.Column.addTime.desc());
        PageHelper.startPage(page, size);
        return collectMapper.selectByExample(example);
    }

    public int countByType(Integer userId, Integer typeId) {
        SotwoCollectExample example = new SotwoCollectExample();
        example.or().andUserIdEqualTo(userId).andTypeIdEqualTo(typeId).andDeletedEqualTo(false);
        return (int)collectMapper.countByExample(example);
    }

    public SotwoCollect queryByTypeAndValue(Integer userId, Integer typeId, Integer valueId) {
        SotwoCollectExample example = new SotwoCollectExample();
        example.or().andUserIdEqualTo(userId).andValueIdEqualTo(valueId).andTypeIdEqualTo(typeId).andDeletedEqualTo(false);
        return collectMapper.selectOneByExample(example);
    }

    public void deleteById(Integer id) {
        SotwoCollect collect = collectMapper.selectByPrimaryKey(id);
        if(collect == null){
            return;
        }
        collect.setDeleted(true);
        collectMapper.updateByPrimaryKey(collect);
    }

    public int add(SotwoCollect collect) {
        return collectMapper.insertSelective(collect);
    }

    public List<SotwoCollect> querySelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        SotwoCollectExample example = new SotwoCollectExample();
        SotwoCollectExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return collectMapper.selectByExample(example);
    }

    public int countSelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        SotwoCollectExample example = new SotwoCollectExample();
        SotwoCollectExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }
        criteria.andDeletedEqualTo(false);

        return (int)collectMapper.countByExample(example);
    }

    public void updateById(SotwoCollect collect) {
        collectMapper.updateByPrimaryKeySelective(collect);
    }

    public SotwoCollect findById(Integer id) {
        return collectMapper.selectByPrimaryKey(id);
    }
}
