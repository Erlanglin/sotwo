package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoUserMapper;
import org.myoranges.sotwo.db.domain.SotwoUser;
import org.myoranges.sotwo.db.domain.SotwoUserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoUserService {
    @Resource
    private SotwoUserMapper userMapper;

    public SotwoUser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public SotwoUser queryByOid(String openId) {
        SotwoUserExample example = new SotwoUserExample();
        example.or().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    public void add(SotwoUser user) {
        userMapper.insertSelective(user);
    }

    public void update(SotwoUser user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    public List<SotwoUser> querySelective(String username, String mobile, Integer page, Integer size, String sort, String order) {
        SotwoUserExample example = new SotwoUserExample();
        SotwoUserExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        if(!StringUtils.isEmpty(mobile)){
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }

    public int countSeletive(String username, String mobile, Integer page, Integer size, String sort, String order) {
        SotwoUserExample example = new SotwoUserExample();
        SotwoUserExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        if(!StringUtils.isEmpty(mobile)){
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andDeletedEqualTo(false);

        return (int) userMapper.countByExample(example);
    }

    public int count() {
        SotwoUserExample example = new SotwoUserExample();
        example.or().andDeletedEqualTo(false);

        return (int)userMapper.countByExample(example);
    }

    public List<SotwoUser> queryByUsername(String username) {
        SotwoUserExample example = new SotwoUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public List<SotwoUser> queryByMobile(String mobile) {
        SotwoUserExample example = new SotwoUserExample();
        example.or().andMobileEqualTo(mobile).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        SotwoUser user = userMapper.selectByPrimaryKey(id);
        if(user == null){
            return;
        }
        user.setDeleted(true);
        userMapper.updateByPrimaryKey(user);
    }
}
