package com.httpserver.sink;

import java.net.InetAddress;

import org.apache.commons.cli.CommandLine;

import io.undertow.Undertow;

public class SinkServer {

	public static void main(final String[] args) throws Exception {
		
		CommandLine cmd = Utils.parseArgs(args);
        Utils._initKafkaProps(cmd.getOptionValue("k"));
        String p = cmd.getOptionValue("p");
        int port = 8080 ;
        if( null != p && !p.isEmpty())
        	port = Integer.parseInt(p);
        
        String hostName = InetAddress.getLocalHost().getHostName();
        System.out.println(hostName);
        Undertow server = Undertow.builder()
                .addHttpListener(port, hostName)
                .setHandler(new Handler()).build();
        server.start();
    }
}
