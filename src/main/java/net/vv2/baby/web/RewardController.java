package net.vv2.baby.web;


import com.xiaoleilu.hutool.date.DateUtil;
import net.vv2.baby.domain.Baby;
import net.vv2.baby.service.RewardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.thymeleaf.util.StringUtils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            startDate = DateUtil.beginOfMonth(DateUtil.date()).toString("yyyy-MM-dd");
        }

        if(StringUtils.isEmpty(endDate))
        {
            endDate = DateUtil.endOfMonth(DateUtil.date()).toString("yyyy-MM-dd");
        }

        Map<String,Object> param = new HashMap<>();
        param.put("startDate",startDate);
        param.put("endDate",endDate);

        List<HashMap<String,Object>> list = rewardService.selectRewardCount(param);
        model.addAttribute("list",list);
        model.addAttribute("inData",param);
        return "baby/rewardList";
    }



}
