package ru.lexa.books_reviews.reviews.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMqConfig {
    @Value("${queue.name.createBookReview}")
    private String queue;

    @Value("${queue.name.deleteBooksReviews}")
    private String queue2;

    @Value("${queue.name.deleteFilmsReviews}")
    private String queue3;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routing-key.createBookReview}")
    private String routingKey;

    @Value("${spring.rabbitmq.routing-key.deleteBooksReview}")
    private String routingKey2;

    @Value("${spring.rabbitmq.routing-key.deleteFilmsReviews}")
    private String routingKey3;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Bean
    Queue queue() {
        return new Queue(queue, true);
    }

    @Bean
    Queue queue2() {
        return new Queue(queue2, true);
    }

    @Bean
    Queue queue3() {
        return new Queue(queue3, true);
    }

    @Bean
    Exchange myExchange() {
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(myExchange())
                .with(routingKey)
                .noargs();
    }

    @Bean
    Binding binding2() {
        return BindingBuilder
                .bind(queue2())
                .to(myExchange())
                .with(routingKey2)
                .noargs();
    }

    @Bean
    Binding binding3() {
        return BindingBuilder
                .bind(queue3())
                .to(myExchange())
                .with(routingKey3)
                .noargs();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}

