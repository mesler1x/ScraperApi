package ru.urfu.scraperapi.controller;

import com.amazon.sqs.javamessaging.AmazonSQSMessagingClientWrapper;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Тестовый контроллер для общения с YMQ")
public class YMQController {

    private static final String queueName = "sample-queue";
    private static final String accessKeyId = "YCAJENL1EyZ3QjkrUAf5HKDHG";
    private static final String secretAccessKeyId = "YCNLQUo5L9vtGvKvcxZqDxwAbFcT961honiP2IuX";

    @Operation(summary = "Тестовое отправление сообщение в очередь YMQ")
    @PostMapping("/send-message")
    public boolean sendMessage(@RequestParam("message") String message) throws JMSException {
        try (SqsClient session = SqsClient.builder()
                .region(Region.of("ru-central1"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                accessKeyId,
                                secretAccessKeyId
                        )
                ))
                .endpointOverride(URI.create("https://message-queue.api.cloud.yandex.net/b1gg2mgvrsoqais0clr6/dj600000005cbbvc0459/sample-queue"))
                .build()) {
            sendMessage(session, message);
        }
        log.info("Successfully sent message: [{}] to queue with name [{}]", message, queueName);
        return true;
    }

    private void sendMessage(SqsClient sqsClient, String message) throws JMSException {
        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                sqsClient
        );

        MessageProducer producer;
        try (SQSConnection connection = connectionFactory.createConnection()) {

            AmazonSQSMessagingClientWrapper client = connection.getWrappedAmazonSQSClient();

            if (!client.queueExists(queueName)) {
                client.createQueue(queueName);
            }

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            producer = session.createProducer(queue);
            connection.start();
            Message messageToSend = session.createTextMessage(message);
            producer.send(messageToSend);
        }
    }
}
