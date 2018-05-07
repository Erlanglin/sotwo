package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;

import org.myoranges.sotwo.db.dao.SotwoAdminMapper;
import org.myoranges.sotwo.db.domain.SotwoAdmin;
import org.myoranges.sotwo.db.domain.SotwoAdminExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoAdminService {
    @Resource
    private SotwoAdminMapper adminMapper;

    public List<SotwoAdmin> findAdmin(String username) {
        SotwoAdminExample example = new SotwoAdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

    private final SotwoAdmin.Column[] result = new SotwoAdmin.Column[]{SotwoAdmin.Column.id, SotwoAdmin.Column.username, SotwoAdmin.Column.avatar};
    public List<SotwoAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order) {
        SotwoAdminExample example = new SotwoAdminExample();
        SotwoAdminExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, limit);
        return adminMapper.selectByExampleSelective(example, result);
    }

    public int countSelective(String username, Integer page, Integer size, String sort, String order) {
        SotwoAdminExample example = new SotwoAdminExample();
        SotwoAdminExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)adminMapper.countByExample(example);
    }

    public void updateById(SotwoAdmin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void deleteById(Integer id) {
        SotwoAdmin admin = adminMapper.selectByPrimaryKey(id);
        if(admin == null){
            return;
        }
        admin.setDeleted(true);
        adminMapper.updateByPrimaryKey(admin);
    }

    public void add(SotwoAdmin admin) {
        adminMapper.insertSelective(admin);
    }

    public SotwoAdmin findById(Integer id) {
        return adminMapper.selectByPrimaryKeySelective(id, result);
    }
}
