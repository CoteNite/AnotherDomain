package cn.cotenite.id.api;


/**
 * @Author RichardYoung
 * @Description
 * @Date 2025/3/21 01:38
 */
public interface IdGeneratorService {

    String getSegmentId(String key);

    public String getSnowflakeId(String key);

}
