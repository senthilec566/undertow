package com.httpserver.sink;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

public enum KafkaProducerSingleton {
	_INSTANCE;
	private Producer<Long, String> producer = null;
	private Properties kafkaProps;
	
	public org.apache.kafka.clients.producer.Producer<Long,String> getKafkaProducer() {
		if( producer == null ){
			 producer = new KafkaProducer<>(kafkaProps);
			}
			return producer;
	}
	
	public void setKafkaProps(Properties kafkaProps) {
		this.kafkaProps = kafkaProps;
	}

	public Properties getKafkaProps() {
		return kafkaProps;
	}
}
