package cn.cotenite.user.model.domain

import cn.cotenite.user.commons.enums.Delete
import cn.cotenite.user.commons.enums.Gender
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

    val gender:Gender?

    val avatar:String?

    val mail:String

    val description:String?

    val webUrl:String?

    val createTime:LocalTime

    val updateTime:LocalTime

    @Column(name = "`delete`")
    @Default("UNDELETE")
    @LogicalDeleted("DELETED")
    val delete: Delete

}