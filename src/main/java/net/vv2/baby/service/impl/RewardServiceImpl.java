package net.vv2.baby.service.impl;


import net.vv2.baby.mapper.RewardMapper;

import net.vv2.baby.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author J.Sky bosichong@qq.com
 * @create 2017-06-08 13:08
 **/
@Service
public class RewardServiceImpl implements RewardService {


    @Autowired
    private RewardMapper rewardMapper;

    @Override
    public int addReward(Map<String,Object> reward) {
        return rewardMapper.addReward(reward);
    }

    @Override
    public int selectAllRewardCount() {
        return rewardMapper.selectAllRewardCount();
    }

    @Override
    public List<HashMap<String,Object>> selectRewardCountByUser(Map<String,Object> param) {
        return rewardMapper.selectRewardCountByUser(param);
    }


    @Override
    public List<HashMap<String,Object>> selectAllReward(String startDate, String endDate,Integer offset, Integer rows) {
        return rewardMapper.selectAllReward(startDate,endDate,offset, rows);
    }


    @Override
    public List<HashMap<String,Object>> selectRewardCount(Map<String,Object> param) {
        return rewardMapper.selectRewardCount(param);
    }


    @Override
    public List<HashMap<String,Object>> selectRewardByNum(Map<String,Object> param) {
        return rewardMapper.selectRewardByNum(param);
    }


    @Override
    public List<HashMap<String,Object>> selectRewardTypeList(Map<String,Object> param) {
        return rewardMapper.selectRewardTypeList(param);
    }

    @Override
    public int delReward(Integer id) {
        return rewardMapper.delReward(id);
    }

    @Override
    public int updateReward(Map<String,Object> param) {
        return rewardMapper.updateReward(param);
    }

}
