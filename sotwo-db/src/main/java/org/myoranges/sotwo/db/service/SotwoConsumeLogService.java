package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoConsumeLogMapper;
import org.myoranges.sotwo.db.domain.SotwoConsumeInfo;
import org.myoranges.sotwo.db.domain.SotwoConsumeInfoExample;
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

    public int add(SotwoConsumeLog consumeLog) {
        return consumeLogMapper.insertSelective(consumeLog);
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

    public List<SotwoConsumeLog> querySelective( SotwoConsumeLog sotwoConsumeLog, Integer page, Integer size, String sort, String order) {
        PageHelper.startPage(page, size);
        return consumeLogMapper.selectByExample(this.entitytoExample(sotwoConsumeLog));
    }

    public int countSeletive(SotwoConsumeLog sotwoConsumeLog, Integer page, Integer size, String sort, String order) {
        return (int) consumeLogMapper.countByExample(this.entitytoExample(sotwoConsumeLog));
    }

    public void deleteById(Integer id) {
        SotwoConsumeLog consumeLog = consumeLogMapper.selectByPrimaryKey(id);
        if (consumeLog == null) {
            return;
        }
        consumeLogMapper.updateByPrimaryKey(consumeLog);
    }

    public SotwoConsumeLogExample entitytoExample(SotwoConsumeLog sotwoConsumeLog) {
        SotwoConsumeLogExample sotwoConsumeLogExample = new SotwoConsumeLogExample();
        SotwoConsumeLogExample.Criteria criteria = sotwoConsumeLogExample.createCriteria();
        if (sotwoConsumeLog.getId() != null) {
            criteria.andIdEqualTo(sotwoConsumeLog.getId());
        }
        if (sotwoConsumeLog.getAddTime() != null) {
            criteria.andAddTimeEqualTo(sotwoConsumeLog.getAddTime());
        }
        if (sotwoConsumeLog.getConsumeAddress() != null) {
            criteria.andConsumeAddressEqualTo(sotwoConsumeLog.getConsumeAddress());
        }
        if (sotwoConsumeLog.getConsumeCategoryId() != null) {
            criteria.andConsumeCategoryIdEqualTo(sotwoConsumeLog.getConsumeCategoryId());
        }
        if (sotwoConsumeLog.getConsumeTitle() != null) {
            criteria.andConsumeTitleEqualTo(sotwoConsumeLog.getConsumeTitle());
        }
        if (sotwoConsumeLog.getHandlerTime() != null) {
            criteria.andHandlerTimeEqualTo(sotwoConsumeLog.getHandlerTime());
        }
        if (sotwoConsumeLog.getPayUserId() != null) {
            criteria.andPayUserIdEqualTo(sotwoConsumeLog.getPayUserId());
        }
        if (sotwoConsumeLog.getPrice() != null) {
            criteria.andPriceEqualTo(sotwoConsumeLog.getPrice());
        }
        if (sotwoConsumeLog.getRegTime() != null) {
            criteria.andRegTimeEqualTo(sotwoConsumeLog.getRegTime());
        }
        if (sotwoConsumeLog.getStatus() != null) {
            criteria.andStatusEqualTo(sotwoConsumeLog.getStatus());
        }
        return sotwoConsumeLogExample;
    }


}
