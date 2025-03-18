package cn.cotenite.auth.repo

import cn.cotenite.auth.commons.utils.RedisKeyCreator
import cn.cotenite.auth.model.domain.VerifyAgg
import cn.cotenite.expection.BusinessException
import cn.cotenite.utils.RandomKeyCreator
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.Duration

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/16 03:33
 */
@Repository
class VerifyRepository(
    @Autowired
    private val redissonClient: RedissonClient
){
    fun getVerifyAgg(key:String): VerifyAgg {
        return VerifyAgg(key,redissonClient.getBucket<String>(key).get()?:throw BusinessException("验证码不存在"))
    }

    fun saveLoginKey(code:String):String{
        val uuid = RandomKeyCreator.getUuidKey()
        val key=RedisKeyCreator.loginCodeKey(uuid)
        val bucket = redissonClient.getBucket<String>(key)
        bucket.set(code)
        bucket.expire(Duration.ofMinutes(3))
        return uuid
    }

    fun saveRegisterKey(key: String, code:String){
        val bucket = redissonClient.getBucket<String>(key)
        bucket.set(code)
        bucket.expire(Duration.ofMinutes(3))
    }

    fun checkExist(key:String):Boolean{
        return redissonClient.getBucket<String>(key).isExists
    }

}