package com.github.yuque.api;

import com.github.yuque.api.model.BookDetailSerializer;
import com.github.yuque.api.model.DocSerializer;

import java.util.List;

/**
 * @program: yuque
 * @description: 语雀交互服务
 * @author: zhangchaozhen
 * @create: 2021-01-07 14:42
 **/
public interface YuqueService {

    /**
     * 知识库详情
     * @param type 仓库类型，Book - 文库，Design - 设计稿
     * @return
     */
    BookDetailSerializer reposDetail(String type);

    /**
     * 文档列表
     * @return
     */
    List<DocSerializer> docs();

    /**
     * 文档详情
     * @param slug
     * @return
     */
    DocSerializer docDetail(String slug);
}
