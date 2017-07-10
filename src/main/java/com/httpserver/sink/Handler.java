package com.httpserver.sink;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class Handler implements HttpHandler {

	final public static String _SUCCESS = "SUCCESS";
	final public static String _FAILURE = "FAILURE";
	final PostToKafka post2Kafka = new PostToKafka();

	@Override
	public void handleRequest(final HttpServerExchange exchange) throws Exception {
			if (exchange.isInIoThread()) {
				exchange.dispatch(this);
				return;
			}
			exchange.getRequestReceiver().receiveFullString((exchangeReq, data) -> {
				post2Kafka.write2Kafka(data);
				exchangeReq.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                exchangeReq.getResponseSender().send(_SUCCESS);
			}, (exchangeReq, exception) -> {
				exchangeReq.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
				exchangeReq.getResponseSender().send(_FAILURE);
			});
	}
}
