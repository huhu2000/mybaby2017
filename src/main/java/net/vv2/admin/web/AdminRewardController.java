package net.vv2.admin.web;

import com.xiaoleilu.hutool.date.DateUtil;

import net.vv2.Utils.PageHelp;
import net.vv2.baby.domain.Baby;
import net.vv2.baby.service.impl.BabyServiceImpl;
import net.vv2.baby.service.impl.RewardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.util.*;


@Controller
@RequestMapping("/admin/reward")
public class AdminRewardController {
    @Autowired
    private RewardServiceImpl rewardService;

    @Autowired
    private BabyServiceImpl babyService;


    @ResponseBody
    @RequestMapping(value = "/changeRewardType", method = RequestMethod.POST)
    public List<HashMap<String, Object>> changeRewardType(@RequestBody Map param) {
        List<HashMap<String, Object>> result = new ArrayList<>();
                if(!StringUtils.isEmpty((String)param.get("babyId"))
                        && !StringUtils.isEmpty((String)param.get("rewardTime")) ) {


                    // 将获取的json数据封装一层，然后在给返回
                    Map<String, Object> tmp = new HashMap<>();
                    tmp.put("baby_id",param.get("babyId"));
                    tmp.put("reward_time",((String)param.get("rewardTime")).replaceAll("-",""));
                    result  = rewardService.selectRewardTypeList(tmp);

                }

        return result;
    }

    @RequestMapping("/rewardList")
    public String rewardList( @RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "6") int rows,
                              @RequestParam(defaultValue = "") String startDate,
                              @RequestParam(defaultValue = "") String endDate,
                              Model model){

        if(StringUtils.isEmpty(startDate))
        {
            startDate = DateUtil.format(DateUtil.date(),"yyyy-MM-dd");

        }

        if(StringUtils.isEmpty(endDate))
        {
            endDate =  DateUtil.format(DateUtil.date(),"yyyy-MM-dd");
        }

        PageHelp pageHelp = new PageHelp(rewardService.selectAllRewardCount(startDate,endDate),pageNum,rows);

        Map<String,Object> param = new HashMap<>();
        param.put("startDate",startDate);
        param.put("endDate",endDate);

        List<HashMap<String,Object>> list = rewardService.selectAllReward(startDate,endDate,(pageNum-1)*rows,rows);

        long totalPage = 1;
        if(pageHelp != null && pageHelp.getPageArray() != null && pageHelp.getPageArray().length >0)
        {
            totalPage = pageHelp.getPageArray()[0];
        }


        model.addAttribute("rewardList",list);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("inData",param);

        return "admin/reward/rewardList";

    }


    /**
     * 兑换限时界面
     * @param model
     * @return
     */
    @RequestMapping("/writeoffRewardView")
    public String writeoffRewardView(
            Model model){

        Map<String,Object> param = new HashMap<>();
        List<HashMap<String,Object>> list = rewardService.selectRewardCountByUser(param);
        model.addAttribute("rewardCountList",list);
        return "admin/reward/writeoffReward";

    }

    /**
     * 兑换操作
     * @param score
     * @param babyId
     * @param mv
     * @return
     */
    @RequestMapping("/writeoffReward")
    public ModelAndView writeoffReward(
            @RequestParam(defaultValue = "0") String score,
            @RequestParam(defaultValue = "") String babyId,
            ModelAndView mv){

        Map<String,Object> param = new HashMap<>();
        param.put("baby_id",babyId);
        param.put("write_off","0");
        param.put("num",score);
        List<HashMap<String,Object>> rewardList = rewardService.selectRewardByNum(param);
        if(rewardList !=null && rewardList.size() >0)
        {
            for (HashMap<String,Object> reward :rewardList  )
            {
                reward.put("write_off","1");
                rewardService.updateReward(reward);
            }
        }

        return returnMv(true,mv,"/admin/reward/writeoffRewardView");

    }


    /**
     * 特殊奖励
     * @param score
     * @param babyId
     * @param mv
     * @return
     */
    @RequestMapping("/specialReward")
    public ModelAndView specialReward(
            @RequestParam(defaultValue = "0") String score,
            @RequestParam(defaultValue = "") String babyId,
            ModelAndView mv){

      int scoreNum  = Integer.parseInt(score);
      String type = "";
      if("1".equals(babyId))
      {
          type = "12";
      }
      else if("2".equals(babyId))
      {
          type = "211";
      }

      if(scoreNum > 0)
      {
          for(int i =0 ; i<scoreNum; i++)
          {
              Map<String,Object> param = new HashMap<>();
              param.put("baby_id",babyId);
              param.put("type",type);
              param.put("reward_time",DateUtil.format(new Date(), "yyyyMMdd"));
              rewardService.addReward(param);
          }
      }

      return returnMv(true,mv,"/admin/reward/writeoffRewardView");

    }



    @RequestMapping("/addReward")
    public String addReward(Model model){

        List<Baby> babyList = babyService.selectAllBaby();
        model.addAttribute("babyList",babyList);

     /*   List<HashMap<String,Object>>  rewardTypeList = rewardService.selectRewardTypeList();
        model.addAttribute("rewardTypeList",rewardTypeList);*/

        return "admin/reward/addReward";
    }

    @ResponseBody
    @RequestMapping(value="/saveReward" , method=RequestMethod.POST)
    public HashMap<String,Object> saveReward( @RequestBody Map param){
        HashMap<String,Object> rewardParam = new HashMap<String,Object> ();
        rewardParam.put("baby_id",param.get("babyId"));
        rewardParam.put("type",param.get("typeId"));
        rewardParam.put("reward_time",DateUtil.format(DateUtil.parse((String)param.get("rewardTime")), "yyyyMMdd"));
        rewardService.addReward(rewardParam);


        HashMap<String,Object> result = new HashMap<String,Object>();
        result.put("resutCode","0000");
        result.put("resutInfo","新增奖励成功");

        return result;
        //return returnMv((rewardService.addReward(rewardParam)>0),mv,"/admin/reward/addReward");
    /*    List<Baby> babyList = babyService.selectAllBaby();
        model.addAttribute("babyList",babyList);

        model.addAttribute("reward",rewardParam);
        return "admin/reward/addReward";*/

    }


    @RequestMapping("/delReward/{id}")
    public ModelAndView delReward(@PathVariable Integer id,
                                ModelAndView mv){

        return returnMv((rewardService.delReward(id)>0),mv,"/admin/reward/rewardList");

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 选择页面跳转
     * @param bl
     * @param mv
     * @return
     */
    public ModelAndView returnMv(boolean bl, ModelAndView mv,String url) {
        if (bl) {
            return updateDate(mv, "操作成功！", "<meta http-equiv=\"refresh\" content=\"2;url=" + url + "\">", "success");
        } else {
            return updateDate(mv, "操作失败！", "<meta http-equiv=\"refresh\" content=\"2;url=" +  url + "\">", "err");
        }

    }

    /**
     * 页面跳转
     * @param mv
     * @param msg
     * @param url
     * @param viewName
     * @return
     */
    public ModelAndView updateDate(ModelAndView mv, String msg, String url, String viewName) {
        mv.addObject("msg", msg);
        mv.addObject("url", url);
        mv.setViewName(viewName);
        return mv;
    }
}
