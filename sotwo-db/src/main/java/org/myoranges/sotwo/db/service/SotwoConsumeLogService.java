package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoConsumeLogMapper;
import org.myoranges.sotwo.db.domain.SotwoConsumeLog;
import org.myoranges.sotwo.db.domain.SotwoConsumeLogExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoConsumeLogService {
    @Resource
    private SotwoConsumeLogMapper consumeLogMapper;

    public SotwoConsumeLog findById(Integer consumeLogId) {
        return consumeLogMapper.selectByPrimaryKey(consumeLogId);
    }

    public void add(SotwoConsumeLog consumeLog) {
        consumeLogMapper.insertSelective(consumeLog);
    }

    public void update(SotwoConsumeLog consumeLog) {
        consumeLogMapper.updateByPrimaryKeySelective(consumeLog);
    }


    public int count() {
        SotwoConsumeLogExample example = new SotwoConsumeLogExample();
        example.or();

        return (int) consumeLogMapper.countByExample(example);
    }

    public List<SotwoConsumeLog> queryByConsumeCategoryId(Integer consumeCategoryId) {
        SotwoConsumeLogExample example = new SotwoConsumeLogExample();
        example.or().andConsumeCategoryIdEqualTo(consumeCategoryId);
        return consumeLogMapper.selectByExample(example);
    }

    public List<SotwoConsumeLog> querySelective(Integer consumeCategoryId, Integer payUserId, Integer page, Integer size, String sort, String order) {
        SotwoConsumeLogExample example = new SotwoConsumeLogExample();
        SotwoConsumeLogExample.Criteria criteria = example.createCriteria();

        if (consumeCategoryId != null) {
            criteria.andConsumeCategoryIdEqualTo(consumeCategoryId);
        }
        if (payUserId != null) {
            criteria.andPayUserIdEqualTo(payUserId);
        }
        PageHelper.startPage(page, size);
        return consumeLogMapper.selectByExample(example);
    }

    public int countSeletive(Integer consumeCategoryId, Integer payUserId, Integer page, Integer size, String sort, String order) {
        SotwoConsumeLogExample example = new SotwoConsumeLogExample();
        SotwoConsumeLogExample.Criteria criteria = example.createCriteria();
        if (consumeCategoryId != null) {
            criteria.andConsumeCategoryIdEqualTo(consumeCategoryId);
        }
        if (payUserId != null) {
            criteria.andPayUserIdEqualTo(payUserId);
        }

        return (int) consumeLogMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        SotwoConsumeLog consumeLog = consumeLogMapper.selectByPrimaryKey(id);
        if (consumeLog == null) {
            return;
        }
        consumeLogMapper.updateByPrimaryKey(consumeLog);
    }
}
