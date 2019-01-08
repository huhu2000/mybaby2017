package net.vv2.baby.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface RewardService {

    int addReward(Map<String,Object> reward);

    int selectAllRewardCount();

    List<HashMap<String,Object>> selectAllReward(String startDate, String endDate,Integer offset, Integer rows);

    List<HashMap<String,Object>>  selectRewardCount(Map<String,Object> param);

    List<HashMap<String,Object>> selectRewardCountByUser(Map<String,Object> param);

    List<HashMap<String,Object>> selectRewardTypeList(Map<String,Object> param);

    List<HashMap<String,Object>>  selectRewardByNum(Map<String,Object> param);

     int delReward(Integer id);

    int updateReward(Map<String,Object> param);
}
