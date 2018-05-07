package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoCommentMapper;
import org.myoranges.sotwo.db.domain.sotwoComment;
import org.myoranges.sotwo.db.domain.sotwoCommentExample;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoCommentService {
    @Resource
    private SotwoCommentMapper commentMapper;

    public List<sotwoComment> queryGoodsByGid(Integer id, int offset, int limit) {
        sotwoCommentExample example = new sotwoCommentExample();
        example.setOrderByClause(sotwoComment.Column.addTime.desc());
        example.or().andValueIdEqualTo(id).andTypeIdEqualTo((byte)0).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return commentMapper.selectByExample(example);
    }

    public int countGoodsByGid(Integer id, int offset, int limit) {
        sotwoCommentExample example = new sotwoCommentExample();
        example.or().andValueIdEqualTo(id).andTypeIdEqualTo((byte)0).andDeletedEqualTo(false);
        return (int)commentMapper.countByExample(example);
    }

    public List<sotwoComment> query(Byte typeId, Integer valueId, Integer showType, Integer offset, Integer limit) {
        sotwoCommentExample example = new sotwoCommentExample();
        example.setOrderByClause(sotwoComment.Column.addTime.desc());
        if(showType == 0) {
            example.or().andValueIdEqualTo(valueId).andTypeIdEqualTo(typeId).andDeletedEqualTo(false);
        }
        else if(showType == 1){
            example.or().andValueIdEqualTo(valueId).andTypeIdEqualTo(typeId).andHasPictureEqualTo(true).andDeletedEqualTo(false);
        }
        else{
            Assert.state(false, "showType不支持");
        }
        PageHelper.startPage(offset, limit);
        return commentMapper.selectByExample(example);
    }

    public int count(Byte typeId, Integer valueId, Integer showType, Integer offset, Integer size){
        sotwoCommentExample example = new sotwoCommentExample();
        if(showType == 0) {
            example.or().andValueIdEqualTo(valueId).andTypeIdEqualTo(typeId).andDeletedEqualTo(false);
        }
        else if(showType == 1){
            example.or().andValueIdEqualTo(valueId).andTypeIdEqualTo(typeId).andHasPictureEqualTo(true).andDeletedEqualTo(false);
        }
        else{
            Assert.state(false, "");
        }
        return (int)commentMapper.countByExample(example);
    }

    public Integer save(sotwoComment comment) {
        return commentMapper.insertSelective(comment);
    }


    public void update(sotwoComment comment) {
        commentMapper.updateByPrimaryKeySelective(comment);
    }


    public List<sotwoComment> querySelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        sotwoCommentExample example = new sotwoCommentExample();
        example.setOrderByClause(sotwoComment.Column.addTime.desc());
        sotwoCommentExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(Integer.valueOf(valueId)).andTypeIdEqualTo((byte)0);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return commentMapper.selectByExample(example);
    }

    public int countSelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        sotwoCommentExample example = new sotwoCommentExample();
        sotwoCommentExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(Integer.valueOf(valueId)).andTypeIdEqualTo((byte)0);
        }
        criteria.andDeletedEqualTo(false);

        return (int)commentMapper.countByExample(example);
    }

    public void updateById(sotwoComment comment) {
        commentMapper.updateByPrimaryKeySelective(comment);
    }

    public void deleteById(Integer id) {
        sotwoComment comment = commentMapper.selectByPrimaryKey(id);
        if(comment == null){
            return;
        }
        comment.setDeleted(true);
        commentMapper.updateByPrimaryKey(comment);
    }

    public void add(sotwoComment comment) {
        commentMapper.insertSelective(comment);
    }

    public sotwoComment findById(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }
}
