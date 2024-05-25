//package nl.fontys.sem6.auth.message;
//
//import nl.fontys.sem6.auth.config.RabbitMQConfig;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MessageConsumer {
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    public void sendAuthMessage(String message) {
//        rabbitTemplate.convertAndSend(RabbitMQConfig.AUTH_QUEUE, message);
//    }
//}
//
//
//
//
