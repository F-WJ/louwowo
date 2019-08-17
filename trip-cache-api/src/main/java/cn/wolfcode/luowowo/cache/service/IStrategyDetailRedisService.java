package cn.wolfcode.luowowo.cache.service;


import cn.wolfcode.luowowo.cache.vo.StrategyStatisVO;
import cn.wolfcode.luowowo.cache.vo.TravelStatisVO;
import cn.wolfcode.luowowo.common.util.AjaxResult;

import java.util.List;

public interface IStrategyDetailRedisService {
    void initData(StrategyStatisVO vo);

    boolean isExistKey(Long strategyId);

    StrategyStatisVO setViewNum(Long id);

    StrategyStatisVO saveReplynum(Long detailId);

    AjaxResult saveFavornum(Long sid, Long uid);

    boolean isFavorBy(Long sid, Long uid);

    AjaxResult saveThumbsupnum(Long sid, Long id);

    List<StrategyStatisVO> getAllStatisVos();


    List<StrategyStatisVO> getAbroadCdsTop10();

    List<StrategyStatisVO> getunAbroadCdsTop10();


    List<StrategyStatisVO> gethotCdsTop10();

    List<TravelStatisVO> getAllTravelStatisVos();

}
