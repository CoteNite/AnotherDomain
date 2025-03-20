package cn.cotenite.auth.policy


import cn.cotenite.auth.command.VerifyCommand
import cn.cotenite.expection.BusinessException
import cn.hutool.core.util.RandomUtil
import com.alibaba.nacos.api.config.annotation.NacosValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/17 04:35
 */
@Service
class EmailPolicy(
    @Value("\${spring.mail.username}")
    private val from:String,
    private val verifyCommand: VerifyCommand,
    private val taskExecutor: ThreadPoolTaskExecutor,
    private val mailSender: JavaMailSender
) {


    @Transactional(rollbackFor = [BusinessException::class])
    fun sendMail4Register(email:String) {
        try {
            val message = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message)
            val code=RandomUtil.randomString(6)
            helper.setFrom(from)
            helper.setTo(email)
            helper.setSubject("欢迎使用AnotherDomain")
            helper.setText( """
                您的邮箱正被用于申请AnotherDomain的账号<br>
                您的验证码为：<b>${code}<b/> <br>
                有效期三分钟，请妥善保管。<br>
                感谢您的信赖与使用！<br>
                AnotherDomain团队<br>
                若非本人操作，请忽略此邮件。
                """,true)
            taskExecutor.submit{mailSender.send(helper.mimeMessage)}
            verifyCommand.handleRegisterCode(email,code)
        }catch (e:Exception){
         throw BusinessException("邮件发送错误")
        }
    }

    @Transactional(rollbackFor = [BusinessException::class])
    fun sendMail4ResetPassword(email:String) {
        try {
            val message = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message)
            val code=RandomUtil.randomString(6)
            helper.setFrom(from)
            helper.setTo(email)
            helper.setSubject("欢迎使用AnotherDomain")
            helper.setText("""您的邮箱关联的账号正在尝试重置密码<br>
                您的验证码为：<b>${code}<b/> <br>
                有效期三分钟，请妥善保管。<br>
                若非本人操作，则说明您的账号可能泄漏，请立即修改密码或冻结账户。<br>
                感谢您的信赖与使用！<br>
                AnotherDomain团队<br>""",true)
            taskExecutor.submit{mailSender.send(helper.mimeMessage)}
            verifyCommand.handleRestPasswordCode(email,code)
        }catch (e:Exception){
            throw BusinessException("邮件发送错误")
        }
    }
}