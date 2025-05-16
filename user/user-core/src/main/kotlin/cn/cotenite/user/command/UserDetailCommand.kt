package cn.cotenite.user.command

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.user.commons.constants.MQConstants
import cn.cotenite.user.model.domain.dto.UserDetailUpdateInput
import cn.cotenite.user.repository.cache.UserDetailViewCacheRepository
import cn.cotenite.user.repository.database.UserDetailRepository
import cn.cotenite.utils.UserIdContextHolder
import org.apache.rocketmq.client.producer.SendCallback
import org.apache.rocketmq.client.producer.SendResult
import org.apache.rocketmq.spring.core.RocketMQTemplate
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/28 01:47
 */
interface UserDetailCommand {
    fun updateUserDetail(input: UserDetailUpdateInput)
}

@Slf4j
@Service
class UserDetailCommandImpl(
    private val userDetailRepository: UserDetailRepository,
    private val cacheRepository: UserDetailViewCacheRepository,
    private val rocketMQTemplate: RocketMQTemplate
): UserDetailCommand {
    @Transactional(rollbackFor = [Exception::class])
    override fun updateUserDetail( input: UserDetailUpdateInput){
        cacheRepository.deleteUserDetailCache(input.id)
        UserIdContextHolder.verifyUser(input.id)
        userDetailRepository.updateUserDetail(input)

        val message = MessageBuilder.withPayload(input.id.toString()).build()
        rocketMQTemplate.asyncSend(MQConstants.TOPIC_DELAY_DELETE_NOTE_REDIS_CACHE, message,
            object : SendCallback{

                override fun onSuccess(e: SendResult?) {
                    log.info("## 延时删除 Redis 笔记缓存消息发送成功...")
                }

                override fun onException(e: Throwable?) {
                    log.error("## 延时删除 Redis 笔记缓存消息发送失败...", e)
                }
            },
            3000,
            1
        )

    }
}