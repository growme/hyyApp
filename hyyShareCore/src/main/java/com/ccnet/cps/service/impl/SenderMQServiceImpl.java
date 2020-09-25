package com.ccnet.cps.service.impl;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service("senderMQService")
@Lazy
public class SenderMQServiceImpl {

	@Autowired
	private JmsTemplate jmsQueueTemplate;

	public void sendMessage(Destination destination, final String message) {
		System.out.println("QueueSender发送消息：" + message);
		jmsQueueTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
}
