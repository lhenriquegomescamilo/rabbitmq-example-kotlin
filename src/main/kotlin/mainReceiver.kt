import com.rabbitmq.client.*
import com.rabbitmq.client.Consumer
import java.nio.charset.Charset

fun main() {
    val factory = ConnectionFactory()
    factory.host = "localhost"
    factory.username = "guest"
    factory.password = "guest"
    val connection = factory.newConnection()
    try {
        val channel = connection.createChannel()
        channel.basicConsume(Send.QUEUE_NAME, true, Consumer())
        Thread.sleep(120_000)

    } finally {
        connection.close()
    }


}

class Consumer : Consumer {
    override fun handleRecoverOk() {
        println("handleRecoverOk")
    }

    override fun handleConsumeOk(consumerTag: String?) {
        println("ConsumerTag $consumerTag")
    }

    override fun handleShutdownSignal(consumerTag: String?, sig: ShutdownSignalException?) {
        println("handleShutdownSignal $consumerTag")
    }

    override fun handleDelivery(
        consumerTag: String?,
        envelope: Envelope?,
        properties: AMQP.BasicProperties?,
        body: ByteArray?
    ) {
        val message = String(body!!, Charset.forName("UTF-8"))
        println("[x] Received $message")
    }

    override fun handleCancelOk(consumerTag: String?) {
        println("consumerTag")
    }

}