package net.vv2.baby.mapper;


import org.apache.ibatis.annotations.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Mapper
public interface RewardMapper {

    @Insert("insert into bb_reward (baby_id,type,reward_time,create_time,year,month)" +
            " values(#{baby_id},#{type},#{reward_time},now(), year(CURRENT_DATE),month(CURRENT_DATE))")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addReward(Map<String,Object> reward);




    @Select("<script>" +" select count(*)  from bb_reward a, bb_baby b , bb_reward_type c" +
            " where b.id = a.baby_id" +
            " and a.type = c.type_id " +
            " and a.write_off = '0' " +
            "<if test='startDate!=null and startDate!=\"\"'>" +
            " <![CDATA[ and str_to_date(a.reward_time,'%Y%m%d') >= str_to_date(#{startDate}, '%Y-%m-%d') ]]>" +
            "</if>"+
            "<if test='endDate!=null and endDate!=\"\"'>" +
            "  <![CDATA[ and str_to_date(a.reward_time,'%Y%m%d') <= str_to_date(#{endDate}, '%Y-%m-%d') ]]>" +
            "</if>"+
            " ORDER BY create_time DESC  " +
            "</script>")
    int selectAllRewardCount(@Param("startDate") String endDate, @Param("endDate") String startDate);

    @Select("<script>" +
            "select a.* , b.name baby_name, c.type_name,STR_TO_DATE(a.reward_time,'%Y%m%d')  reward_time1  " +
            " from bb_reward a, bb_baby b , bb_reward_type c" +
            " where b.id = a.baby_id" +
            " and a.type = c.type_id " +
            " and a.write_off = '0' " +
            "<if test='startDate!=null and startDate!=\"\"'>" +
            " <![CDATA[ and str_to_date(a.reward_time,'%Y%m%d') >= str_to_date(#{startDate}, '%Y-%m-%d') ]]>" +
            "</if>"+
            "<if test='endDate!=null and endDate!=\"\"'>" +
            "  <![CDATA[ and str_to_date(a.reward_time,'%Y%m%d') <= str_to_date(#{endDate}, '%Y-%m-%d') ]]>" +
            "</if>"+
            " ORDER BY create_time DESC  " +
            " LIMIT #{offset},#{rows}" +
            "</script>")
    List<HashMap<String, Object>> selectAllReward(@Param("startDate") String endDate, @Param("endDate") String startDate, @Param("offset") Integer offset,@Param("rows") Integer rows);


    @Select("<script>" +
            " select *  " +
            " from bb_reward  " +
            " where 1=1" +
            "<if test='baby_id!=null and baby_id!=\"\"'>" +
            " and baby_id = #{baby_id} " +
            "</if>"+
            "<if test='write_off!=null and write_off!=\"\"'>" +
            " and write_off = #{write_off} " +
            "</if>"+
            " ORDER BY create_time asc " +
            " LIMIT ${num}" +
            "</script>")
    List<HashMap<String, Object>> selectRewardByNum(Map<String,Object> param);



    @Select("select * from bb_reward where id = #{id}")
    HashMap<String, Object> selectRewardById(Integer id);

    @Update("<script>" +
            "update  bb_reward set " +
            "<if test='write_off!=null and write_off!=\"\"'>" +
            " write_off = #{write_off} " +
            "</if>"+
            " where id = #{id}" +
            "</script>")
    int updateReward(Map<String,Object> param);

    @Delete("delete from bb_reward where id = #{id}")
    int delReward(Integer id);



      /*  "select a.name, count(*) ct ," +
                " count(case type when '0' then a.id  end) ct0,\n" +
                " count(case type when '1' then a.id  end) ct1, " +
                " count(case type when '2' then a.id  end) ct2, " +
                " count(case type when '3' then a.id  end) ct3, " +
                " count(case type when '4' then a.id  end) ct4, " +
                " count(case type when '5' then a.id  end) ct5, " +
                " count(case type when '6' then a.id  end) ct6 " +
                " from bb_reward b, bb_baby a\n" +
                " where a.id = b.baby_id\n " +
                " and b.write_off = '0' " +*/
    /**
     * 返回reward统计数据数据
     *
     * @return
     */
    @Select("<script>" +
            "select a.id, a.name, count(*) ct " +
            " from bb_reward b, bb_baby a\n" +
            " where a.id = b.baby_id\n " +
            " and b.write_off = '0' " +
            "<if test='startDate!=null and startDate!=\"\"'>" +
            " <![CDATA[ and str_to_date(b.reward_time,'%Y%m%d') >= str_to_date(#{startDate}, '%Y-%m-%d') ]]>" +
            "</if>"+
            "<if test='endDate!=null and endDate!=\"\"'>" +
            "  <![CDATA[ and str_to_date(b.reward_time,'%Y%m%d') <= str_to_date(#{endDate}, '%Y-%m-%d') ]]>" +
            "</if>"+
            " group by a.id,a.name" +
            "</script>")
    List<HashMap<String, Object>> selectRewardCount(Map<String,Object> param);

    @Select("<script>" +
            "select b.type_id , b.type_name , count(*) type_count" +
            " from bb_reward a, bb_reward_type b\n" +
            " where a.type = b.type_id\n " +
            " and a.baby_id = b.baby_id " +
            " and a.baby_id = #{babyId} " +
            "<if test='startDate!=null and startDate!=\"\"'>" +
            " <![CDATA[ and str_to_date(a.reward_time,'%Y%m%d') >= str_to_date(#{startDate}, '%Y-%m-%d') ]]>" +
            "</if>"+
            "<if test='endDate!=null and endDate!=\"\"'>" +
            "  <![CDATA[ and str_to_date(a.reward_time,'%Y%m%d') <= str_to_date(#{endDate}, '%Y-%m-%d') ]]>" +
            "</if>" +
            " and write_off = '0' " +
            " group by b.type_id,b.type_name" +
            "</script>")
    List<HashMap<String, Object>> selectRewardCountByType(Map<String,Object> param);



    @Select("<script>" +
            " select  a.id, a.name, count(*) ct" +
            " from bb_reward b, bb_baby a\n" +
            " where a.id = b.baby_id\n " +
            " and b.write_off = '0' " +
            "  group by a.id ,a.name " +
            "</script>")
    List<HashMap<String, Object>> selectRewardCountByUser(Map<String,Object> param);



    @Select("<script>" +
            "select *  from bb_reward_type  t where " +
            " t.baby_id = #{baby_id} " +
            " and t.is_publish = '1' " +
            "<if test='reward_time!=null and reward_time!=\"\" and  baby_id!=null and baby_id!=\"\" '>" +
            "  <![CDATA[ and not EXISTS\n" +
            "    (select 1 from  bb_reward b\n" +
            "    where t.type_id = b.type\n" +
            "    and b.reward_time = #{reward_time} \n" +
            "    and b.baby_id = #{baby_id}\n" +
            ") ]]>" +
            "</if>"+
            "</script>")
    List<HashMap<String, Object>> selectRewardTypeList(Map<String,Object> param);


}

