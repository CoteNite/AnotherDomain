import cn.cotenite.auth.AuthApplication
import cn.cotenite.auth.command.UserCommand
import cn.cotenite.auth.commons.utils.SnowflakeIdGenerator
import cn.cotenite.auth.model.domain.dto.dto.UserInput
import cn.cotenite.auth.repo.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/15 04:42
 */
@SpringBootTest(classes = [AuthApplication::class])
class RedissonTest(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val userCommand: UserCommand,
    @Autowired
    private val snowflakeIdGenerator: SnowflakeIdGenerator
){

    @Test
    fun setList(){

//        val userInput = UserInput("123ddfs123", 21312312312, "3234", "3213123")
//        println(userRepository.saveUser(userInput))
    }
}
