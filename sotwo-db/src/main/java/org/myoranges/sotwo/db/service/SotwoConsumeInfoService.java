package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoConsumeInfoMapper;
import org.myoranges.sotwo.db.domain.SotwoConsumeInfo;
import org.myoranges.sotwo.db.domain.SotwoConsumeInfoExample;
import org.myoranges.sotwo.db.domain.SotwoUser;
import org.myoranges.sotwo.db.domain.SotwoUserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoConsumeInfoService {
    @Resource
    private SotwoConsumeInfoMapper consumeInfoMapper;

    public SotwoConsumeInfo findById(Integer consumeInfoId) {
        return consumeInfoMapper.selectByPrimaryKey(consumeInfoId);
    }

    public void add(SotwoConsumeInfo consumeInfo) {
        consumeInfoMapper.insertSelective(consumeInfo);
    }

    public void update(SotwoConsumeInfo consumeInfo) {
        consumeInfoMapper.updateByPrimaryKeySelective(consumeInfo);
    }


    public int count() {
        SotwoConsumeInfoExample example = new SotwoConsumeInfoExample();
        example.or();

        return (int) consumeInfoMapper.countByExample(example);
    }

    public List<SotwoConsumeInfo> queryByConsumeLogId(Integer consumeLogId) {
        SotwoConsumeInfoExample example = new SotwoConsumeInfoExample();
        example.or().andConsumeLogIdEqualTo(consumeLogId);
        return consumeInfoMapper.selectByExample(example);
    }

    public List<SotwoConsumeInfo> querySelective(Integer consumeLogId, Integer userId, Integer page, Integer size, String sort, String order) {
        SotwoConsumeInfoExample example = new SotwoConsumeInfoExample();
        SotwoConsumeInfoExample.Criteria criteria = example.createCriteria();

        if (consumeLogId != null) {
            criteria.andConsumeLogIdEqualTo(consumeLogId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        PageHelper.startPage(page, size);
        return consumeInfoMapper.selectByExample(example);
    }

    public int countSeletive(Integer consumeLogId, Integer userId, Integer page, Integer size, String sort, String order) {
        SotwoConsumeInfoExample example = new SotwoConsumeInfoExample();
        SotwoConsumeInfoExample.Criteria criteria = example.createCriteria();
        if (consumeLogId != null) {
            criteria.andConsumeLogIdEqualTo(consumeLogId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        return (int) consumeInfoMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        SotwoConsumeInfo consumeInfo = consumeInfoMapper.selectByPrimaryKey(id);
        if (consumeInfo == null) {
            return;
        }
        consumeInfoMapper.updateByPrimaryKey(consumeInfo);
    }
}
