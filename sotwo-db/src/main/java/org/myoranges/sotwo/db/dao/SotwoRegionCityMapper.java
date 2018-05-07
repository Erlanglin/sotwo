package org.myoranges.sotwo.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.myoranges.sotwo.db.domain.sotwoRegionCity;
import org.myoranges.sotwo.db.domain.sotwoRegionCityExample;

public interface SotwoRegionCityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    long countByExample(sotwoRegionCityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int deleteByExample(sotwoRegionCityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int deleteByPrimaryKey(Integer code);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int insert(sotwoRegionCity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int insertSelective(sotwoRegionCity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    List<sotwoRegionCity> selectByExample(sotwoRegionCityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<sotwoRegionCity> selectByExampleSelective(@Param("example") sotwoRegionCityExample example, @Param("selective") sotwoRegionCity.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    sotwoRegionCity selectByPrimaryKey(Integer code);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    sotwoRegionCity selectByPrimaryKeySelective(@Param("code") Integer code, @Param("selective") sotwoRegionCity.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int updateByExampleSelective(@Param("record") sotwoRegionCity record, @Param("example") sotwoRegionCityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int updateByExample(@Param("record") sotwoRegionCity record, @Param("example") sotwoRegionCityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int updateByPrimaryKeySelective(sotwoRegionCity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int updateByPrimaryKey(sotwoRegionCity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    sotwoRegionCity selectOneByExample(sotwoRegionCityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_region_city
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    sotwoRegionCity selectOneByExampleSelective(@Param("example") sotwoRegionCityExample example, @Param("selective") sotwoRegionCity.Column ... selective);
}