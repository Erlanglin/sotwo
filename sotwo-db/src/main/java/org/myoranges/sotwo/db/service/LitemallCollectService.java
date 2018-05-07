package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.domain.sotwoCollect;
import org.myoranges.sotwo.db.dao.SotwoCollectMapper;
import org.myoranges.sotwo.db.domain.sotwoCollectExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoCollectService {
    @Resource
    private SotwoCollectMapper collectMapper;

    public int count(int uid, Integer gid) {
        sotwoCollectExample example = new sotwoCollectExample();
        example.or().andUserIdEqualTo(uid).andValueIdEqualTo(gid).andDeletedEqualTo(false);
        return (int)collectMapper.countByExample(example);
    }

    public List<sotwoCollect> queryByType(Integer userId, Integer typeId, Integer page, Integer size) {
        sotwoCollectExample example = new sotwoCollectExample();
        example.or().andUserIdEqualTo(userId).andTypeIdEqualTo(typeId).andDeletedEqualTo(false);
        example.setOrderByClause(sotwoCollect.Column.addTime.desc());
        PageHelper.startPage(page, size);
        return collectMapper.selectByExample(example);
    }

    public int countByType(Integer userId, Integer typeId) {
        sotwoCollectExample example = new sotwoCollectExample();
        example.or().andUserIdEqualTo(userId).andTypeIdEqualTo(typeId).andDeletedEqualTo(false);
        return (int)collectMapper.countByExample(example);
    }

    public sotwoCollect queryByTypeAndValue(Integer userId, Integer typeId, Integer valueId) {
        sotwoCollectExample example = new sotwoCollectExample();
        example.or().andUserIdEqualTo(userId).andValueIdEqualTo(valueId).andTypeIdEqualTo(typeId).andDeletedEqualTo(false);
        return collectMapper.selectOneByExample(example);
    }

    public void deleteById(Integer id) {
        sotwoCollect collect = collectMapper.selectByPrimaryKey(id);
        if(collect == null){
            return;
        }
        collect.setDeleted(true);
        collectMapper.updateByPrimaryKey(collect);
    }

    public int add(sotwoCollect collect) {
        return collectMapper.insertSelective(collect);
    }

    public List<sotwoCollect> querySelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        sotwoCollectExample example = new sotwoCollectExample();
        sotwoCollectExample.Criteria criteria = example.createCriteria();

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
        sotwoCollectExample example = new sotwoCollectExample();
        sotwoCollectExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }
        criteria.andDeletedEqualTo(false);

        return (int)collectMapper.countByExample(example);
    }

    public void updateById(sotwoCollect collect) {
        collectMapper.updateByPrimaryKeySelective(collect);
    }

    public sotwoCollect findById(Integer id) {
        return collectMapper.selectByPrimaryKey(id);
    }
}
