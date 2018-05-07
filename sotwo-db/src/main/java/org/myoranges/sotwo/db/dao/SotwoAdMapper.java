package org.myoranges.sotwo.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.myoranges.sotwo.db.domain.SoTwoAd;
import org.myoranges.sotwo.db.domain.sotwoAdExample;

public interface SotwoAdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    long countByExample(sotwoAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int deleteByExample(sotwoAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int insert(sotwoAd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int insertSelective(sotwoAd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    List<sotwoAd> selectByExample(sotwoAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<sotwoAd> selectByExampleSelective(@Param("example") sotwoAdExample example, @Param("selective") sotwoAd.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    sotwoAd selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    sotwoAd selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") sotwoAd.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int updateByExampleSelective(@Param("record") sotwoAd record, @Param("example") sotwoAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int updateByExample(@Param("record") sotwoAd record, @Param("example") sotwoAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int updateByPrimaryKeySelective(sotwoAd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated Sat Apr 07 10:22:31 CST 2018
     */
    int updateByPrimaryKey(sotwoAd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    sotwoAd selectOneByExample(sotwoAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_ad
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    sotwoAd selectOneByExampleSelective(@Param("example") sotwoAdExample example, @Param("selective") sotwoAd.Column ... selective);
}