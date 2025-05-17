package cn.cotenite.model.request

import jakarta.validation.constraints.NotNull

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/18 00:15
 */
data class FollowRequest(
    @NotNull
    val followUserId: Long
)