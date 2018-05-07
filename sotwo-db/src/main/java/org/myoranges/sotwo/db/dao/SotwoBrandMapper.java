package org.myoranges.sotwo.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.myoranges.sotwo.db.domain.sotwoBrand;
import org.myoranges.sotwo.db.domain.sotwoBrandExample;

public interface SotwoBrandMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    long countByExample(sotwoBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int deleteByExample(sotwoBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int insert(sotwoBrand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int insertSelective(sotwoBrand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    List<sotwoBrand> selectByExample(sotwoBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<sotwoBrand> selectByExampleSelective(@Param("example") sotwoBrandExample example, @Param("selective") sotwoBrand.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    sotwoBrand selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    sotwoBrand selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") sotwoBrand.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int updateByExampleSelective(@Param("record") sotwoBrand record, @Param("example") sotwoBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int updateByExample(@Param("record") sotwoBrand record, @Param("example") sotwoBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int updateByPrimaryKeySelective(sotwoBrand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int updateByPrimaryKey(sotwoBrand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    sotwoBrand selectOneByExample(sotwoBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_brand
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    sotwoBrand selectOneByExampleSelective(@Param("example") sotwoBrandExample example, @Param("selective") sotwoBrand.Column ... selective);
}