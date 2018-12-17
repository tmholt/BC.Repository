
package mil.don.masio.masioservice.configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;



@Configuration
public class EventConsumerConfiguration
{
    // this is the status exchange that we want to bind queue 1 to
    @Bean
    public Exchange statusExchange() {
        return new FanoutExchange("status-events");
    }

    // this is the detections exchange that we want to bind queue 2 to
    @Bean
    public Exchange detectExchange() {
        return new FanoutExchange("detection-events");
    }


    // this is our personal queue of rabbitmq status messages
    @Bean
    public Queue statusMessagesQueue() {
        UUID extension = UUID.randomUUID();
        return new Queue(
            "status-queue-" + extension.toString(),
            false, true, true);
    }

    // this is our personal queue of rabbitmq device detection messages
    @Bean
    public Queue detectionMessagesQueue() {
        UUID extension = UUID.randomUUID();
        return new Queue(
            "detections-queue-" + extension.toString(),
            false, true, true);
    }


    @Bean
    public List<Binding> bindings() {

        Binding binding1 = BindingBuilder.bind(
            statusMessagesQueue())
                .to(statusExchange())
                .with("status.*")
                .noargs();

        Binding binding2 = BindingBuilder.bind(
            detectionMessagesQueue())
            .to(detectExchange())
            .with("device.*")
            .noargs();


        return Arrays.asList(binding1, binding2);
    }

}
