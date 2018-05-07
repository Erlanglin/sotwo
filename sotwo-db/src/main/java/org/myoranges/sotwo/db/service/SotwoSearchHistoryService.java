package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoSearchHistoryMapper;
import org.myoranges.sotwo.db.domain.SotwoSearchHistory;
import org.myoranges.sotwo.db.domain.SotwoSearchHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoSearchHistoryService {
    @Resource
    private SotwoSearchHistoryMapper searchHistoryMapper;

    public void save(SotwoSearchHistory searchHistory) {
        searchHistoryMapper.insertSelective(searchHistory);
    }

    public List<SotwoSearchHistory> queryByUid(int uid) {
        SotwoSearchHistoryExample example = new SotwoSearchHistoryExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        example.setDistinct(true);
        return searchHistoryMapper.selectByExampleSelective(example, SotwoSearchHistory.Column.keyword);
    }

    public void deleteByUid(int uid) {
        SotwoSearchHistoryExample example = new SotwoSearchHistoryExample();
        example.or().andUserIdEqualTo(uid);
        SotwoSearchHistory searchHistory = new SotwoSearchHistory();
        searchHistory.setDeleted(true);
        searchHistoryMapper.updateByExampleSelective(searchHistory, example);
    }

    public void deleteById(Integer id) {
        SotwoSearchHistory searchHistory = searchHistoryMapper.selectByPrimaryKey(id);
        if(searchHistory == null){
            return;
        }
        searchHistory.setDeleted(true);
        searchHistoryMapper.updateByPrimaryKey(searchHistory);
    }

    public void add(SotwoSearchHistory searchHistory) {
        searchHistoryMapper.insertSelective(searchHistory);
    }

    public List<SotwoSearchHistory> querySelective(String userId, String keyword, Integer page, Integer size, String sort, String order) {
        SotwoSearchHistoryExample example = new SotwoSearchHistoryExample();
        SotwoSearchHistoryExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(keyword)){
            criteria.andKeywordLike("%" + keyword + "%" );
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return searchHistoryMapper.selectByExample(example);
    }

    public int countSelective(String userId, String keyword, Integer page, Integer size, String sort, String order) {
        SotwoSearchHistoryExample example = new SotwoSearchHistoryExample();
        SotwoSearchHistoryExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(keyword)){
            criteria.andKeywordLike("%" + keyword + "%" );
        }
        criteria.andDeletedEqualTo(false);

        return (int)searchHistoryMapper.countByExample(example);
    }

    public void updateById(SotwoSearchHistory collect) {
        searchHistoryMapper.updateByPrimaryKeySelective(collect);
    }

    public SotwoSearchHistory findById(Integer id) {
        return searchHistoryMapper.selectByPrimaryKey(id);
    }
}
