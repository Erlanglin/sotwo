package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.*;
import org.myoranges.sotwo.db.domain.SotwoRegion;
import org.myoranges.sotwo.db.domain.SotwoRegionExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoRegionService {
    @Resource
    private SotwoRegionMapper regionMapper;
    @Resource
    private SotwoRegionProvinceMapper provinceMapper;
    @Resource
    private SotwoRegionCityMapper cityMapper;
    @Resource
    private SotwoRegionAreaMapper areaMapper;
    @Resource
    private SotwoRegionStreetMapper streetMapper;

    public List<SotwoRegion> queryByPid(Integer parentId) {
        SotwoRegionExample example = new SotwoRegionExample();
        example.or().andPidEqualTo(parentId);
        return regionMapper.selectByExample(example);
    }

    public SotwoRegion findById(Integer id) {
        return regionMapper.selectByPrimaryKey(id);
    }

    public List<SotwoRegion> querySelective(String name, Integer code, Integer page, Integer size, String sort, String order) {
        SotwoRegionExample example = new SotwoRegionExample();
        SotwoRegionExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(code != null){
            criteria.andCodeEqualTo(code);
        }
        PageHelper.startPage(page, size);
        return regionMapper.selectByExample(example);
    }

    public int countSelective(String name, Integer code, Integer page, Integer size, String sort, String order) {
        SotwoRegionExample example = new SotwoRegionExample();
        SotwoRegionExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(code != null){
            criteria.andCodeEqualTo(code);
        }
        return (int)regionMapper.countByExample(example);
    }
}
