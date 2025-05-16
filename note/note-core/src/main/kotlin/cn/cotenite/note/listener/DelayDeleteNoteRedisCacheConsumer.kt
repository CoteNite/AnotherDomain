package cn.cotenite.note.listener

import cn.cotenite.asp.Slf4j
import cn.cotenite.expection.BusinessException
import cn.cotenite.note.common.constants.MQConstants
import cn.cotenite.note.repository.cache.NoteAggCacheRepository
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener
import org.apache.rocketmq.spring.core.RocketMQListener
import org.springframework.stereotype.Component

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/16 23:55
 */
@Slf4j
@Component
@RocketMQMessageListener(
    consumerGroup =  "another_domain_group_" + MQConstants.TOPIC_DELAY_DELETE_REDIS_CACHE,
    topic = MQConstants.TOPIC_DELAY_DELETE_REDIS_CACHE
)
class DelayDeleteNoteRedisCacheConsumer(
    private val cacheRepository: NoteAggCacheRepository
):RocketMQListener<String>{
    override fun onMessage(message: String?) {
        val noteId=message?.toLong()?:throw BusinessException("noteId为空，无法完成Redis缓存延迟双删操作")
        cacheRepository.deleteCache(noteId)
    }

}