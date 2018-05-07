package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoTopicMapper;
import org.myoranges.sotwo.db.domain.SotwoTopic;
import org.myoranges.sotwo.db.domain.SotwoTopicExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoTopicService {
    @Resource
    private SotwoTopicMapper topicMapper;

    public List<SotwoTopic> queryList(int offset, int limit) {
        SotwoTopicExample example = new SotwoTopicExample();
        example.or().andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return topicMapper.selectByExampleWithBLOBs(example);
    }

    public int queryTotal() {
        SotwoTopicExample example = new SotwoTopicExample();
        example.or().andDeletedEqualTo(false);
        return (int)topicMapper.countByExample(example);
    }

    public SotwoTopic findById(Integer id) {
        SotwoTopicExample example = new SotwoTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return topicMapper.selectOneByExampleWithBLOBs(example);
    }

    public List<SotwoTopic> queryRelatedList(Integer id, int offset, int limit) {
        SotwoTopicExample example = new SotwoTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<SotwoTopic> topics = topicMapper.selectByExample(example);
        if(topics.size() == 0){
            return queryList(offset, limit);
        }
        SotwoTopic topic = topics.get(0);

        example = new SotwoTopicExample();
        example.or().andIdNotEqualTo(topic.getId()).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        List<SotwoTopic> relateds = topicMapper.selectByExampleWithBLOBs(example);
        if(relateds.size() != 0){
            return relateds;
        }

        return queryList(offset, limit);
    }

    public List<SotwoTopic> querySelective(String title, String subtitle, Integer page, Integer limit, String sort, String order) {
        SotwoTopicExample example = new SotwoTopicExample();
        SotwoTopicExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%" + title + "%");
        }
        if(!StringUtils.isEmpty(subtitle)){
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, limit);
        return topicMapper.selectByExampleWithBLOBs(example);
    }

    public int countSelective(String title, String subtitle, Integer page, Integer size, String sort, String order) {
        SotwoTopicExample example = new SotwoTopicExample();
        SotwoTopicExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%" + title + "%");
        }
        if(!StringUtils.isEmpty(subtitle)){
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)topicMapper.countByExample(example);
    }

    public void updateById(SotwoTopic topic) {
        SotwoTopicExample example = new SotwoTopicExample();
        example.or().andIdEqualTo(topic.getId());
        topicMapper.updateByExampleWithBLOBs(topic, example);
    }

    public void deleteById(Integer id) {
        SotwoTopic topic = topicMapper.selectByPrimaryKey(id);
        if(topic == null){
            return;
        }
        topic.setDeleted(true);
        topicMapper.updateByPrimaryKeySelective(topic);
    }

    public void add(SotwoTopic topic) {
        topicMapper.insertSelective(topic);
    }


}
