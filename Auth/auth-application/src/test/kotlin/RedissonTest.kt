import cn.cotenite.auth.AuthApplication
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/15 04:42
 */
@SpringBootTest(classes = [AuthApplication::class])
class RedissonTest(
    @Autowired
    val redissonClient: RedissonClient
){

    @Test
    fun setList(){
        val list = redissonClient.getList<String>("mylist")
        list.add("1ewdsa")
        val list1 = redissonClient.getList<String>("listTest")
        println(list1)
    }
}
