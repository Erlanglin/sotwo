package org.myoranges.sotwo.db.service;

import org.myoranges.sotwo.db.dao.SotwoConsumeInfoMapper;
import org.myoranges.sotwo.db.domain.SotwoConsumeInfo;
import org.myoranges.sotwo.db.domain.SotwoConsumeInfoExample;
import org.springframework.stereotype.Service;

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

        return (int)consumeInfoMapper.countByExample(example);
    }


    public void deleteById(Integer id) {
        SotwoConsumeInfo consumeInfo = consumeInfoMapper.selectByPrimaryKey(id);
        if(consumeInfo == null){
            return;
        }
        consumeInfoMapper.updateByPrimaryKey(consumeInfo);
    }
}
