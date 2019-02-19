package net.vv2.baby.web;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.date.DateUtil;
import net.vv2.baby.domain.Baby;
import net.vv2.baby.service.RewardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.thymeleaf.util.StringUtils;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author J.Sky bosichong@qq.com
 * @create 2017-06-09 16:46
 **/
@Controller
@RequestMapping(value = "/reward")
public class RewardController {
    @Autowired
    private RewardService rewardService;


    /**
     * rewardList
     * @param model
     * @return
     */
    @RequestMapping("/rewardList")
    public String rewardList(@RequestParam(defaultValue = "") String startDate,
                             @RequestParam(defaultValue = "") String endDate,
                             Model model){

        if(StringUtils.isEmpty(startDate))
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(DateUtil.date());
            c.add(Calendar.YEAR, -1);
            Date y = c.getTime();
            startDate  = format.format(y);
            //startDate = DateUtil.beginOfMonth(DateUtil.date()).toString("yyyy-MM-dd");
        }

        if(StringUtils.isEmpty(endDate))
        {
            endDate = DateUtil.endOfMonth(DateUtil.date()).toString("yyyy-MM-dd");
        }

        Map<String,Object> param = new HashMap<>();
        param.put("startDate",startDate);
        param.put("endDate",endDate);

        List<HashMap<String,Object>> list = rewardService.selectRewardCount(param);
        if(list != null  && list.size() >0) {
            for (HashMap<String, Object> item : list) {
                Map<String, Object> paramType = new HashMap<>();
                paramType.put("startDate", startDate);
                paramType.put("endDate", endDate);
                paramType.put("babyId", item.get("id"));

                List<HashMap<String, Object>> typeList = rewardService.selectRewardCountByType(paramType);
                item.put("typeCountList", typeList);

                //组织char数据
                if (typeList != null && typeList.size() > 0) {
                    JSONArray jsonArray = new JSONArray();
                    for (HashMap<String, Object> map : typeList) {
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("name", map.get("type_name"));
                        jsonObject.put("y", map.get("type_count"));

                        jsonArray.add(jsonObject);
                    }
                    item.put("typeCountListJson", jsonArray.toJSONString());
                }
            }
        }

        model.addAttribute("list",list);
        model.addAttribute("inData",param);
        return "baby/rewardList";
    }



}
