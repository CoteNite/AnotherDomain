package cn.cotenite.user.consumer

import cn.cotenite.asp.Slf4j
import cn.cotenite.expection.BusinessException
import cn.cotenite.user.commons.constants.MQConstants
import cn.cotenite.user.repository.cache.UserDetailViewCacheRepository
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener
import org.apache.rocketmq.spring.core.RocketMQListener
import org.springframework.stereotype.Component

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/17 00:41
 */
@Component
@Slf4j
@RocketMQMessageListener(
    consumerGroup = "another_domain_group_" + MQConstants.TOPIC_DELAY_DELETE_NOTE_REDIS_CACHE,
    topic = MQConstants.TOPIC_DELAY_DELETE_NOTE_REDIS_CACHE
)
class DelayDeleteNoteRedisCacheConsumer(
    private val cacheRepository: UserDetailViewCacheRepository
):RocketMQListener<String>{
    override fun onMessage(message: String?) {
        val id = message?.toLong() ?: throw BusinessException("messageId非Long，Redis延迟双删失败")
        cacheRepository.deleteUserDetailCache(id)
    }


}