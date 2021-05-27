package com.sandun.consumerservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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

}
