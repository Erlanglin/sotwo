package org.myoranges.sotwo.db.service;

import com.github.pagehelper.PageHelper;
import org.myoranges.sotwo.db.dao.SotwoGoodsSpecificationMapper;
import org.myoranges.sotwo.db.domain.sotwoGoodsSpecification;
import org.myoranges.sotwo.db.domain.sotwoGoodsSpecificationExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class sotwoGoodsSpecificationService {
    @Resource
    private SotwoGoodsSpecificationMapper goodsSpecificationMapper;

    public List<sotwoGoodsSpecification> queryByGid(Integer id) {
        sotwoGoodsSpecificationExample example = new sotwoGoodsSpecificationExample();
        example.or().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        return goodsSpecificationMapper.selectByExample(example);
    }

    public sotwoGoodsSpecification findById(Integer id) {
        return goodsSpecificationMapper.selectByPrimaryKey(id);
    }

    public List<sotwoGoodsSpecification> querySelective(Integer goodsId, Integer page, Integer size, String sort, String order) {
        sotwoGoodsSpecificationExample example = new sotwoGoodsSpecificationExample();
        sotwoGoodsSpecificationExample.Criteria criteria = example.createCriteria();

        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return goodsSpecificationMapper.selectByExample(example);
    }

    public int countSelective(Integer goodsId, Integer page, Integer size, String sort, String order) {
        sotwoGoodsSpecificationExample example = new sotwoGoodsSpecificationExample();
        sotwoGoodsSpecificationExample.Criteria criteria = example.createCriteria();

        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        return (int)goodsSpecificationMapper.countByExample(example);
    }

    public void updateById(sotwoGoodsSpecification goodsSpecification) {
        goodsSpecificationMapper.updateByPrimaryKeySelective(goodsSpecification);
    }

    public void deleteById(Integer id) {
        sotwoGoodsSpecification goodsSpecification = goodsSpecificationMapper.selectByPrimaryKey(id);
        if(goodsSpecification == null){
            return;
        }
        goodsSpecification.setDeleted(true);
        goodsSpecificationMapper.updateByPrimaryKey(goodsSpecification);
    }

    public void add(sotwoGoodsSpecification goodsSpecification) {
        goodsSpecificationMapper.insertSelective(goodsSpecification);
    }

    public Integer[] queryIdsByGid(Integer goodsId) {
        List<sotwoGoodsSpecification> goodsSpecificationList = queryByGid(goodsId);
        Integer[] ids = new Integer[goodsSpecificationList.size()];
        for(int i = 0; i < ids.length; i++){
            ids[i] = goodsSpecificationList.get(i).getId();
        }
        return ids;
    }

    private class VO {
        private String name;
        private List<sotwoGoodsSpecification> valueList;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public List<sotwoGoodsSpecification> getValueList() {
            return valueList;
        }

        public void setValueList(List<sotwoGoodsSpecification> valueList) {
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
        List<sotwoGoodsSpecification> goodsSpecificationList = queryByGid(id);

        Map<String, VO> map = new HashMap<>();
        List<VO> specificationVoList = new ArrayList<>();

        for(sotwoGoodsSpecification goodsSpecification : goodsSpecificationList){
            String specification = goodsSpecification.getSpecification();
            VO goodsSpecificationVo = map.get(specification);
            if(goodsSpecificationVo == null){
                goodsSpecificationVo = new VO();
                goodsSpecificationVo.setName(specification);
                List<sotwoGoodsSpecification> valueList = new ArrayList<>();
                valueList.add(goodsSpecification);
                goodsSpecificationVo.setValueList(valueList);
                map.put(specification, goodsSpecificationVo);
                specificationVoList.add(goodsSpecificationVo);
            }
            else{
                List<sotwoGoodsSpecification> valueList = goodsSpecificationVo.getValueList();
                valueList.add(goodsSpecification);
            }
        }

        return specificationVoList;
    }

}
