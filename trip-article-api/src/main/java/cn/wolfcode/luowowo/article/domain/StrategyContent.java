package cn.wolfcode.luowowo.article.domain;

import cn.wolfcode.luowowo.common.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 攻略内容
 */
@Setter
@Getter
public class StrategyContent extends BaseDomain {
    private String content;
}