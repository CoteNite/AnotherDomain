package cn.cotenite.user.command

import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.user.commons.constants.MQConstants
import cn.cotenite.user.dto.UserDetailCreateDTO
import cn.cotenite.user.model.domain.dto.UserDetailSaveInput
import cn.cotenite.user.repository.cache.UserDetailViewCacheRepository
import cn.cotenite.user.repository.database.UserDetailRepository
import org.apache.dubbo.config.annotation.DubboService
import org.apache.rocketmq.client.producer.SendCallback
import org.apache.rocketmq.client.producer.SendResult
import org.apache.rocketmq.spring.core.RocketMQTemplate
import org.springframework.messaging.support.MessageBuilder
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.bind.annotation.CrossOrigin

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/25 16:01
 */
@CrossOrigin("*")
@DubboService(version = "1.0")
class UserDetailDubboCommandImpl(
    private val userDetailRepository: UserDetailRepository,
    private val cacheRepository: UserDetailViewCacheRepository,
    private val rocketMQTemplate: RocketMQTemplate,
): UserDetailDubboCommand {

    override fun createUserDetail(userDetailCreateDTO: UserDetailCreateDTO) {
        val userDetailSaveInput = userDetailCreateDTO.run{ UserDetailSaveInput(id, userNum, email) }
        userDetailRepository.saveUserDetail4Create(userDetailSaveInput)

    }

    override fun deleteUserById(id: Long) {
        cacheRepository.deleteUserDetailCache(id)
        userDetailRepository.deleteUserDetailById(id)
        val message = MessageBuilder.withPayload(id).build()
        rocketMQTemplate.asyncSend(
            MQConstants.TOPIC_DELAY_DELETE_NOTE_REDIS_CACHE, message,
            object : SendCallback {

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