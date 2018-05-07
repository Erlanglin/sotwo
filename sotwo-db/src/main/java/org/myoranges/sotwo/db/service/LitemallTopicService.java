package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoTopicMapper;
import org.myoranges.sotwo.db.domain.sotwoTopic;
import org.myoranges.sotwo.db.domain.sotwoTopicExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoTopicService {
    @Resource
    private SotwoTopicMapper topicMapper;

    public List<sotwoTopic> queryList(int offset, int limit) {
        sotwoTopicExample example = new sotwoTopicExample();
        example.or().andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return topicMapper.selectByExampleWithBLOBs(example);
    }

    public int queryTotal() {
        sotwoTopicExample example = new sotwoTopicExample();
        example.or().andDeletedEqualTo(false);
        return (int)topicMapper.countByExample(example);
    }

    public sotwoTopic findById(Integer id) {
        sotwoTopicExample example = new sotwoTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return topicMapper.selectOneByExampleWithBLOBs(example);
    }

    public List<sotwoTopic> queryRelatedList(Integer id, int offset, int limit) {
        sotwoTopicExample example = new sotwoTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<sotwoTopic> topics = topicMapper.selectByExample(example);
        if(topics.size() == 0){
            return queryList(offset, limit);
        }
        sotwoTopic topic = topics.get(0);

        example = new sotwoTopicExample();
        example.or().andIdNotEqualTo(topic.getId()).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        List<sotwoTopic> relateds = topicMapper.selectByExampleWithBLOBs(example);
        if(relateds.size() != 0){
            return relateds;
        }

        return queryList(offset, limit);
    }

    public List<sotwoTopic> querySelective(String title, String subtitle, Integer page, Integer limit, String sort, String order) {
        sotwoTopicExample example = new sotwoTopicExample();
        sotwoTopicExample.Criteria criteria = example.createCriteria();

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
        sotwoTopicExample example = new sotwoTopicExample();
        sotwoTopicExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%" + title + "%");
        }
        if(!StringUtils.isEmpty(subtitle)){
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)topicMapper.countByExample(example);
    }

    public void updateById(sotwoTopic topic) {
        sotwoTopicExample example = new sotwoTopicExample();
        example.or().andIdEqualTo(topic.getId());
        topicMapper.updateByExampleWithBLOBs(topic, example);
    }

    public void deleteById(Integer id) {
        sotwoTopic topic = topicMapper.selectByPrimaryKey(id);
        if(topic == null){
            return;
        }
        topic.setDeleted(true);
        topicMapper.updateByPrimaryKeySelective(topic);
    }

    public void add(sotwoTopic topic) {
        topicMapper.insertSelective(topic);
    }


}
