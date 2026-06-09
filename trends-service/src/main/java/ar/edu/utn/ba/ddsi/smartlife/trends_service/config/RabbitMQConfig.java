package ar.edu.utn.ba.ddsi.smartlife.trends_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
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

    @Value("${smartlife.events.routing-keys.venta-registrada}")
    private String ventaRegistradaRoutingKey;

    @Value("${smartlife.events.queues.venta-registrada}")
    private String ventaRegistradaQueueName;

    @Bean
    public DirectExchange eventosExchange() {
        return new DirectExchange(eventosExchangeName, true, false);
    }

    @Bean
    public Queue ventaRegistradaQueue() {
        return QueueBuilder.durable(ventaRegistradaQueueName).build();
    }

    @Bean
    public Binding ventaRegistradaBinding(Queue ventaRegistradaQueue, DirectExchange eventosExchange) {
        return BindingBuilder.bind(ventaRegistradaQueue).to(eventosExchange).with(ventaRegistradaRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
