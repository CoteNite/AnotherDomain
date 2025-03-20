import cn.cotenite.auth.AuthApplication
import cn.cotenite.auth.command.UserCommand
import cn.cotenite.auth.model.domain.agg.User
import cn.cotenite.auth.model.domain.dto.dto.UserInput
import cn.cotenite.auth.repo.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.test.context.junit.jupiter.SpringExtension

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
    private val userCommand: UserCommand
){

    @Test
    fun setList(){
        println(userRepository.test2getPermission())
    }
}
