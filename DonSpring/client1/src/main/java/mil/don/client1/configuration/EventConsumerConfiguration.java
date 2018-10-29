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
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mil.don.client1.Client1StatusListener;

@Configuration
public class EventConsumerConfiguration
{
    @Bean
    public Exchange eventExchange() {
        return new TopicExchange("rmq-exchange");
    }

    @Bean
    public Queue queue() {
        return new Queue("status-queue");
    }

    @Bean
    public Binding binding(Queue queue, Exchange eventExchange) {
        return BindingBuilder
            .bind(queue)
            .to(eventExchange)
            .with("status.*")
            .noargs(); // status.service and status.device
    }

    //@Bean
    //public Client1StatusListener statusReceiver() {
    //    return new Client1StatusListener();
    //}
}
