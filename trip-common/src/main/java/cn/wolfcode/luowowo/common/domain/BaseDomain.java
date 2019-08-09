package cn.wolfcode.luowowo.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 公共服务
 */
@Setter
@Getter
public class BaseDomain implements Serializable {
    protected  Long id;
}
