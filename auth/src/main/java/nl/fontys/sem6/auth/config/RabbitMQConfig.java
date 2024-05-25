package nl.fontys.sem6.auth.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String AUTH_QUEUE = "authQueue";
    public static final String USER_REGISTRATION_QUEUE = "userRegistrationQueue";
    public static final String USER_DETAILS_QUEUE = "userDetailsQueue";

    @Bean
    public Queue authQueue() {
        return new Queue(AUTH_QUEUE, false);
    }

    @Bean
    public Queue userRegistrationQueue() {
        return new Queue(USER_REGISTRATION_QUEUE, false);
    }

    @Bean
    public Queue userDetailsQueue() {
        return new Queue(USER_DETAILS_QUEUE, false);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}


//package nl.fontys.sem6.auth.config;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//
//    public static final String AUTH_QUEUE = "authQueue";
//    public static final String USER_REGISTRATION_QUEUE = "userRegistrationQueue";
//    public static final String USER_DETAILS_QUEUE = "userDetailsQueue";
//
//    @Bean
//    public Queue authQueue() {
//        return new Queue(AUTH_QUEUE, false);
//    }
//
//    @Bean
//    public Queue userRegistrationQueue() {
//        return new Queue(USER_REGISTRATION_QUEUE, false);
//    }
//
//    @Bean
//    public Queue userDetailsQueue() {
//        return new Queue(USER_DETAILS_QUEUE, false);
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setReplyTimeout(6000); // Set appropriate timeout
//        return template;
//    }
////    public static final String USER_REGISTRATION_QUEUE = "userRegistrationQueue";
////
////    @Bean
////    public Queue userRegistrationQueue() {
////        return new Queue(USER_REGISTRATION_QUEUE, false);
////    }
////    public static final String AUTH_QUEUE = "authQueue";
////
////    @Bean
////    public Queue queue() {
////        return new Queue(AUTH_QUEUE, false);
////    }
//}
//
//
//
//
