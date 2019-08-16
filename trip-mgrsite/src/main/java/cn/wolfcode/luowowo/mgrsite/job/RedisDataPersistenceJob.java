package cn.wolfcode.luowowo.mgrsite.job;

import cn.wolfcode.luowowo.article.service.IStrategyDetailService;
import cn.wolfcode.luowowo.article.service.vo.newStrategyStatisVO;
import cn.wolfcode.luowowo.cache.service.IStrategyDetailRedisService;
import cn.wolfcode.luowowo.cache.vo.StrategyStatisVO;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时任务: 定时通过redis中的数据
 *
 *
 */
@Component
public class RedisDataPersistenceJob {

    //Seconds Minutes Hours DayofMonth Month DayofWeek Year
    //秒        分     小时  月份中的日期  月份   星期中的日期 年份
    //Seconds Minutes Hours DayofMonth Month DayofWeek     spring 支持这种
    //  0       0      2      1         *       ?         *   表示在每月的1日的凌晨2点调整任务
    //  0       0   10,14,16  *         *       ?             每天上午10点，下午2点，4点


    @Reference
    private IStrategyDetailRedisService strategyDetailRedisService;

    @Reference
    private IStrategyDetailService strategyDetailService;


    //指定doWork方法运行规则
    //定时任务执行计划
    @Scheduled(cron = "0/10 * * * * ? ")
    public void doWork(){


        System.out.println("-------------------开始同步-----------------");

        //获取redis所有 vo数据
        List<StrategyStatisVO> vos = strategyDetailRedisService.getAllStatisVos();

        for (StrategyStatisVO vo : vos) {

            //更新所有数据
            if(vo.getStrategyId() != 0) {
                newStrategyStatisVO v = new newStrategyStatisVO();
                v.setFavornum(vo.getFavornum());
                v.setReplynum(vo.getReplynum());
                v.setSharenum(vo.getSharenum());
                v.setStrategyId(vo.getStrategyId());
                v.setThumbsupnum(vo.getThumbsupnum());
                v.setViewnum(vo.getViewnum());
                strategyDetailService.updateStatisData(v);
            }

        }

        System.out.println("-------------------同步结束-----------------");
    }
}
