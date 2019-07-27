import com.rabbitmq.client.ConnectionFactory

fun main() {
    val factory = ConnectionFactory()
    factory.host = "localhost"
    factory.username = "guest"
    factory.password = "guest"

    val connection = factory.newConnection()
    try {
        val channel = connection.createChannel()
        channel.queueDeclare(Send.QUEUE_NAME, false, false, false, null)
        val message = "Hello World"
        channel.basicPublish("", Send.QUEUE_NAME, null, message.toByteArray())
        println("[x] Sent '$message'")

    } finally {
        connection.close()
    }


}


class Send {
    companion object {
        val QUEUE_NAME = "hello"
    }
}