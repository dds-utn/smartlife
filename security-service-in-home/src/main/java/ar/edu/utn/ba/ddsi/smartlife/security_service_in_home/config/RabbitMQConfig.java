package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange eventosExchange(@Value("${smartlife.events.exchange}") String name) {
        return new DirectExchange(name, true, false);
    }

    @Bean
    public Queue sensorDatosQueue(@Value("${smartlife.events.queues.sensor-datos}") String name) {
        return new Queue(name, true);
    }

    @Bean
    public Binding sensorDatosBinding(
        Queue sensorDatosQueue,
        DirectExchange eventosExchange,
        @Value("${smartlife.events.routing-keys.sensor-dato}") String routingKey
    ) {
        return BindingBuilder.bind(sensorDatosQueue).to(eventosExchange).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
