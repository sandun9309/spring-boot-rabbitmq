package com.sandun.producerservice.controller;

import com.sandun.producerservice.utility.IConstants;
import com.sandun.producerservice.dto.Message;
import com.sandun.producerservice.utility.Utility;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("messages")
@RestController
public class MessageController
{
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostMapping("/send")
	public String sendMessage( @RequestBody Message message )
	{
		if ( Utility.isNull( message.getSubject() ) || Utility.isNull( message.getMessage() ) )
		{
			return "Subject an Message required";
		}
		try
		{
			rabbitTemplate.convertAndSend( IConstants.QUEUE_TEST_1, message );
			return "Message sent Success";
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			return "Failed to send Message";
		}
	}

	@PostMapping("/send-and-receive")
	public String sendAndReceiveMessage( @RequestBody Message message )
	{
		if ( Utility.isNull( message.getSubject() ) || Utility.isNull( message.getMessage() ) )
		{
			return "Subject an Message required";
		}
		try
		{
			String response = ( String ) rabbitTemplate.convertSendAndReceive( IConstants.EXCHANGE_TEST_2, IConstants.ROUTING_TEST_2, message );
			return response;
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			return "Failed to send Message";
		}
	}
}
