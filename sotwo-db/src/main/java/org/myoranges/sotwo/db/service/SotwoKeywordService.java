package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoKeywordMapper;
import org.myoranges.sotwo.db.domain.SotwoKeyword;
import org.myoranges.sotwo.db.domain.SotwoKeywordExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoKeywordService {
    @Resource
    private SotwoKeywordMapper keywordsMapper;

    public List<SotwoKeyword> queryDefaults() {
        SotwoKeywordExample example = new SotwoKeywordExample();
        example.or().andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return keywordsMapper.selectByExample(example);
    }

    public SotwoKeyword queryDefault() {
        SotwoKeywordExample example = new SotwoKeywordExample();
        example.or().andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return keywordsMapper.selectOneByExample(example);
    }

    public List<SotwoKeyword> queryHots() {
        SotwoKeywordExample example = new SotwoKeywordExample();
        example.or().andIsHotEqualTo(true).andDeletedEqualTo(false);
        return keywordsMapper.selectByExample(example);
    }

    public List<SotwoKeyword> queryByKeyword(String keyword, Integer page, Integer size) {
        SotwoKeywordExample example = new SotwoKeywordExample();
        example.setDistinct(true);
        example.or().andKeywordLike("%" + keyword + "%").andDeletedEqualTo(false);
        PageHelper.startPage(page, size);
        return keywordsMapper.selectByExampleSelective(example, SotwoKeyword.Column.keyword);
    }

    public List<SotwoKeyword> querySelective(String keyword, String url, Integer page, Integer limit, String sort, String order) {
        SotwoKeywordExample example = new SotwoKeywordExample();
        SotwoKeywordExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        if (!StringUtils.isEmpty(url)) {
            criteria.andUrlLike("%" + url + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, limit);
        return keywordsMapper.selectByExample(example);
    }

    public int countSelective(String keyword, String url, Integer page, Integer limit, String sort, String order) {
        SotwoKeywordExample example = new SotwoKeywordExample();
        SotwoKeywordExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        if (!StringUtils.isEmpty(url)) {
            criteria.andUrlLike("%" + url + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, limit);
        return (int)keywordsMapper.countByExample(example);
    }

    public void add(SotwoKeyword keywords) {
        keywordsMapper.insertSelective(keywords);
    }

    public SotwoKeyword findById(Integer id) {
        return keywordsMapper.selectByPrimaryKey(id);
    }

    public void updateById(SotwoKeyword keywords) {
        keywordsMapper.updateByPrimaryKeySelective(keywords);
    }

    public void deleteById(Integer id) {
        SotwoKeyword keywords = keywordsMapper.selectByPrimaryKey(id);
        if(keywords == null){
            return;
        }
        keywords.setDeleted(true);
        keywordsMapper.updateByPrimaryKey(keywords);
    }
}
