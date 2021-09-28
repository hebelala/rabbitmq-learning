package com.github.hebelala.rabbitmq.learning.workqueues;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

	private final static String QUEUE_NAME = "task_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			// durable
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			String message = String.join(" ", args);
			// mark our messages as persistent
			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
	}

}
