/*
 *  Changed "config" type to HashMap, which attains the Map properties while 
 *  aslo allowing for easy ".entrySet" access for iteration.
 *  
 *  Handle method does the following:
 *  	- retrieves information from config
 *  	- calls http client issuing a get request per url in config
 *  	- parses json response
 *  	- compiles and reformats responses into a single response
 *  	- returns reformatted response
 *  
 *  HTTP Client method can be found in ClientServer.java
 *  
 */
package io.bankbridge.handler;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.services.ClientServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

public class BanksRemoteCalls {

	private static HashMap<String, String> config;

	@SuppressWarnings("unchecked")
	public static void init() throws Exception {
		config = new ObjectMapper()
				.readValue(Thread.currentThread().getContextClassLoader().getResource("banks-v2.json"), HashMap.class);
	}

	public static String handle(Request request, Response response) {

		// FOR TESTING PURPOSES ONLY
		// System.out.println(config);
		// 
		// prints in format:
		// {Royal Bank of Boredom=http://localhost:1234/rbb,
		// Credit Sweets=http://localhost:1234/cs,
		// Banco de espiritu santo=http://localhost:1234/bes}

		ClientServer server = new ClientServer();
		List<Map> result = new ArrayList<>();

		if (config != null) {
			for (java.util.Map.Entry<String, String> entry : config.entrySet()) {
				String bankName = entry.getKey();
				String url = entry.getValue();
				try {
					String serverResponse = server.get(url); 
					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(serverResponse);

					Map map = new HashMap<>();
					String name;
					if (json.containsKey("name")) {
						name = (String) json.get("name");
					} else {
						name = bankName;
					}
					map.put("name", name);

					String bic;
					if (json.containsKey("bic")) {
						bic = (String) json.get("bic");
					} else {
						bic = "temporarily unavailble";
					}
					map.put("id", bic);

					/* uncomment for return object to include more information
				 String countryCode;
				 if (json.containsKey("countryCode")) {
					 countryCode = (String) json.get("countryCode");
				 } else {
					 countryCode = "temporarily unavailble";
				 }
				 map.put("countryCode", countryCode);

				 String auth;
				 if (json.containsKey("auth")) {
					 auth = (String) json.get("auth");
				 } else {
					 auth = "temporarily unavailble";
				 }
				 map.put("auth", auth);
					 */

					result.add(map);

				} catch (Exception e) {
					continue; // If a url call fails, continue to next url in config.
				}
			}
		}
		try {
			String resultAsString = new ObjectMapper().writeValueAsString(result);
			return resultAsString;
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error while processing request");
		}
	}

}
