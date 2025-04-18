package cn.cotenite.auth.controller

import cn.cotenite.asp.Slf4j
import cn.cotenite.auth.command.VerifyCommand
import cn.cotenite.auth.model.domain.dto.dto.ResetPasswordInput
import cn.cotenite.auth.model.dto.rep.RegisterRepDTO
import cn.cotenite.auth.model.dto.req.LoginReqDTO
import cn.cotenite.auth.service.AuthService
import cn.cotenite.filter.LoginUserContextHolder
import cn.cotenite.response.Response
import cn.dev33.satoken.stp.StpUtil
import jakarta.validation.constraints.Pattern
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/15 06:09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
class AuthController(
    private val verifyCommand: VerifyCommand,
    private val authService: AuthService
){

    @GetMapping("/loginCode")
    fun loginCode(): Response {
        val captchaReqDTO = verifyCommand.handleLoginCode()
        return Response.success(captchaReqDTO)
    }

    @GetMapping("/registerCode")
    fun registerCode(@RequestParam
                     @Pattern(regexp = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}", message = "邮箱格式不正确")
                     email: String): Response {
        authService.sendEmail4Register(email)
        return Response.success()
    }


    @GetMapping("/resetPasswordCode")
    fun resetPasswordCode(@RequestParam @Pattern(regexp = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}", message = "邮箱格式不正确")
                          email:String): Response {
        authService.sendEmail4RestPassword(email)
        return Response.success()
    }

    @PostMapping("/register")
    fun register(@RequestBody @Validated registerRepDTO: RegisterRepDTO): Response {
        val (email, password, verifyCode) = registerRepDTO
        authService.doRegister(email,password,verifyCode)
        return Response.success()
    }

    @PostMapping("/login")
    fun login(@RequestBody @Validated loginReqDTO: LoginReqDTO):Response{
        val (number, password, verifyKey,verifyCode) = loginReqDTO
        authService.doLogin(number, password, verifyKey, verifyCode)
        return Response.success()
    }

    @PostMapping("/restPassword")
    fun restPassword(@RequestBody @Validated restPasswordInput: ResetPasswordInput): Response {
        authService.resetPassword(restPasswordInput)
        return Response.success()
    }

    @GetMapping("/logout")
    fun logout(): Response {
        val userId = LoginUserContextHolder.getUserId()
        StpUtil.logout(userId)
        return Response.success()
    }


    @GetMapping("/getCancelCode")
    fun getCancelCode(): Response {
        val userId = LoginUserContextHolder.getUserId()
        authService.doGetCancelCode(userId)
        return Response.success()
    }


    @GetMapping("/cancel")
    fun cancel(@RequestParam verifyCode:String): Response {
        val userId = LoginUserContextHolder.getUserId()
        StpUtil.logout(userId)
        authService.doCancel(userId, verifyCode)
        return Response.success()
    }


}