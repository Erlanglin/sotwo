package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoGoodsSpecificationMapper;
import org.myoranges.sotwo.db.domain.SotwoGoodsSpecification;
import org.myoranges.sotwo.db.domain.SotwoGoodsSpecificationExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SotwoGoodsSpecificationService {
    @Resource
    private SotwoGoodsSpecificationMapper goodsSpecificationMapper;

    public List<SotwoGoodsSpecification> queryByGid(Integer id) {
        SotwoGoodsSpecificationExample example = new SotwoGoodsSpecificationExample();
        example.or().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        return goodsSpecificationMapper.selectByExample(example);
    }

    public SotwoGoodsSpecification findById(Integer id) {
        return goodsSpecificationMapper.selectByPrimaryKey(id);
    }

    public List<SotwoGoodsSpecification> querySelective(Integer goodsId, Integer page, Integer size, String sort, String order) {
        SotwoGoodsSpecificationExample example = new SotwoGoodsSpecificationExample();
        SotwoGoodsSpecificationExample.Criteria criteria = example.createCriteria();

        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return goodsSpecificationMapper.selectByExample(example);
    }

    public int countSelective(Integer goodsId, Integer page, Integer size, String sort, String order) {
        SotwoGoodsSpecificationExample example = new SotwoGoodsSpecificationExample();
        SotwoGoodsSpecificationExample.Criteria criteria = example.createCriteria();

        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        return (int)goodsSpecificationMapper.countByExample(example);
    }

    public void updateById(SotwoGoodsSpecification goodsSpecification) {
        goodsSpecificationMapper.updateByPrimaryKeySelective(goodsSpecification);
    }

    public void deleteById(Integer id) {
        SotwoGoodsSpecification goodsSpecification = goodsSpecificationMapper.selectByPrimaryKey(id);
        if(goodsSpecification == null){
            return;
        }
        goodsSpecification.setDeleted(true);
        goodsSpecificationMapper.updateByPrimaryKey(goodsSpecification);
    }

    public void add(SotwoGoodsSpecification goodsSpecification) {
        goodsSpecificationMapper.insertSelective(goodsSpecification);
    }

    public Integer[] queryIdsByGid(Integer goodsId) {
        List<SotwoGoodsSpecification> goodsSpecificationList = queryByGid(goodsId);
        Integer[] ids = new Integer[goodsSpecificationList.size()];
        for(int i = 0; i < ids.length; i++){
            ids[i] = goodsSpecificationList.get(i).getId();
        }
        return ids;
    }

    private class VO {
        private String name;
        private List<SotwoGoodsSpecification> valueList;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public List<SotwoGoodsSpecification> getValueList() {
            return valueList;
        }

        public void setValueList(List<SotwoGoodsSpecification> valueList) {
            this.valueList = valueList;
        }
    }

    /**
     * [
     *  {
     *      name: '',
     *      valueList: [ {}, {}]
     *  },
     *  {
     *      name: '',
     *      valueList: [ {}, {}]
     *  }
     *  ]
     *
     * @param id
     * @return
     */
    public Object getSpecificationVoList(Integer id) {
        List<SotwoGoodsSpecification> goodsSpecificationList = queryByGid(id);

        Map<String, VO> map = new HashMap<>();
        List<VO> specificationVoList = new ArrayList<>();

        for(SotwoGoodsSpecification goodsSpecification : goodsSpecificationList){
            String specification = goodsSpecification.getSpecification();
            VO goodsSpecificationVo = map.get(specification);
            if(goodsSpecificationVo == null){
                goodsSpecificationVo = new VO();
                goodsSpecificationVo.setName(specification);
                List<SotwoGoodsSpecification> valueList = new ArrayList<>();
                valueList.add(goodsSpecification);
                goodsSpecificationVo.setValueList(valueList);
                map.put(specification, goodsSpecificationVo);
                specificationVoList.add(goodsSpecificationVo);
            }
            else{
                List<SotwoGoodsSpecification> valueList = goodsSpecificationVo.getValueList();
                valueList.add(goodsSpecification);
            }
        }

        return specificationVoList;
    }

}
