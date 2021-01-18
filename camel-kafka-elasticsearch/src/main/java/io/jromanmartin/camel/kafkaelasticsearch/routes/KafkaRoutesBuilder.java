package io.jromanmartin.camel.kafkaelasticsearch.routes;

import io.jromanmartin.camel.kafkaelasticsearch.beans.ElasticSearchProcessor;
import io.jromanmartin.camel.kafkaelasticsearch.model.ElasticSearchMessage;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class KafkaRoutesBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://context?period=60s")
                .id("time-context-route")
                .setBody()
                .constant("Camel Context is working")
                .log("[DSL] >>> ${body}");

        from("kafka:apps.samples.greetings?brokers=event-bus-reg1-kafka-bootstrap:9092")
                .id("kafka-consumer-route")
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .log("    on the partition ${headers[kafka.PARTITION]}")
                .log("    with the offset ${headers[kafka.OFFSET]}")
                .log("    with the timestamp ${headers[kafka.TIMESTAMP]}")
                .log("    with the key ${headers[kafka.KEY]}")
                .process("elasticSearchProcessor")
                .marshal().json(JsonLibrary.Jackson)
                .log("Message to send to ElasticSearch: ${body}")
                .to("elasticsearch-rest://elasticsearch-demo?operation=Index&indexName=metrics-index")
                .log("Message sent to ES");
    }

}
