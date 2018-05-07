package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoAdminMapper;
import org.myoranges.sotwo.db.domain.sotwoAdmin;
import org.myoranges.sotwo.db.domain.sotwoAdmin.Column;
import org.myoranges.sotwo.db.domain.sotwoAdminExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoAdminService {
    @Resource
    private SotwoAdminMapper adminMapper;

    public List<sotwoAdmin> findAdmin(String username) {
        sotwoAdminExample example = new sotwoAdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

    private final Column[] result = new Column[]{Column.id, Column.username, Column.avatar};
    public List<sotwoAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order) {
        sotwoAdminExample example = new sotwoAdminExample();
        sotwoAdminExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, limit);
        return adminMapper.selectByExampleSelective(example, result);
    }

    public int countSelective(String username, Integer page, Integer size, String sort, String order) {
        sotwoAdminExample example = new sotwoAdminExample();
        sotwoAdminExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)adminMapper.countByExample(example);
    }

    public void updateById(sotwoAdmin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void deleteById(Integer id) {
        sotwoAdmin admin = adminMapper.selectByPrimaryKey(id);
        if(admin == null){
            return;
        }
        admin.setDeleted(true);
        adminMapper.updateByPrimaryKey(admin);
    }

    public void add(sotwoAdmin admin) {
        adminMapper.insertSelective(admin);
    }

    public sotwoAdmin findById(Integer id) {
        return adminMapper.selectByPrimaryKeySelective(id, result);
    }
}
