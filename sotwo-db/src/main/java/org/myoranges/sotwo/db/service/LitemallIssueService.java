package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoIssueMapper;
import org.myoranges.sotwo.db.domain.sotwoIssue;
import org.myoranges.sotwo.db.domain.sotwoIssueExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sotwoIssueService {
    @Resource
    private SotwoIssueMapper issueMapper;

    public List<sotwoIssue> query() {
        sotwoIssueExample example = new sotwoIssueExample();
        example.or().andDeletedEqualTo(false);
        return issueMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        sotwoIssue issue = issueMapper.selectByPrimaryKey(id);
        if(issue == null){
            return;
        }
        issue.setDeleted(true);
        issueMapper.updateByPrimaryKey(issue);
    }

    public void add(sotwoIssue issue) {
        issueMapper.insertSelective(issue);
    }

    public List<sotwoIssue> querySelective(String question, Integer page, Integer size, String sort, String order) {
        sotwoIssueExample example = new sotwoIssueExample();
        sotwoIssueExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(question)){
            criteria.andQuestionLike("%" + question + "%" );
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return issueMapper.selectByExample(example);
    }

    public int countSelective(String question, Integer page, Integer size, String sort, String order) {
        sotwoIssueExample example = new sotwoIssueExample();
        sotwoIssueExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(question)){
            criteria.andQuestionLike("%" + question + "%" );
        }
        criteria.andDeletedEqualTo(false);

        return (int)issueMapper.countByExample(example);
    }

    public void updateById(sotwoIssue issue) {
        issueMapper.updateByPrimaryKeySelective(issue);
    }

    public sotwoIssue findById(Integer id) {
        return issueMapper.selectByPrimaryKey(id);
    }
}
