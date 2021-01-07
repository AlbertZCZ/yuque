package com.github.yuque.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: yuque
 * @description: 用户信息
 * @author: zhangchaozhen
 * @create: 2021-01-07 17:10
 **/
@Data
public class UserSerializer {
    /**
     * 用户编号
     */
    private String id;
    /**
     * 类型 [`User`  - 用户, Group - 团队]
     */
    private String type;
    /**
     * 用户个人路径
     */
    private String login;
    /**
     * 昵称
     */
    private String name;
    /**
     * 头像 URL
     */
    @JsonProperty("avatar_url")
    private String avatarUrl;
    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private LocalDateTime createAt;
    /**
     * 更新时间
     */
    @JsonProperty("updated_at")
    private LocalDateTime updateAt;
}
