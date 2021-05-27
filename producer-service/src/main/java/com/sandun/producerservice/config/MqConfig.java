package com.sandun.producerservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandun.producerservice.utility.IConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MqConfig
{
	@Autowired
	private Environment environment;

	@Bean
	public ConnectionFactory connectionFactory()
	{
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUsername( environment.getProperty( "rabbit.username" ) );
		connectionFactory.setPassword( environment.getProperty( "rabbit.password" ) );
		connectionFactory.setVirtualHost( "/" );
		connectionFactory.setHost( environment.getProperty( "rabbit.hostName" ) );
		connectionFactory.setPort( Integer.parseInt( environment.getProperty( "rabbit.portNumber" ) ) );
		return connectionFactory;
	}

	@Bean
	public MessageConverter messageConverter()
	{
		ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
		return new Jackson2JsonMessageConverter( objectMapper );
	}

	@Bean
	public AmqpTemplate amqpTemplate( ConnectionFactory connectionFactory )
	{
		final RabbitTemplate rabbitTemplate = new RabbitTemplate( connectionFactory );
		rabbitTemplate.setMessageConverter( messageConverter() );
		return rabbitTemplate;
	}

	//--------------------------------------------------------------------------------
	@Bean
	public Queue queueTest1()
	{
		return new Queue( IConstants.QUEUE_TEST_1 );
	}

	@Bean
	public Queue queueTest2()
	{
		return new Queue( IConstants.QUEUE_TEST_2 );
	}

	@Bean
	public TopicExchange topicExchangeTest2()
	{
		return new TopicExchange( IConstants.EXCHANGE_TEST_2 );
	}

	@Bean
	public Binding bindingTest2()
	{
		return BindingBuilder.bind( queueTest2() ).to( topicExchangeTest2() ).with( IConstants.ROUTING_TEST_2 );
	}

}
