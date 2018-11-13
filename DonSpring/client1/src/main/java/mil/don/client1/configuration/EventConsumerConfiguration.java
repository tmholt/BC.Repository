/*-------------------------------------------------------------------------------------------------
| *** UNCLASSIFIED ***
|--------------------------------------------------------------------------------------------------
| U.S. Army Research, Development, and Engineering Command
| Aviation and Missile Research, Development, and Engineering Center
| Software Engineering Directorate, Redstone Arsenal, AL
|--------------------------------------------------------------------------------------------------
| Export-Control Act Warning: Warning - This Document contains technical data whose export is
| restricted by the Arms Export Control Act (Title 22, U.S.C., Sec 2751, et seq) or the Export
| Administration Act of 1979, as amended, Title 50, U.S.C., App. 2401 et seq. Violations of these
| export laws are subject to severe criminal penalties. Disseminate in accordance with provisions
| of DoD Directive 5230.25.
|--------------------------------------------------------------------------------------------------
| Distribution Statement C:  Distribution authorized to U.S. Government Agencies and their
| contractors, Export Controlled, Critical Technology, 13 Feb 2017. Other request for this document
| shall be referred to U.S. Army Aviation and Missile Research Development and Engineering Center
| Software Engineering Directorate, Attn: RDMR-BAW, Redstone Arsenal, AL 35898.
--------------------------------------------------------------------------------------------------*/

package mil.don.client1.configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import mil.don.client1.Client1StatusListener;

@Configuration
public class EventConsumerConfiguration
{
    // this is the exchange that we want to bind our queue to
    @Bean
    public Exchange statusExchange() {
        return new FanoutExchange("status-events");
    }

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
