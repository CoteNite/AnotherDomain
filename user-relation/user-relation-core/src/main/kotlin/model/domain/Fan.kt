package cn.cotenite.model.domain

import cn.cotenite.note.common.enums.Delete
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Default
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.LogicalDeleted
import java.time.LocalTime

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/17 03:32
 */
@Entity
interface Fan {

    val id:Long

    val userId:Long

    val fansUserId:Long

    val createTime:LocalTime

    val updateTime:LocalTime

    @Column(name = "`delete`")
    @Default("UNDELETE")
    @LogicalDeleted("DELETED")
    val delete: Delete
}