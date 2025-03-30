package cn.cotenite.note.rpc

import cn.cotenite.api.command.NoteContentCommand
import org.apache.dubbo.config.annotation.DubboReference
import org.springframework.stereotype.Service

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 07:48
 */
@Service
class NoteContentRpc(
    @DubboReference(interfaceClass = NoteContentCommand::class,version = "1.0")
    private val noteContentCommand: NoteContentCommand
){
//    fun addNoteContent(content:String): String? {
//
//    }
}