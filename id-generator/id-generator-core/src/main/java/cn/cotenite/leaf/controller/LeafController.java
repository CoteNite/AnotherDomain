package cn.cotenite.leaf.controller;

import cn.cotenite.id.api.IdGeneratorService;
import cn.cotenite.leaf.common.Result;
import cn.cotenite.leaf.common.Status;
import cn.cotenite.leaf.exception.LeafServerException;
import cn.cotenite.leaf.exception.NoKeyException;
import cn.cotenite.leaf.fallback.FallbackService;
import cn.cotenite.leaf.service.SegmentService;
import cn.cotenite.leaf.service.SnowflakeService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 29543
 */
@RestController
@RequestMapping("/id")
@CrossOrigin("*")
@DubboService(version = "1.0")
public class LeafController implements IdGeneratorService {

    private Logger logger = LoggerFactory.getLogger(LeafController.class);

    @Resource
    private SegmentService segmentService;
    @Resource
    private SnowflakeService snowflakeService;

    @Override
    @RequestMapping(value = "/segment/get/{key}")
    @SentinelResource(blockHandlerClass = FallbackService.class, fallback = "getSnowflakeFallback")
    public String getSegmentId(@PathVariable("key") String key) {
        return get(key, segmentService.getId(key));
    }

    @Override
    @RequestMapping(value = "/snowflake/get/{key}")
    public String getSnowflakeId(@PathVariable("key") String key) {
        return get(key, snowflakeService.getId(key));
    }

    private String get(@PathVariable("key") String key, Result id) {
        Result result;
        if (key == null || key.isEmpty()) {
            throw new NoKeyException();
        }
        result = id;
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(result.toString());
        }
        return String.valueOf(result.getId());
    }
}
