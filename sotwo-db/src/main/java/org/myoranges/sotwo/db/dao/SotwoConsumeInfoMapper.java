package org.myoranges.sotwo.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.myoranges.sotwo.db.domain.SotwoConsumeInfo;
import org.myoranges.sotwo.db.domain.SotwoConsumeInfoExample;

public interface SotwoConsumeInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated Sat May 12 10:41:29 CST 2018
     */
    long countByExample(SotwoConsumeInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated Sat May 12 10:41:29 CST 2018
     */
    int deleteByExample(SotwoConsumeInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated Sat May 12 10:41:29 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated Sat May 12 10:41:29 CST 2018
     */
    int insert(SotwoConsumeInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated Sat May 12 10:41:29 CST 2018
     */
    int insertSelective(SotwoConsumeInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated Sat May 12 10:41:29 CST 2018
     */
    List<SotwoConsumeInfo> selectByExample(SotwoConsumeInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<SotwoConsumeInfo> selectByExampleSelective(@Param("example") SotwoConsumeInfoExample example, @Param("selective") SotwoConsumeInfo.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated Sat May 12 10:41:29 CST 2018
     */
    SotwoConsumeInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    SotwoConsumeInfo selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SotwoConsumeInfo.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated Sat May 12 10:41:29 CST 2018
     */
    int updateByExampleSelective(@Param("record") SotwoConsumeInfo record, @Param("example") SotwoConsumeInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated Sat May 12 10:41:29 CST 2018
     */
    int updateByExample(@Param("record") SotwoConsumeInfo record, @Param("example") SotwoConsumeInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated Sat May 12 10:41:29 CST 2018
     */
    int updateByPrimaryKeySelective(SotwoConsumeInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated Sat May 12 10:41:29 CST 2018
     */
    int updateByPrimaryKey(SotwoConsumeInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    SotwoConsumeInfo selectOneByExample(SotwoConsumeInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sotwo_consume_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    SotwoConsumeInfo selectOneByExampleSelective(@Param("example") SotwoConsumeInfoExample example, @Param("selective") SotwoConsumeInfo.Column ... selective);
}