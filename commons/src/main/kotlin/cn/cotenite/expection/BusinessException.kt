package cn.cotenite.expection

import cn.cotenite.enums.Errors

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/15 03:07
 */
class BusinessException(override val message:String):Exception(){

    constructor(e:Errors):this(e.message)

}