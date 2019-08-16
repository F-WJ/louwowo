package cn.wolfcode.louwowo.cache.service.impl;

import cn.wolfcode.louwowo.util.DateUtil;
import cn.wolfcode.luowowo.article.domain.StrategyDetail;
import cn.wolfcode.luowowo.article.service.IStrategyDetailService;
import cn.wolfcode.luowowo.cache.service.IStrategyDetailRedisService;
import cn.wolfcode.luowowo.cache.util.RedisKeys;
import cn.wolfcode.luowowo.cache.vo.StrategyStatisVO;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Service
public class StrategyDetailRedisServiceImpl implements IStrategyDetailRedisService {



    @Autowired
    private StringRedisTemplate redisTemplate;


    @Reference
    private IStrategyDetailService strategyDetailService;

    /**
     * 初始化操作（保存mysql部分需要缓存的数据）
     * @param vo
     */
    @Override
    public void initData(StrategyStatisVO vo) {
        redisTemplate.opsForValue().set(RedisKeys.STRATEGY_DETAIL_VO.join(vo.getStrategyId().toString()), JSON.toJSONString(vo));
    }


    /**
     * 在是初始化时候的判断redis已经存在数据
     * @param strategyId
     * @return
     */
    @Override
    public boolean isExistKey(Long strategyId) {
        Boolean hasKey = redisTemplate.hasKey(RedisKeys.STRATEGY_DETAIL_VO.join(strategyId.toString()));
        return hasKey;
    }

    public StrategyStatisVO detailBySql(StrategyStatisVO vo, Long id){
        //获取mysql上的攻略明细的部分需要缓存的值
        StrategyDetail detail = strategyDetailService.get(id);
        vo.setStrategyId(detail.getId()); //文章id
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
    public StrategyStatisVO setViewNum(Long id) {
        //获取vo值
        boolean existKey = this.isExistKey(id);
        StrategyStatisVO vo = new StrategyStatisVO();
        if (existKey) {
            //在redis上取值，并保存
            String detailStr = redisTemplate.opsForValue().get(RedisKeys.STRATEGY_DETAIL_VO.join(id.toString()));
            vo = JSON.parseObject(detailStr, StrategyStatisVO.class);
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
    public StrategyStatisVO saveReplynum(Long id) {
        //获取vo值
        boolean existKey = this.isExistKey(id);
        StrategyStatisVO vo = new StrategyStatisVO();
        if (existKey) {
            //在redis上取值，并保存
            String detailStr = redisTemplate.opsForValue().get(RedisKeys.STRATEGY_DETAIL_VO.join(id.toString()));
            vo = JSON.parseObject(detailStr, StrategyStatisVO.class);
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
    public AjaxResult saveFavornum(Long sid, Long uid) {
        //获取vo值
        AjaxResult ajaxResult = new AjaxResult();
        boolean existKey = this.isExistKey(sid);
        StrategyStatisVO vo = new StrategyStatisVO();
        if (!existKey) {
            //如果没有值在mysql上获取
            vo = this.detailBySql(vo, sid);
            // 收藏加一
            //使用key值判断是否已经收藏
            updateFavornumByKey(sid, uid, ajaxResult, vo);
            return ajaxResult;

        }else {
            //在redis上取值，并保存
            String detailStr = redisTemplate.opsForValue().get(RedisKeys.STRATEGY_DETAIL_VO.join(sid.toString()));
            vo = JSON.parseObject(detailStr, StrategyStatisVO.class);
            //使用key值判断是否已经收藏
            updateFavornumByKey(sid, uid, ajaxResult, vo);
            return ajaxResult;


        }
    }


    /**
     * 判断用户是否已经给收藏了文章
     * @param
     * @return
     */
    @Override
    public boolean isFavorBy(Long sid, Long uid) {
        Boolean hasKey = redisTemplate.hasKey(RedisKeys.FAVORNUM_USERINFO_ID.join(sid.toString(),uid.toString()));
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
        StrategyStatisVO vo = new StrategyStatisVO();
        if (!existKey) {
            //如果没有值在mysql上获取
            vo = this.detailBySql(vo, sid);
            // 收藏加一
            //使用key值判断是否已经顶
            updateThumbsupnum(sid, uid, ajaxResult, vo);
            return ajaxResult;

        }else {
            //在redis上取值，并保存
            String detailStr = redisTemplate.opsForValue().get(RedisKeys.STRATEGY_DETAIL_VO.join(sid.toString()));
            vo = JSON.parseObject(detailStr, StrategyStatisVO.class);
            //使用key值判断是否已经顶
            updateThumbsupnum(sid, uid, ajaxResult, vo);
            return ajaxResult;


        }
    }

    /**
     * 获取所有redis上的数据
     * @return
     */
    @Override
    public List<StrategyStatisVO> getAllStatisVos() {
        //根据指定key规则获取key集合
        Set<String> keys = redisTemplate.keys(RedisKeys.STRATEGY_DETAIL_VO.getPrefix() + ":*");
        List<StrategyStatisVO> list = new ArrayList<>();


        for (String key : keys) {
            String detailStr = redisTemplate.opsForValue().get(key);
            list.add(JSON.parseObject(detailStr, StrategyStatisVO.class));
        }

        return list;
    }

    /**
     * 海外攻略排行榜top10
     * @return
     */
    @Override
    public List<StrategyStatisVO> getAbroadCdsTop10() {
        //查询所有海外攻略信息
        Set<String> keys = redisTemplate.keys(RedisKeys.STRATEGY_DETAIL_VO.getPrefix() + ":*");
        List<StrategyStatisVO> list = new ArrayList<>();
        if(keys != null) {
            //获取海外的数据
            for (String key : keys) {
                String detailStr = redisTemplate.opsForValue().get(key);
                StrategyStatisVO strategyStatisVO = JSON.parseObject(detailStr, StrategyStatisVO.class);
                StrategyDetail strategyDetail = strategyDetailService.get(strategyStatisVO.getStrategyId());
                boolean isabroad = strategyDetail.isIsabroad();
                if(isabroad){
                    //保存到zset上(做排行榜)(点赞数+收藏数)
                    double score = strategyStatisVO.getThumbsupnum() + strategyStatisVO.getFavornum();
                    redisTemplate.opsForZSet().add(RedisKeys.ABROADCDS_TOP10_DETAIL_ID.getPrefix(), key, score);
                }
            }

            //获取zset上值
            Set<String> ranges = redisTemplate.opsForZSet().reverseRange(RedisKeys.ABROADCDS_TOP10_DETAIL_ID.getPrefix(), 0, 9);
            for (String range : ranges) {
                String valueStr = redisTemplate.opsForValue().get(range);
                StrategyStatisVO vo = JSON.parseObject(valueStr, StrategyStatisVO.class);
                StrategyDetail strategyDetail = strategyDetailService.get(vo.getStrategyId());

                vo.setDestId(strategyDetail.getDest().getId());
                vo.setDestName(strategyDetail.getDest().getName());
                vo.setTitle(strategyDetail.getTitle());
                list.add(vo);
            }

        }
        return list;
    }

    /**
     * 国内攻略排行榜top10
     * @return
     */
    @Override
    public List<StrategyStatisVO> getunAbroadCdsTop10() {
        //查询所有攻略信息
        Set<String> keys = redisTemplate.keys(RedisKeys.STRATEGY_DETAIL_VO.getPrefix() + ":*");
        List<StrategyStatisVO> list = new ArrayList<>();
        if(keys != null) {
            //获取海外的数据
            for (String key : keys) {
                String detailStr = redisTemplate.opsForValue().get(key);
                StrategyStatisVO strategyStatisVO = JSON.parseObject(detailStr, StrategyStatisVO.class);
                StrategyDetail strategyDetail = strategyDetailService.get(strategyStatisVO.getStrategyId());
                boolean isabroad = strategyDetail.isIsabroad();
                if(!isabroad){
                    //保存到zset上(做排行榜)(点赞数+收藏数)
                    double score = strategyStatisVO.getThumbsupnum() + strategyStatisVO.getFavornum();
                    redisTemplate.opsForZSet().add(RedisKeys.UNABROADCDS_TOP10_DETAIL_ID.getPrefix(), key, score);
                }
            }

            //获取zset上值（排序）
            Set<String> ranges = redisTemplate.opsForZSet().reverseRange(RedisKeys.UNABROADCDS_TOP10_DETAIL_ID.getPrefix(), 0, 9);
            for (String range : ranges) {
                String valueStr = redisTemplate.opsForValue().get(range);
                StrategyStatisVO vo = JSON.parseObject(valueStr, StrategyStatisVO.class);
                StrategyDetail strategyDetail = strategyDetailService.get(vo.getStrategyId());

                vo.setDestId(strategyDetail.getDest().getId());
                vo.setDestName(strategyDetail.getDest().getName());
                vo.setTitle(strategyDetail.getTitle());
                list.add(vo);
            }

        }
        return list;
    }


    /**
     * 热门攻略top10     HOTCDS_TOP10_DETAIL_ID
     * @return
     */
    @Override
    public List<StrategyStatisVO> gethotCdsTop10() {
        //查询所有攻略信息
        Set<String> keys = redisTemplate.keys(RedisKeys.STRATEGY_DETAIL_VO.getPrefix() + ":*");
        List<StrategyStatisVO> list = new ArrayList<>();
        if(keys != null) {
            //获取海外的数据
            for (String key : keys) {
                String detailStr = redisTemplate.opsForValue().get(key);
                StrategyStatisVO strategyStatisVO = JSON.parseObject(detailStr, StrategyStatisVO.class);
                StrategyDetail strategyDetail = strategyDetailService.get(strategyStatisVO.getStrategyId());
                //保存到zset上(做排行榜)(浏览数+评论数)
                double score = strategyStatisVO.getViewnum() + strategyStatisVO.getReplynum();
                redisTemplate.opsForZSet().add(RedisKeys.HOTCDS_TOP10_DETAIL_ID.getPrefix(), key, score);

            }

            //获取zset上值
            Set<String> ranges = redisTemplate.opsForZSet().reverseRange(RedisKeys.HOTCDS_TOP10_DETAIL_ID.getPrefix(), 0, 9);
            for (String range : ranges) {
                String valueStr = redisTemplate.opsForValue().get(range);
                StrategyStatisVO vo = JSON.parseObject(valueStr, StrategyStatisVO.class);
                StrategyDetail strategyDetail = strategyDetailService.get(vo.getStrategyId());

                vo.setDestId(strategyDetail.getDest().getId());
                vo.setDestName(strategyDetail.getDest().getName());
                vo.setTitle(strategyDetail.getTitle());
                list.add(vo);
            }

        }
        return list;
    }

    private void updateFavornumByKey(Long sid, Long uid, AjaxResult ajaxResult, StrategyStatisVO vo) {
        Boolean hasKey = redisTemplate.hasKey(RedisKeys.FAVORNUM_USERINFO_ID.join(sid.toString(),uid.toString()));
        if(!hasKey){
            vo.setFavornum(vo.getFavornum() + 1);
            redisTemplate.opsForValue().set(RedisKeys.FAVORNUM_USERINFO_ID.join(sid.toString(),uid.toString()), "1");

        }else{
            vo.setFavornum(vo.getFavornum() - 1);
            redisTemplate.delete(RedisKeys.FAVORNUM_USERINFO_ID.join(sid.toString(),uid.toString()));
            ajaxResult.setSuccess(false);
        }
        this.initData(vo);
        ajaxResult.setData(vo);
    }

    private void updateThumbsupnum(Long sid, Long uid, AjaxResult ajaxResult, StrategyStatisVO vo) {
        Boolean hasKey = redisTemplate.hasKey(RedisKeys.THUMBSUPNUM_USERINFO_ID.join(sid.toString(),uid.toString()));
        if(!hasKey){
            vo.setThumbsupnum(vo.getThumbsupnum() + 1);
            Date nowDate = new Date();
            long dateBetween = DateUtil.getDateBetween(nowDate, DateUtil.getEndDate(nowDate));
            redisTemplate.opsForValue().set(RedisKeys.THUMBSUPNUM_USERINFO_ID.join(sid.toString(),uid.toString()), "1", dateBetween, TimeUnit.SECONDS);

        }else{
            ajaxResult.setSuccess(false);
        }
        this.initData(vo);
        ajaxResult.setData(vo);
    }

}
