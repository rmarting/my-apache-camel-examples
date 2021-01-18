package io.jromanmartin.camel.kafkaelasticsearch.beans;

import io.jromanmartin.camel.kafkaelasticsearch.model.ElasticSearchMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("elasticSearchProcessor")
public class ElasticSearchProcessor implements Processor {

    final static private Logger LOGGER = LoggerFactory.getLogger(ElasticSearchProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getMessage();
        String body = message.getBody(String.class);

        LOGGER.info("Converting to ElasticSearchMessage");

        ElasticSearchMessage elasticSearchMessage = new ElasticSearchMessage();
        elasticSearchMessage.setMessage(body);
        elasticSearchMessage.setTopic(message.getHeader("kafka.TOPIC", String.class));
        elasticSearchMessage.setPartition(message.getHeader("kafka.PARTITION", String.class));
        elasticSearchMessage.setOffset(message.getHeader("kafka.OFFSET", Long.class));
        elasticSearchMessage.setTimestamp(message.getHeader("kafka.TIMESTAMP", Long.class));

        LOGGER.info("Converted to ElasticSearchMessage");

        exchange.getMessage().setBody(elasticSearchMessage, ElasticSearchMessage.class);
    }

}
