package cn.cotenite.note.producer

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.note.common.constants.MQConstants
import org.apache.rocketmq.client.producer.SendCallback
import org.apache.rocketmq.client.producer.SendResult
import org.apache.rocketmq.spring.core.RocketMQTemplate
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/16 23:53
 */
@Slf4j
@Component
class CacheDeleteProducer(
    private val rocketMQTemplate: RocketMQTemplate
){

    fun deleteNoteCache(id:String){
        val message = MessageBuilder.withPayload(id.toString()).build()
        rocketMQTemplate.asyncSend(MQConstants.TOPIC_DELAY_DELETE_REDIS_CACHE, message, object : SendCallback {
            override fun onSuccess(result: SendResult?) {
                log.info("Redis缓存数据延迟双删成功")
            }

            override fun onException(e: Throwable?) {
                log.info("Redis缓存数据延迟双删失败",e)
            }

        },3000,1)
    }
}