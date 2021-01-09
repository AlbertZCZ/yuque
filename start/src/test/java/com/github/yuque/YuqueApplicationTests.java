package com.github.yuque;

import com.github.yuque.api.YuqueService;
import com.github.yuque.api.model.BookDetailSerializer;
import com.github.yuque.api.model.DocSerializer;
import com.github.yuque.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YuqueApplication.class)
class YuqueApplicationTests {

    @Autowired
    private YuqueService yuqueService;

    @Test
    public void testBook() {
        BookDetailSerializer book = yuqueService.reposDetail("Book");
        log.info(JsonUtil.obj2String(book));
    }

    @Test
    public void testDocList() {
        List<DocSerializer> docs = yuqueService.docs();
        log.info(JsonUtil.obj2String(docs));
    }

    @Test
    public void testDocDetail() {
        DocSerializer docSerializer = yuqueService.docDetail("rwvsu0");
        log.info(JsonUtil.obj2String(docSerializer));
    }
}
