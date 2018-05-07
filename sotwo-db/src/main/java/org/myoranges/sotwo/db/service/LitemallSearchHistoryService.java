package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoSearchHistoryMapper;
import org.myoranges.sotwo.db.domain.sotwoSearchHistory;
import org.myoranges.sotwo.db.domain.sotwoSearchHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoSearchHistoryService {
    @Resource
    private SotwoSearchHistoryMapper searchHistoryMapper;

    public void save(sotwoSearchHistory searchHistory) {
        searchHistoryMapper.insertSelective(searchHistory);
    }

    public List<sotwoSearchHistory> queryByUid(int uid) {
        sotwoSearchHistoryExample example = new sotwoSearchHistoryExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        example.setDistinct(true);
        return searchHistoryMapper.selectByExampleSelective(example, sotwoSearchHistory.Column.keyword);
    }

    public void deleteByUid(int uid) {
        sotwoSearchHistoryExample example = new sotwoSearchHistoryExample();
        example.or().andUserIdEqualTo(uid);
        sotwoSearchHistory searchHistory = new sotwoSearchHistory();
        searchHistory.setDeleted(true);
        searchHistoryMapper.updateByExampleSelective(searchHistory, example);
    }

    public void deleteById(Integer id) {
        sotwoSearchHistory searchHistory = searchHistoryMapper.selectByPrimaryKey(id);
        if(searchHistory == null){
            return;
        }
        searchHistory.setDeleted(true);
        searchHistoryMapper.updateByPrimaryKey(searchHistory);
    }

    public void add(sotwoSearchHistory searchHistory) {
        searchHistoryMapper.insertSelective(searchHistory);
    }

    public List<sotwoSearchHistory> querySelective(String userId, String keyword, Integer page, Integer size, String sort, String order) {
        sotwoSearchHistoryExample example = new sotwoSearchHistoryExample();
        sotwoSearchHistoryExample.Criteria criteria = example.createCriteria();

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
        sotwoSearchHistoryExample example = new sotwoSearchHistoryExample();
        sotwoSearchHistoryExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if(!StringUtils.isEmpty(keyword)){
            criteria.andKeywordLike("%" + keyword + "%" );
        }
        criteria.andDeletedEqualTo(false);

        return (int)searchHistoryMapper.countByExample(example);
    }

    public void updateById(sotwoSearchHistory collect) {
        searchHistoryMapper.updateByPrimaryKeySelective(collect);
    }

    public sotwoSearchHistory findById(Integer id) {
        return searchHistoryMapper.selectByPrimaryKey(id);
    }
}
