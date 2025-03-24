package cn.cotenite.leaf.fallback;

import cn.cotenite.asp.Slf4j;
import cn.cotenite.utils.SnowFlakeUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author RichardYoung
 * @Description
 * @Date 2025/3/24 12:03
 */
@Component
public class FallbackService {

    private static final Logger log = LoggerFactory.getLogger(FallbackService.class);

    String getSnowflakeFallback(String key) {
        log.error("id服务发生错误，进行兜底方案！！！");
        return String.valueOf(SnowFlakeUtils.INSTANCE.generate());
    }


}
