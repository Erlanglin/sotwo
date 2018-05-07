package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoAdMapper;
import org.myoranges.sotwo.db.domain.SotwoAd;
import org.myoranges.sotwo.db.domain.SotwoAdExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoAdService {
    @Resource
    private SotwoAdMapper adMapper;

    public List<SotwoAd> queryByApid(Integer i) {
        SotwoAdExample example = new SotwoAdExample();
        example.or().andPositionEqualTo(i).andDeletedEqualTo(false);
        return adMapper.selectByExample(example);
    }

    public List<SotwoAd> querySelective(String name, String content, Integer page, Integer limit, String sort, String order) {
        SotwoAdExample example = new SotwoAdExample();
        SotwoAdExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, limit);
        return adMapper.selectByExample(example);
    }

    public int countSelective(String name, String content, Integer page, Integer size, String sort, String order) {
        SotwoAdExample example = new SotwoAdExample();
        SotwoAdExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)adMapper.countByExample(example);
    }

    public void updateById(SotwoAd ad) {
        adMapper.updateByPrimaryKeySelective(ad);
    }

    public void deleteById(Integer id) {
        SotwoAd ad = adMapper.selectByPrimaryKey(id);
        if(ad == null){
            return;
        }
        ad.setDeleted(true);
        adMapper.updateByPrimaryKey(ad);
    }

    public void add(SotwoAd ad) {
        adMapper.insertSelective(ad);
    }

    public SotwoAd findById(Integer id) {
        return adMapper.selectByPrimaryKey(id);
    }
}
