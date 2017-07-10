package com.httpserver.sink;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;


public class Utils {

	public static String inputData;
	public static final DateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void perpareInput(String path) throws Exception {
		if (path == null || path.isEmpty()) {
			throw new Exception(" verify input path ");
		}
		FileInputStream fis = null;
		BufferedReader br = null;
		try {
			File fin = new File(path);
			fis = new FileInputStream(fin);
			br = new BufferedReader(new InputStreamReader(fis));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			inputData = buffer.toString();
			buffer = null;
		} finally {
			br.close();
			fis.close();
		}

	}
	
	public static void _initKafkaProps(String propPath) throws Exception {

		if (null == propPath || propPath.isEmpty()) {
			throw new Exception(" Properties File Path is Null or Empty ");
		}
		Properties prop = new Properties();
		File file = new File(propPath);
		if (!file.exists())
			throw new FileNotFoundException(" File Not Found in Path :" + propPath);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
			prop.load(fileInput);
			System.out.println(" Successfull initialized Kafka Properties ...");
			KafkaProducerSingleton._INSTANCE.setKafkaProps(prop);
		} catch (Exception ex) {

		} finally {
			fileInput.close();
		}
	}

	public static CommandLine parseArgs(String[] args) {
		Options options = new Options();
		Option kafka = new Option("k", "kafka-prop", true, "kafka properties path");
		kafka.setRequired(true);
		options.addOption(kafka);
		
		Option input = new Option("p", "port", true, "server port ");
		input.setRequired(false);
		options.addOption(input);
		
		CommandLineParser parser = new GnuParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (Exception ex) {
			formatter.printHelp("SinkServer", options);
			System.exit(1);
		}
		return cmd;
	}
}
