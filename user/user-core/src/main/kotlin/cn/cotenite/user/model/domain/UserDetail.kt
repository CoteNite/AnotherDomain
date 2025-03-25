package cn.cotenite.user.model.domain

import cn.cotenite.user.commons.enums.Delete
import org.babyfish.jimmer.sql.*
import java.time.LocalTime

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/19 07:15
 */
@Entity
interface UserDetail {

    @Id
    val id:Long

    val nickname:String

    val gender:String?

    val avatar:String?

    val mail:String

    val desc:String?

    val webUrl:String?

    val createTime:LocalTime

    val updateTime:LocalTime

    @Column(name = "`delete`")
    @Default("UNDELETE")
    @LogicalDeleted("DELETED")
    val delete: Delete

}