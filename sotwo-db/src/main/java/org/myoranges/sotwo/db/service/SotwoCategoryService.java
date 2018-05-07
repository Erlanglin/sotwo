package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;

import org.myoranges.sotwo.db.dao.SotwoCategoryMapper;
import org.myoranges.sotwo.db.domain.SotwoCategory;
import org.myoranges.sotwo.db.domain.SotwoCategoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoCategoryService {
    @Resource
    private SotwoCategoryMapper categoryMapper;

    public List<SotwoCategory> queryL1WithoutRecommend(int offset, int limit) {
        SotwoCategoryExample example = new SotwoCategoryExample();
        example.or().andLevelEqualTo("L1").andNameNotEqualTo("推荐").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    public List<SotwoCategory> queryL1(int offset, int limit) {
        SotwoCategoryExample example = new SotwoCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    public List<SotwoCategory> queryL1() {
        SotwoCategoryExample example = new SotwoCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public List<SotwoCategory> queryByPid(Integer pid) {
        SotwoCategoryExample example = new SotwoCategoryExample();
        example.or().andParentIdEqualTo(pid).andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public List<SotwoCategory> queryL2ByIds(List<Integer> ids) {
        SotwoCategoryExample example = new SotwoCategoryExample();
        example.or().andIdIn(ids).andLevelEqualTo("L2").andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public SotwoCategory findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public List<SotwoCategory> querySelective(String id, String name, Integer page, Integer size, String sort, String order) {
        SotwoCategoryExample example = new SotwoCategoryExample();
        SotwoCategoryExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(id)){
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return categoryMapper.selectByExample(example);
    }

    public int countSelective(String id, String name, Integer page, Integer size, String sort, String order) {
        SotwoCategoryExample example = new SotwoCategoryExample();
        SotwoCategoryExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(id)){
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)categoryMapper.countByExample(example);
    }

    public void updateById(SotwoCategory category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    public void deleteById(Integer id) {
        SotwoCategory category = categoryMapper.selectByPrimaryKey(id);
        if(category == null){
            return;
        }
        category.setDeleted(true);
        categoryMapper.updateByPrimaryKey(category);
    }

    public void add(SotwoCategory category) {
        categoryMapper.insertSelective(category);
    }

    private SotwoCategory.Column[] CHANNEL = {SotwoCategory.Column.id, SotwoCategory.Column.name, SotwoCategory.Column.iconUrl};
    public List<SotwoCategory> queryChannel() {
        SotwoCategoryExample example = new SotwoCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExampleSelective(example, CHANNEL);
    }
}
