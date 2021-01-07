package com.github.yuque;

import com.github.yuque.api.YuqueService;
import com.github.yuque.api.model.BookDetailSerializer;
import com.github.yuque.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YuqueApplication.class)
class YuqueApplicationTests {

    @Autowired
    private YuqueService yuqueService;

    @Test
    public void testYuqueApi() {
        BookDetailSerializer book = yuqueService.reposDetail("Book");
        log.info(JsonUtil.obj2String(book));
    }

}
