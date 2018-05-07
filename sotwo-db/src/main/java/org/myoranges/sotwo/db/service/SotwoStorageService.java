package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoStorageMapper;
import org.myoranges.sotwo.db.domain.SotwoStorage;
import org.myoranges.sotwo.db.domain.SotwoStorageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SotwoStorageService {
    @Autowired
    private SotwoStorageMapper storageMapper;

    public void deleteByKey(String key) {
        SotwoStorageExample example = new SotwoStorageExample();
        example.or().andKeyEqualTo(key);
        SotwoStorage storage = new SotwoStorage();
        storage.setDeleted(true);
        storageMapper.updateByExampleSelective(storage, example);
    }

    public void add(SotwoStorage storageInfo) {
        storageMapper.insertSelective(storageInfo);
    }

    public SotwoStorage findByName(String filename) {
        SotwoStorageExample example = new SotwoStorageExample();
        example.or().andNameEqualTo(filename).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }

    public SotwoStorage findByKey(String key) {
        SotwoStorageExample example = new SotwoStorageExample();
        example.or().andKeyEqualTo(key).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }

    public void update(SotwoStorage storageInfo) {
        storageMapper.updateByPrimaryKeySelective(storageInfo);
    }


    public SotwoStorage findById(Integer id) {
        return storageMapper.selectByPrimaryKey(id);
    }

    public List<SotwoStorage> querySelective(String key, String name, Integer page, Integer limit, String sort, String order) {
        SotwoStorageExample example = new SotwoStorageExample();
        SotwoStorageExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(key)){
            criteria.andKeyEqualTo(key);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, limit);
        return storageMapper.selectByExample(example);
    }

    public int countSelective(String key, String name, Integer page, Integer size, String sort, String order) {
        SotwoStorageExample example = new SotwoStorageExample();
        SotwoStorageExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(key)){
            criteria.andKeyEqualTo(key);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)storageMapper.countByExample(example);
    }
}
