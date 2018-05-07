package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoKeywordMapper;
import org.myoranges.sotwo.db.domain.sotwoKeyword;
import org.myoranges.sotwo.db.domain.sotwoKeywordExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoKeywordService {
    @Resource
    private SotwoKeywordMapper keywordsMapper;

    public List<sotwoKeyword> queryDefaults() {
        sotwoKeywordExample example = new sotwoKeywordExample();
        example.or().andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return keywordsMapper.selectByExample(example);
    }

    public sotwoKeyword queryDefault() {
        sotwoKeywordExample example = new sotwoKeywordExample();
        example.or().andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return keywordsMapper.selectOneByExample(example);
    }

    public List<sotwoKeyword> queryHots() {
        sotwoKeywordExample example = new sotwoKeywordExample();
        example.or().andIsHotEqualTo(true).andDeletedEqualTo(false);
        return keywordsMapper.selectByExample(example);
    }

    public List<sotwoKeyword> queryByKeyword(String keyword, Integer page, Integer size) {
        sotwoKeywordExample example = new sotwoKeywordExample();
        example.setDistinct(true);
        example.or().andKeywordLike("%" + keyword + "%").andDeletedEqualTo(false);
        PageHelper.startPage(page, size);
        return keywordsMapper.selectByExampleSelective(example, sotwoKeyword.Column.keyword);
    }

    public List<sotwoKeyword> querySelective(String keyword, String url, Integer page, Integer limit, String sort, String order) {
        sotwoKeywordExample example = new sotwoKeywordExample();
        sotwoKeywordExample.Criteria criteria = example.createCriteria();

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
        sotwoKeywordExample example = new sotwoKeywordExample();
        sotwoKeywordExample.Criteria criteria = example.createCriteria();

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

    public void add(sotwoKeyword keywords) {
        keywordsMapper.insertSelective(keywords);
    }

    public sotwoKeyword findById(Integer id) {
        return keywordsMapper.selectByPrimaryKey(id);
    }

    public void updateById(sotwoKeyword keywords) {
        keywordsMapper.updateByPrimaryKeySelective(keywords);
    }

    public void deleteById(Integer id) {
        sotwoKeyword keywords = keywordsMapper.selectByPrimaryKey(id);
        if(keywords == null){
            return;
        }
        keywords.setDeleted(true);
        keywordsMapper.updateByPrimaryKey(keywords);
    }
}
