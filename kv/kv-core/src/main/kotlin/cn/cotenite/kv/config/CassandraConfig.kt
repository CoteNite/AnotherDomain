package cn.cotenite.kv.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/29 19:21
 */
@Configuration
class CassandraConfig(
    @Value("\${spring.cassandra.keyspace-name}")
    val keyspaceName:String,
    @Value("\${spring.cassandra.contact-points}")
    val contactPoints:String,
    @Value("\${spring.cassandra.port}")
    val port:Int
):AbstractCassandraConfiguration(){
    public override fun getKeyspaceName(): String {
        return keyspaceName
    }

    public override fun getContactPoints(): String {
        return contactPoints
    }

    public override fun getPort(): Int {
        return port
    }


}