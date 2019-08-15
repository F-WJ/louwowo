package cn.wolfcode.luowowo.mgrsite.listener;


import cn.wolfcode.luowowo.article.domain.StrategyDetail;
import cn.wolfcode.luowowo.article.service.IStrategyDetailService;
import cn.wolfcode.luowowo.cache.service.IStrategyDetailRedisService;
import cn.wolfcode.luowowo.cache.vo.StrategyStatisVO;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化redis数据（将mysql的部分数据缓存到redis里面）
 */
@Component
public class RedisDataInitListener implements ApplicationListener<ContextRefreshedEvent> {


    @Reference
    private IStrategyDetailService strategyDetailService;

    @Reference
    private IStrategyDetailRedisService strategyDetailRedisService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("---------初始化------------");
        //获取mysql上的攻略明细的部分需要缓存的值
        List<StrategyDetail> strategyDetails = strategyDetailService.list();
        StrategyStatisVO vo = new StrategyStatisVO();
        for (StrategyDetail detail : strategyDetails) {
            //判断redis是否已经存在攻略id值，通过key值
            if(strategyDetailRedisService.isExistKey(detail.getId())){
                continue;
            }
            vo.setStrategyId(detail.getId()); //文章id
            vo.setViewnum(detail.getViewnum()); //文章点击数
            vo.setReplynum(detail.getReplynum()); //攻略文章评论数
            vo.setSharenum(detail.getSharenum());  //攻略文章分享数
            vo.setThumbsupnum(detail.getThumbsupnum()); //点赞个数（顶）
            vo.setFavornum(detail.getFavornum()); //文章收藏数

            //保存到redis上
            strategyDetailRedisService.initData(vo);

        }



        System.out.println("---------初始化结束------------");
    }
}
