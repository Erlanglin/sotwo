package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.domain.sotwoBrandExample;
import org.myoranges.sotwo.db.dao.SotwoBrandMapper;
import org.myoranges.sotwo.db.domain.sotwoBrand;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoBrandService {
    @Resource
    private SotwoBrandMapper brandMapper;

    public List<sotwoBrand> queryWithNew(int offset, int limit) {
        sotwoBrandExample example = new sotwoBrandExample();
        example.or().andIsNewEqualTo(true).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return brandMapper.selectByExample(example);
    }

    public List<sotwoBrand> query(int offset, int limit) {
        sotwoBrandExample example = new sotwoBrandExample();
        example.or().andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return brandMapper.selectByExample(example);
    }

    public int queryTotalCount() {
        sotwoBrandExample example = new sotwoBrandExample();
        example.or().andDeletedEqualTo(false);
        return (int)brandMapper.countByExample(example);
    }

    public sotwoBrand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public List<sotwoBrand> querySelective(String id, String name, Integer page, Integer size, String sort, String order) {
        sotwoBrandExample example = new sotwoBrandExample();
        sotwoBrandExample.Criteria criteria = example.createCriteria();

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
        sotwoBrandExample example = new sotwoBrandExample();
        sotwoBrandExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(id)){
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)brandMapper.countByExample(example);
    }

    public void updateById(sotwoBrand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    public void deleteById(Integer id) {
        sotwoBrand brand = brandMapper.selectByPrimaryKey(id);
        if(brand == null){
            return;
        }
        brand.setDeleted(true);
        brandMapper.updateByPrimaryKey(brand);
    }

    public void add(sotwoBrand brand) {
        brandMapper.insertSelective(brand);
    }

}
