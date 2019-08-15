package cn.wolfcode.luowowo.cache.service;

import cn.wolfcode.luowowo.cache.vo.TravelStatisVO;
import cn.wolfcode.luowowo.common.util.AjaxResult;

public interface ITravelDetailRedisService {
    void initData(TravelStatisVO vo);

    boolean isExistKey(Long travelId);

    TravelStatisVO setViewNum(Long id);

    TravelStatisVO saveReplynum(Long detailId);

    AjaxResult saveFavornum(Long tid, Long uid);

    boolean isFavorBy(Long tid, Long uid);

    AjaxResult saveThumbsupnum(Long tid, Long id);
}
