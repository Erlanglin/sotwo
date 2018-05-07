package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoIssueMapper;
import org.myoranges.sotwo.db.domain.SotwoIssue;
import org.myoranges.sotwo.db.domain.SotwoIssueExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SotwoIssueService {
    @Resource
    private SotwoIssueMapper issueMapper;

    public List<SotwoIssue> query() {
        SotwoIssueExample example = new SotwoIssueExample();
        example.or().andDeletedEqualTo(false);
        return issueMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        SotwoIssue issue = issueMapper.selectByPrimaryKey(id);
        if(issue == null){
            return;
        }
        issue.setDeleted(true);
        issueMapper.updateByPrimaryKey(issue);
    }

    public void add(SotwoIssue issue) {
        issueMapper.insertSelective(issue);
    }

    public List<SotwoIssue> querySelective(String question, Integer page, Integer size, String sort, String order) {
        SotwoIssueExample example = new SotwoIssueExample();
        SotwoIssueExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(question)){
            criteria.andQuestionLike("%" + question + "%" );
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return issueMapper.selectByExample(example);
    }

    public int countSelective(String question, Integer page, Integer size, String sort, String order) {
        SotwoIssueExample example = new SotwoIssueExample();
        SotwoIssueExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(question)){
            criteria.andQuestionLike("%" + question + "%" );
        }
        criteria.andDeletedEqualTo(false);

        return (int)issueMapper.countByExample(example);
    }

    public void updateById(SotwoIssue issue) {
        issueMapper.updateByPrimaryKeySelective(issue);
    }

    public SotwoIssue findById(Integer id) {
        return issueMapper.selectByPrimaryKey(id);
    }
}
