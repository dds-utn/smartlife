package ar.edu.utn.ba.ddsi.smartlife.security_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${smartlife.events.exchange}")
    private String eventosExchangeName;

    @Value("${smartlife.events.routing-keys.amenaza-detectada}")
    private String amenazaDetectadaRoutingKey;

    @Value("${smartlife.events.queues.amenaza-detectada}")
    private String amenazaDetectadaQueueName;

    @Bean
    public DirectExchange eventosExchange() {
        return new DirectExchange(eventosExchangeName, true, false);
    }

    @Bean
    public Queue amenazaDetectadaQueue() {
        return QueueBuilder.durable(amenazaDetectadaQueueName).build();
    }

    @Bean
    public Binding amenazaDetectadaBinding(Queue amenazaDetectadaQueue, DirectExchange eventosExchange) {
        return BindingBuilder.bind(amenazaDetectadaQueue).to(eventosExchange).with(amenazaDetectadaRoutingKey);
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
