package com.lambdaschool.gdp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GdpApplication {
  public static final String EXCHANGE_NAME = "GDP";
  public static final String QUEUE_NAME = "Log";

  public static void main(String[] args) {
    SpringApplication.run(GdpApplication.class, args);
  }

  @Bean
  public TopicExchange appExchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  @Bean
  public Queue appQueue() {
    return new Queue(QUEUE_NAME);
  }

  @Bean
  public Binding declareBinding() {
    return BindingBuilder.bind(appQueue()).to(appExchange()).with(QUEUE_NAME);
  }

  @Bean
  public RabbitTemplate makeTemplate(final ConnectionFactory factory) {
    final RabbitTemplate template = new RabbitTemplate(factory);
    template.setMessageConverter(producerJacksonConverter());
    return template;
  }

  @Bean
  public Jackson2JsonMessageConverter producerJacksonConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
