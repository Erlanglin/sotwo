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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class SotwoConsumeInfoService {
    @Resource
    private SotwoConsumeInfoMapper consumeInfoMapper;

    public SotwoConsumeInfo findById(Integer consumeInfoId) {
        return consumeInfoMapper.selectByPrimaryKey(consumeInfoId);
    }

    public int add(SotwoConsumeInfo consumeInfo) {
        return consumeInfoMapper.insertSelective(consumeInfo);
    }

    public int updateByPrimaryKey(SotwoConsumeInfo consumeInfo) {
        return consumeInfoMapper.updateByPrimaryKeySelective(consumeInfo);
    }
    public int update(SotwoConsumeInfo consumeInfo) {
        return consumeInfoMapper.updateByPrimaryKeySelective(consumeInfo);
    }

    public int updateStatus(Integer consumeLogId, Integer userId) {
        List<SotwoConsumeInfo> sotwoConsumeInfos = this.queryByConsumeLogIdAndUserId(consumeLogId,userId);
        if (sotwoConsumeInfos.size() == 1){
            for(SotwoConsumeInfo sotwoConsumeInfo : sotwoConsumeInfos){
                sotwoConsumeInfo.setStatus(1);
                sotwoConsumeInfo.setHandlerTime(LocalDateTime.now());
                return this.updateByPrimaryKey(sotwoConsumeInfo);
            }
        }
        return 0;
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

    public List<SotwoConsumeInfo> queryByConsumeLogIdAndUserId(Integer consumeLogId, Integer userId) {
        SotwoConsumeInfo sotwoConsumeInfo = new SotwoConsumeInfo();
        sotwoConsumeInfo.setConsumeLogId(consumeLogId);
        sotwoConsumeInfo.setUserId(userId);
        return consumeInfoMapper.selectByExample(this.entitytoExample(sotwoConsumeInfo));
    }

    public List<SotwoConsumeInfo> querySelective(SotwoConsumeInfo sotwoConsumeInfo, Integer page, Integer limit, String sort, String order) {
        return consumeInfoMapper.selectByExample(this.entitytoExample(sotwoConsumeInfo));
    }
    public List<SotwoConsumeInfo> querySelective(SotwoConsumeInfo sotwoConsumeInfo, String startTime, String endTime, Integer page, Integer size, String sort, String order) {
        return consumeInfoMapper.selectByExample(this.entitytoExample(sotwoConsumeInfo));
    }

    public int countSeletive(SotwoConsumeInfo sotwoConsumeInfo, Integer page, Integer size, String sort, String order) {
        return (int) consumeInfoMapper.countByExample(this.entitytoExample(sotwoConsumeInfo));
    }

    public void deleteById(Integer id) {
        SotwoConsumeInfo consumeInfo = consumeInfoMapper.selectByPrimaryKey(id);
        if (consumeInfo == null) {
            return;
        }
        consumeInfoMapper.updateByPrimaryKey(consumeInfo);
    }

    public SotwoConsumeInfoExample entitytoExample(SotwoConsumeInfo sotwoConsumeInfo) {
        SotwoConsumeInfoExample sotwoConsumeInfoExample = new SotwoConsumeInfoExample();
        SotwoConsumeInfoExample.Criteria criteria = sotwoConsumeInfoExample.createCriteria();
        if (sotwoConsumeInfo.getId() != null) {
            criteria.andIdEqualTo(sotwoConsumeInfo.getId());
        }
        if (sotwoConsumeInfo.getUserId() != null) {
            criteria.andUserIdEqualTo(sotwoConsumeInfo.getUserId());
        }
        if (sotwoConsumeInfo.getAddTime() != null) {
            criteria.andAddTimeEqualTo(sotwoConsumeInfo.getAddTime());
        }
        if (sotwoConsumeInfo.getConsumeLogId() != null) {
            criteria.andConsumeLogIdEqualTo(sotwoConsumeInfo.getConsumeLogId());
        }
        if (sotwoConsumeInfo.getHandlerTime() != null) {
            criteria.andHandlerTimeEqualTo(sotwoConsumeInfo.getHandlerTime());
        }
        if (sotwoConsumeInfo.getPayStatus() != null) {
            criteria.andPayStatusEqualTo(sotwoConsumeInfo.getPayStatus());
        }
        if (sotwoConsumeInfo.getStatus() != null) {
            criteria.andStatusEqualTo(sotwoConsumeInfo.getStatus());
        }
        if (sotwoConsumeInfo.getPrice() != null) {
            criteria.andPriceEqualTo(sotwoConsumeInfo.getPrice());
        }
        return sotwoConsumeInfoExample;
    }

}
