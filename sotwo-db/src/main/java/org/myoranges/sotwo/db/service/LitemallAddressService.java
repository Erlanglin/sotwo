package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.sotwoAddressMapper;
import org.myoranges.sotwo.db.domain.sotwoAddress;
import org.myoranges.sotwo.db.domain.sotwoAddressExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoAddressService {
    @Resource
    private sotwoAddressMapper addressMapper;

    public List<sotwoAddress> queryByUid(Integer uid) {
        sotwoAddressExample example = new sotwoAddressExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return addressMapper.selectByExample(example);
    }

    public sotwoAddress findById(Integer id) {
        return addressMapper.selectByPrimaryKey(id);
    }

    public int add(sotwoAddress address) {
        return addressMapper.insertSelective(address);
    }

    public int update(sotwoAddress address) {
        return addressMapper.updateByPrimaryKeySelective(address);
    }

    public void delete(Integer id) {
        sotwoAddress address = addressMapper.selectByPrimaryKey(id);
        if(address == null){
            return;
        }
        address.setDeleted(true);
        addressMapper.updateByPrimaryKey(address);
    }

    public sotwoAddress findDefault(Integer userId) {
        sotwoAddressExample example = new sotwoAddressExample();
        example.or().andUserIdEqualTo(userId).andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    public void resetDefault(Integer userId) {
        sotwoAddress address = new sotwoAddress();
        address.setIsDefault(false);
        sotwoAddressExample example = new sotwoAddressExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        addressMapper.updateByExampleSelective(address, example);
    }

    public List<sotwoAddress> querySelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        sotwoAddressExample example = new sotwoAddressExample();
        sotwoAddressExample.Criteria criteria = example.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, limit);
        return addressMapper.selectByExample(example);
    }

    public int countSelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        sotwoAddressExample example = new sotwoAddressExample();
        sotwoAddressExample.Criteria criteria = example.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)addressMapper.countByExample(example);
    }

    public void updateById(sotwoAddress address) {
        addressMapper.updateByPrimaryKeySelective(address);
    }
}
