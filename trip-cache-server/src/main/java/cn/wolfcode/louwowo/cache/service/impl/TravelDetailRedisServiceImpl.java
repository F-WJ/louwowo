package cn.wolfcode.louwowo.cache.service.impl;

import cn.wolfcode.louwowo.util.DateUtil;
import cn.wolfcode.luowowo.article.domain.Travel;
import cn.wolfcode.luowowo.article.service.ITravelService;
import cn.wolfcode.luowowo.cache.service.ITravelDetailRedisService;
import cn.wolfcode.luowowo.cache.util.RedisKeys;
import cn.wolfcode.luowowo.cache.vo.TravelStatisVO;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
public class TravelDetailRedisServiceImpl implements ITravelDetailRedisService {



    @Autowired
    private StringRedisTemplate redisTemplate;


    @Reference
    private ITravelService travelService;

    /**
     * 初始化操作（保存mysql部分需要缓存的数据）
     * @param vo
     */
    @Override
    public void initData(TravelStatisVO vo) {
        redisTemplate.opsForValue().set(RedisKeys.TRAVEL_DETAIL_VO.join(vo.getTravelId().toString()), JSON.toJSONString(vo));
    }


    /**
     * 在是初始化时候的判断redis已经存在数据
     * @param travelId
     * @return
     */
    @Override
    public boolean isExistKey(Long travelId) {
        Boolean hasKey = redisTemplate.hasKey(RedisKeys.TRAVEL_DETAIL_VO.join(travelId.toString()));
        return hasKey;
    }

    public TravelStatisVO detailBySql(TravelStatisVO vo, Long id){
        //获取mysql上的攻略明细的部分需要缓存的值
        Travel detail = travelService.get(id);
        vo.setTravelId(detail.getId()); //文章id
        vo.setViewnum(detail.getViewnum()); //文章点击数
        vo.setReplynum(detail.getReplynum()); //攻略文章评论数
        vo.setSharenum(detail.getSharenum());  //攻略文章分享数
        vo.setThumbsupnum(detail.getThumbsupnum()); //点赞个数（顶）
        vo.setFavornum(detail.getFavornum()); //文章收藏数
        return vo;
    }


    /**
     * 添加阅读数(默认加1)
     * @param id
     * @return
     */
    @Override
    public TravelStatisVO setViewNum(Long id) {
        //获取vo值
        boolean existKey = this.isExistKey(id);
        TravelStatisVO vo = new TravelStatisVO();
        if (existKey) {
            //在redis上取值，并保存
            String detailStr = redisTemplate.opsForValue().get(RedisKeys.TRAVEL_DETAIL_VO.join(id.toString()));
            vo = JSON.parseObject(detailStr, TravelStatisVO.class);
            vo.setViewnum(vo.getViewnum() + 1);
            //保存
            this.initData(vo);
            return vo;
        } else {
            //如果没有值在mysql上获取
            vo = this.detailBySql(vo, id);
            vo.setViewnum(vo.getViewnum() + 1);
            this.initData(vo);
            return vo;

        }
    }


    /**
     * 保存评论数
     * @param id
     * @return
     */
    @Override
    public TravelStatisVO saveReplynum(Long id) {
        //获取vo值
        boolean existKey = this.isExistKey(id);
        TravelStatisVO vo = new TravelStatisVO();
        if (existKey) {
            //在redis上取值，并保存
            String detailStr = redisTemplate.opsForValue().get(RedisKeys.TRAVEL_DETAIL_VO.join(id.toString()));
            vo = JSON.parseObject(detailStr, TravelStatisVO.class);
            vo.setReplynum(vo.getReplynum() + 1);
            //保存
            this.initData(vo);
            return vo;
        } else {
            //如果没有值在mysql上获取
            vo = this.detailBySql(vo, id);
            vo.setReplynum(vo.getReplynum() + 1);
            this.initData(vo);
            return vo;

        }
    }

    /**
     * 收藏数
     * @param
     * @return
     */
    @Override
    public AjaxResult saveFavornum(Long tid, Long uid) {
        //获取vo值
        AjaxResult ajaxResult = new AjaxResult();
        boolean existKey = this.isExistKey(tid);
        TravelStatisVO vo = new TravelStatisVO();
        if (!existKey) {
            //如果没有值在mysql上获取
            vo = this.detailBySql(vo, tid);
            // 收藏加一
            //使用key值判断是否已经收藏
            updateFavornumByKey(tid, uid, ajaxResult, vo);
            return ajaxResult;

        }else {
            //在redis上取值，并保存
            String detailStr = redisTemplate.opsForValue().get(RedisKeys.TRAVEL_DETAIL_VO.join(tid.toString()));
            vo = JSON.parseObject(detailStr, TravelStatisVO.class);
            //使用key值判断是否已经收藏
            updateFavornumByKey(tid, uid, ajaxResult, vo);
            return ajaxResult;
        }
    }


    /**
     * 判断用户是否已经给收藏了文章
     * @param
     * @return
     */
    @Override
    public boolean isFavorBy(Long tid, Long uid) {
        Boolean hasKey = redisTemplate.hasKey(RedisKeys.FAVORNUM_TRAVEL_USERINFO_ID.join(tid.toString(),uid.toString()));
        if(hasKey){
            return true;
        }
        return false;
    }

    /**
     * 顶（点赞）
     * @param sid
     * @param uid
     * @return
     */
    @Override
    public AjaxResult saveThumbsupnum(Long sid, Long uid) {
//        THUMBSUPNUM_USERINFO_ID
        //获取vo值
        AjaxResult ajaxResult = new AjaxResult();
        boolean existKey = this.isExistKey(sid);
        TravelStatisVO vo = new TravelStatisVO();
        if (!existKey) {
            //如果没有值在mysql上获取
            vo = this.detailBySql(vo, sid);
            // 收藏加一
            //使用key值判断是否已经顶
            updateThumbsupnum(sid, uid, ajaxResult, vo);
            return ajaxResult;

        }else {
            //在redis上取值，并保存
            String detailStr = redisTemplate.opsForValue().get(RedisKeys.TRAVEL_DETAIL_VO.join(sid.toString()));
            vo = JSON.parseObject(detailStr, TravelStatisVO.class);
            //使用key值判断是否已经顶
            updateThumbsupnum(sid, uid, ajaxResult, vo);
            return ajaxResult;


        }
    }

    private void updateFavornumByKey(Long tid, Long uid, AjaxResult ajaxResult, TravelStatisVO vo) {
        Boolean hasKey = redisTemplate.hasKey(RedisKeys.FAVORNUM_TRAVEL_USERINFO_ID.join(tid.toString(),uid.toString()));
        if(!hasKey){
            vo.setFavornum(vo.getFavornum() + 1);
            redisTemplate.opsForValue().set(RedisKeys.FAVORNUM_TRAVEL_USERINFO_ID.join(tid.toString(),uid.toString()), "1");

        }else{
            vo.setFavornum(vo.getFavornum() - 1);
            redisTemplate.delete(RedisKeys.FAVORNUM_TRAVEL_USERINFO_ID.join(tid.toString(),uid.toString()));
            ajaxResult.setSuccess(false);
        }
        this.initData(vo);
        ajaxResult.setData(vo);
    }

    private void updateThumbsupnum(Long tid, Long uid, AjaxResult ajaxResult, TravelStatisVO vo) {
        Boolean hasKey = redisTemplate.hasKey(RedisKeys.THUMBSUPNUM_TRAVEL_USERINFO_ID.join(tid.toString(),uid.toString()));
        if(!hasKey){
            vo.setThumbsupnum(vo.getThumbsupnum() + 1);
            Date nowDate = new Date();
            long dateBetween = DateUtil.getDateBetween(nowDate, DateUtil.getEndDate(nowDate));
            redisTemplate.opsForValue().set(RedisKeys.THUMBSUPNUM_TRAVEL_USERINFO_ID.join(tid.toString(),uid.toString()), "1", dateBetween, TimeUnit.SECONDS);

        }else{
            ajaxResult.setSuccess(false);
        }
        this.initData(vo);
        ajaxResult.setData(vo);
    }

}
