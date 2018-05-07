package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoCategoryMapper;
import org.myoranges.sotwo.db.domain.sotwoCategory;
import org.myoranges.sotwo.db.domain.sotwoCategoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoCategoryService {
    @Resource
    private SotwoCategoryMapper categoryMapper;

    public List<sotwoCategory> queryL1WithoutRecommend(int offset, int limit) {
        sotwoCategoryExample example = new sotwoCategoryExample();
        example.or().andLevelEqualTo("L1").andNameNotEqualTo("推荐").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    public List<sotwoCategory> queryL1(int offset, int limit) {
        sotwoCategoryExample example = new sotwoCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    public List<sotwoCategory> queryL1() {
        sotwoCategoryExample example = new sotwoCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public List<sotwoCategory> queryByPid(Integer pid) {
        sotwoCategoryExample example = new sotwoCategoryExample();
        example.or().andParentIdEqualTo(pid).andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public List<sotwoCategory> queryL2ByIds(List<Integer> ids) {
        sotwoCategoryExample example = new sotwoCategoryExample();
        example.or().andIdIn(ids).andLevelEqualTo("L2").andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public sotwoCategory findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public List<sotwoCategory> querySelective(String id, String name, Integer page, Integer size, String sort, String order) {
        sotwoCategoryExample example = new sotwoCategoryExample();
        sotwoCategoryExample.Criteria criteria = example.createCriteria();

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
        sotwoCategoryExample example = new sotwoCategoryExample();
        sotwoCategoryExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(id)){
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)categoryMapper.countByExample(example);
    }

    public void updateById(sotwoCategory category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    public void deleteById(Integer id) {
        sotwoCategory category = categoryMapper.selectByPrimaryKey(id);
        if(category == null){
            return;
        }
        category.setDeleted(true);
        categoryMapper.updateByPrimaryKey(category);
    }

    public void add(sotwoCategory category) {
        categoryMapper.insertSelective(category);
    }

    private sotwoCategory.Column[] CHANNEL = {sotwoCategory.Column.id, sotwoCategory.Column.name, sotwoCategory.Column.iconUrl};
    public List<sotwoCategory> queryChannel() {
        sotwoCategoryExample example = new sotwoCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExampleSelective(example, CHANNEL);
    }
}
