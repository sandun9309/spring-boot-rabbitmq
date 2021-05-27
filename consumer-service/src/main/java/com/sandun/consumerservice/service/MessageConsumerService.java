package com.sandun.consumerservice.service;

import com.sandun.consumerservice.dto.Message;
import com.sandun.consumerservice.utility.IConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService
{
	@RabbitListener(queues = IConstants.QUEUE_TEST_1)
	public void consumeMessageFromQueueTest1( Message message )
	{
		System.out.println( "Message received - " + message );
	}

	@RabbitListener(queues = IConstants.QUEUE_TEST_2)
	public String consumeMessageFromQueueTest2( Message message )
	{
		System.out.println( "Message received - " + message );
		return "Message Received";
	}
}
