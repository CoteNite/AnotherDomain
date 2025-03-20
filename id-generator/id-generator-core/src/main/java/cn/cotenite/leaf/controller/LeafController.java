package cn.cotenite.leaf.controller;

import cn.cotenite.leaf.common.Result;
import cn.cotenite.leaf.common.Status;
import cn.cotenite.leaf.exception.LeafServerException;
import cn.cotenite.leaf.exception.NoKeyException;
import cn.cotenite.leaf.service.SegmentService;
import cn.cotenite.leaf.service.SnowflakeService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 29543
 */
@RestController
@RequestMapping("/id")
public class LeafController {

    private Logger logger = LoggerFactory.getLogger(LeafController.class);

    @Resource
    private SegmentService segmentService;
    @Resource
    private SnowflakeService snowflakeService;

    @RequestMapping(value = "/segment/get/{key}")
    public String getSegmentId(@PathVariable("key") String key) {
        return get(key, segmentService.getId(key));
    }

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
