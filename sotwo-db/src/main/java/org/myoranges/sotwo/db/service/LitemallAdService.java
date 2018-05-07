package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.domain.sotwoAd;
import org.myoranges.sotwo.db.domain.sotwoAdExample;
import org.myoranges.sotwo.db.dao.SotwoAdMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoAdService {
    @Resource
    private SotwoAdMapper adMapper;

    public List<sotwoAd> queryByApid(Integer i) {
        sotwoAdExample example = new sotwoAdExample();
        example.or().andPositionEqualTo(i).andDeletedEqualTo(false);
        return adMapper.selectByExample(example);
    }

    public List<sotwoAd> querySelective(String name, String content, Integer page, Integer limit, String sort, String order) {
        sotwoAdExample example = new sotwoAdExample();
        sotwoAdExample.Criteria criteria = example.createCriteria();

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
        sotwoAdExample example = new sotwoAdExample();
        sotwoAdExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)adMapper.countByExample(example);
    }

    public void updateById(sotwoAd ad) {
        adMapper.updateByPrimaryKeySelective(ad);
    }

    public void deleteById(Integer id) {
        sotwoAd ad = adMapper.selectByPrimaryKey(id);
        if(ad == null){
            return;
        }
        ad.setDeleted(true);
        adMapper.updateByPrimaryKey(ad);
    }

    public void add(sotwoAd ad) {
        adMapper.insertSelective(ad);
    }

    public sotwoAd findById(Integer id) {
        return adMapper.selectByPrimaryKey(id);
    }
}
