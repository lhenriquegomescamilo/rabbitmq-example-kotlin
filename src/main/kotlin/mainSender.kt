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

        for (i in 1..1000) {
            val message = "Hello World $i"
            channel.basicPublish("", Send.QUEUE_NAME, null, message.toByteArray())
            println(message)
            Thread.sleep(500)
        }
        println("finish")
    } finally {
        connection.close()
    }


}

class Send {
    companion object {
        val QUEUE_NAME = "hello"
    }
}