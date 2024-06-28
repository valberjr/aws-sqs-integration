package com.example.aws_sqs_integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;

@RestController
public class AmazonSqsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonSqsController.class);

    private final SqsTemplate sqsTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String uri;

    public AmazonSqsController(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @GetMapping("/send/{message}")
    public void sendMessageToQueue(@PathVariable String message) {
        sqsTemplate.send(uri, MessageBuilder.withPayload(message).build());
    }

    /*
     * When a message is received in this queue, the loadMessageFromSQS() method is
     * triggered to process the message, logging it using the Logger.
     */
    @SqsListener("virandoprogramador-queue")
    public void loadMessageFromSQS(String message) {
        LOGGER.info("SQS message {}", message);
    }

}
