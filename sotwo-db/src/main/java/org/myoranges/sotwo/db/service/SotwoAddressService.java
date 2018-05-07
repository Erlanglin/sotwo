package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoAddressMapper;
import org.myoranges.sotwo.db.domain.SotwoAddress;
import org.myoranges.sotwo.db.domain.SotwoAddressExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoAddressService {
    @Resource
    private SotwoAddressMapper addressMapper;

    public List<SotwoAddress> queryByUid(Integer uid) {
        SotwoAddressExample example = new SotwoAddressExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return addressMapper.selectByExample(example);
    }

    public SotwoAddress findById(Integer id) {
        return addressMapper.selectByPrimaryKey(id);
    }

    public int add(SotwoAddress address) {
        return addressMapper.insertSelective(address);
    }

    public int update(SotwoAddress address) {
        return addressMapper.updateByPrimaryKeySelective(address);
    }

    public void delete(Integer id) {
        SotwoAddress address = addressMapper.selectByPrimaryKey(id);
        if(address == null){
            return;
        }
        address.setDeleted(true);
        addressMapper.updateByPrimaryKey(address);
    }

    public SotwoAddress findDefault(Integer userId) {
        SotwoAddressExample example = new SotwoAddressExample();
        example.or().andUserIdEqualTo(userId).andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    public void resetDefault(Integer userId) {
        SotwoAddress address = new SotwoAddress();
        address.setIsDefault(false);
        SotwoAddressExample example = new SotwoAddressExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        addressMapper.updateByExampleSelective(address, example);
    }

    public List<SotwoAddress> querySelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        SotwoAddressExample example = new SotwoAddressExample();
        SotwoAddressExample.Criteria criteria = example.createCriteria();

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
        SotwoAddressExample example = new SotwoAddressExample();
        SotwoAddressExample.Criteria criteria = example.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)addressMapper.countByExample(example);
    }

    public void updateById(SotwoAddress address) {
        addressMapper.updateByPrimaryKeySelective(address);
    }
}
