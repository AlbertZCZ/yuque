package com.github.yuque.common.constant;

/**
 * @program: yuque
 * @description: 语雀api url
 * @author: zhangchaozhen
 * @create: 2021-01-07 16:57
 **/
public class YuqueConstant {
    private YuqueConstant(){}

    public static final String NAMESPACE = "/zhangchaozhen/ekgv3l";
    /**
     * 基本路径
     */
    public static final String BASEURL = "https://www.yuque.com/api/v2";

    /**
     * 获取知识库详情
     */
    public static final String DETAIL = BASEURL + "/repos" + NAMESPACE;

    /**
     * 获取一个仓库的文档列表
     */
    public static final String DOC_LIST = BASEURL + "/repos" + NAMESPACE + "/docs";

    /**
     * 获取单篇文档的详细信息+ :slug
     */
    public static final String DOC_DETAIL = DOC_LIST + "/";
}
