package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;

import org.myoranges.sotwo.db.dao.SotwoBrandMapper;
import org.myoranges.sotwo.db.domain.SotwoBrand;
import org.myoranges.sotwo.db.domain.SotwoBrandExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoBrandService {
    @Resource
    private SotwoBrandMapper brandMapper;

    public List<SotwoBrand> queryWithNew(int offset, int limit) {
        SotwoBrandExample example = new SotwoBrandExample();
        example.or().andIsNewEqualTo(true).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return brandMapper.selectByExample(example);
    }

    public List<SotwoBrand> query(int offset, int limit) {
        SotwoBrandExample example = new SotwoBrandExample();
        example.or().andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return brandMapper.selectByExample(example);
    }

    public int queryTotalCount() {
        SotwoBrandExample example = new SotwoBrandExample();
        example.or().andDeletedEqualTo(false);
        return (int)brandMapper.countByExample(example);
    }

    public SotwoBrand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public List<SotwoBrand> querySelective(String id, String name, Integer page, Integer size, String sort, String order) {
        SotwoBrandExample example = new SotwoBrandExample();
        SotwoBrandExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(id)){
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return brandMapper.selectByExample(example);
    }

    public int countSelective(String id, String name, Integer page, Integer size, String sort, String order) {
        SotwoBrandExample example = new SotwoBrandExample();
        SotwoBrandExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(id)){
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)brandMapper.countByExample(example);
    }

    public void updateById(SotwoBrand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    public void deleteById(Integer id) {
        SotwoBrand brand = brandMapper.selectByPrimaryKey(id);
        if(brand == null){
            return;
        }
        brand.setDeleted(true);
        brandMapper.updateByPrimaryKey(brand);
    }

    public void add(SotwoBrand brand) {
        brandMapper.insertSelective(brand);
    }

}
