package cn.cotenite.user.query

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/18 01:30
 */
interface UserDetailDubboQuery {
    fun checkUserIdExist(id: Long): Boolean
}