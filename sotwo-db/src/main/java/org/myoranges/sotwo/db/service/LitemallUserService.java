package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoUserMapper;
import org.myoranges.sotwo.db.domain.sotwoUser;
import org.myoranges.sotwo.db.domain.sotwoUserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoUserService {
    @Resource
    private SotwoUserMapper userMapper;

    public sotwoUser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public sotwoUser queryByOid(String openId) {
        sotwoUserExample example = new sotwoUserExample();
        example.or().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    public void add(sotwoUser user) {
        userMapper.insertSelective(user);
    }

    public void update(sotwoUser user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    public List<sotwoUser> querySelective(String username, String mobile, Integer page, Integer size, String sort, String order) {
        sotwoUserExample example = new sotwoUserExample();
        sotwoUserExample.Criteria criteria = example.createCriteria();

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
        sotwoUserExample example = new sotwoUserExample();
        sotwoUserExample.Criteria criteria = example.createCriteria();

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
        sotwoUserExample example = new sotwoUserExample();
        example.or().andDeletedEqualTo(false);

        return (int)userMapper.countByExample(example);
    }

    public List<sotwoUser> queryByUsername(String username) {
        sotwoUserExample example = new sotwoUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public List<sotwoUser> queryByMobile(String mobile) {
        sotwoUserExample example = new sotwoUserExample();
        example.or().andMobileEqualTo(mobile).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        sotwoUser user = userMapper.selectByPrimaryKey(id);
        if(user == null){
            return;
        }
        user.setDeleted(true);
        userMapper.updateByPrimaryKey(user);
    }
}
