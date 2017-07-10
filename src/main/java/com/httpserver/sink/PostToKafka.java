package com.httpserver.sink;

import java.time.Instant;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class PostToKafka {
	
	final org.apache.kafka.clients.producer.Producer<Long,String> _producer;
	
	public PostToKafka() {
		_producer = KafkaProducerSingleton._INSTANCE.getKafkaProducer();
	}

	public void write2Kafka( final String request ) {
		final long now = Instant.now().toEpochMilli();
		final ProducerRecord<Long, String> record =  new ProducerRecord<Long,String>(KafkaProducerSingleton._INSTANCE.getKafkaProps().getProperty("topic"),
													 null, now, now,  request);
		_producer.send(record);
	}
	
	public class KafkaProduerCallBack implements  Callback{
		
		@Override
		 public void onCompletion( RecordMetadata recordMetadata, Exception e) {
			System.out.println(recordMetadata.topic());
	            if (e != null) {
	                System.out.println("Error producing to topic " + recordMetadata.topic());
	                e.printStackTrace();
	            }
	            else {
	            	
	            	System.out.println("Success : "+recordMetadata.partition());
	            }
	        }
	}
}
