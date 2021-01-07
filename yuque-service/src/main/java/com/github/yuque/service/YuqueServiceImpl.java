package com.github.yuque.service;

import com.github.yuque.api.YuqueService;
import com.github.yuque.api.model.BookDetailSerializer;
import com.github.yuque.api.model.DetailRes;
import com.github.yuque.common.constant.YuqueConstant;
import com.github.yuque.service.base.BaseThirdService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @program: yuque
 * @description: 语雀
 * @author: zhangchaozhen
 * @create: 2021-01-07 17:19
 **/
@Service
public class YuqueServiceImpl extends BaseThirdService implements YuqueService  {

    @Override
    public BookDetailSerializer reposDetail(String type) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(YuqueConstant.DETAIL);
        builder.queryParam("type",type);
        ParameterizedTypeReference<DetailRes> responseBodyType = new ParameterizedTypeReference<DetailRes>() {
        };
        DetailRes exchange = this.exchange(builder.toUriString(), HttpMethod.GET, responseBodyType, null);

        return exchange.getData();
    }
}
