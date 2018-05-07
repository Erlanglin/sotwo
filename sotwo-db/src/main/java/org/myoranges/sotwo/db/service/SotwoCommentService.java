package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoCommentMapper;
import org.myoranges.sotwo.db.domain.SotwoComment;
import org.myoranges.sotwo.db.domain.SotwoCommentExample;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoCommentService {
    @Resource
    private SotwoCommentMapper commentMapper;

    public List<SotwoComment> queryGoodsByGid(Integer id, int offset, int limit) {
        SotwoCommentExample example = new SotwoCommentExample();
        example.setOrderByClause(SotwoComment.Column.addTime.desc());
        example.or().andValueIdEqualTo(id).andTypeIdEqualTo((byte)0).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return commentMapper.selectByExample(example);
    }

    public int countGoodsByGid(Integer id, int offset, int limit) {
        SotwoCommentExample example = new SotwoCommentExample();
        example.or().andValueIdEqualTo(id).andTypeIdEqualTo((byte)0).andDeletedEqualTo(false);
        return (int)commentMapper.countByExample(example);
    }

    public List<SotwoComment> query(Byte typeId, Integer valueId, Integer showType, Integer offset, Integer limit) {
        SotwoCommentExample example = new SotwoCommentExample();
        example.setOrderByClause(SotwoComment.Column.addTime.desc());
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
        SotwoCommentExample example = new SotwoCommentExample();
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

    public Integer save(SotwoComment comment) {
        return commentMapper.insertSelective(comment);
    }


    public void update(SotwoComment comment) {
        commentMapper.updateByPrimaryKeySelective(comment);
    }


    public List<SotwoComment> querySelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        SotwoCommentExample example = new SotwoCommentExample();
        example.setOrderByClause(SotwoComment.Column.addTime.desc());
        SotwoCommentExample.Criteria criteria = example.createCriteria();

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
        SotwoCommentExample example = new SotwoCommentExample();
        SotwoCommentExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(Integer.valueOf(valueId)).andTypeIdEqualTo((byte)0);
        }
        criteria.andDeletedEqualTo(false);

        return (int)commentMapper.countByExample(example);
    }

    public void updateById(SotwoComment comment) {
        commentMapper.updateByPrimaryKeySelective(comment);
    }

    public void deleteById(Integer id) {
        SotwoComment comment = commentMapper.selectByPrimaryKey(id);
        if(comment == null){
            return;
        }
        comment.setDeleted(true);
        commentMapper.updateByPrimaryKey(comment);
    }

    public void add(SotwoComment comment) {
        commentMapper.insertSelective(comment);
    }

    public SotwoComment findById(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }
}
