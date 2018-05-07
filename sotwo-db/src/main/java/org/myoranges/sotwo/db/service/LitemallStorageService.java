package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoStorageMapper;
import org.myoranges.sotwo.db.domain.sotwoStorage;
import org.myoranges.sotwo.db.domain.sotwoStorageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class sotwoStorageService {
    @Autowired
    private SotwoStorageMapper storageMapper;

    public void deleteByKey(String key) {
        sotwoStorageExample example = new sotwoStorageExample();
        example.or().andKeyEqualTo(key);
        sotwoStorage storage = new sotwoStorage();
        storage.setDeleted(true);
        storageMapper.updateByExampleSelective(storage, example);
    }

    public void add(sotwoStorage storageInfo) {
        storageMapper.insertSelective(storageInfo);
    }

    public sotwoStorage findByName(String filename) {
        sotwoStorageExample example = new sotwoStorageExample();
        example.or().andNameEqualTo(filename).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }

    public sotwoStorage findByKey(String key) {
        sotwoStorageExample example = new sotwoStorageExample();
        example.or().andKeyEqualTo(key).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }

    public void update(sotwoStorage storageInfo) {
        storageMapper.updateByPrimaryKeySelective(storageInfo);
    }


    public sotwoStorage findById(Integer id) {
        return storageMapper.selectByPrimaryKey(id);
    }

    public List<sotwoStorage> querySelective(String key, String name, Integer page, Integer limit, String sort, String order) {
        sotwoStorageExample example = new sotwoStorageExample();
        sotwoStorageExample.Criteria criteria = example.createCriteria();

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
        sotwoStorageExample example = new sotwoStorageExample();
        sotwoStorageExample.Criteria criteria = example.createCriteria();

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
