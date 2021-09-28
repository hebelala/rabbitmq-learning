package com.github.hebelala.rabbitmq.learning.workqueues;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Worker {

	private final static String QUEUE_NAME = "task_queue";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		// prefetchCount = 1
		// This tells RabbitMQ not to give more than one message to a worker at a time.
		// Or, in other words, don't dispatch a new message to a worker until it has processed and acknowledged the previous one.
		// Instead, it will dispatch it to the next worker that is not still busy.
		channel.basicQos(1);

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received '" + message + "'");
			try {
				doWork(message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println(" [x] Done");
				// send ack
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}
		};
		// manual ack
		channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
		});
	}

	private static void doWork(String task) throws InterruptedException {
		for (char ch : task.toCharArray()) {
			if (ch == '.') {
				Thread.sleep(1000);
			}
		}
	}

}
