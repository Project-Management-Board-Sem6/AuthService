package nl.fontys.sem6.auth.message;

import nl.fontys.sem6.auth.config.RabbitMQConfig;
import nl.fontys.sem6.auth.config.UserRegistrationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendUserRegistrationMessage(UserRegistrationMessage message) {
        logger.info("Sending message: {}", message);
        rabbitTemplate.convertAndSend("userRegistrationQueue", message);
    }
}
//
//@Component
//public class MessageProducer {
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
////    public void sendAuthMessage(String message) {
////        rabbitTemplate.convertAndSend(RabbitMQConfig.AUTH_QUEUE, message);
//public void sendUserRegistrationMessage(UserRegistrationMessage message) {
//    rabbitTemplate.convertAndSend(RabbitMQConfig.USER_REGISTRATION_QUEUE, message);
//}
////    }
//}
