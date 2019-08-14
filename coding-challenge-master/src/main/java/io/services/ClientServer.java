/*
 * HTTP Client that uses Apache. 
 * apache was chosen do to familiarity as well as the following benefits:
 * 		- open source
 * 		- highly flexible
 * 		- stable server
 * 		- extra security 
 * 
 */
package io.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class ClientServer {

	/**
	 * 
	 * @param url - string including location of remote server
	 * @return result - string including information retrieved from remote server
	 * @throws Exception
	 */
	public String get(String url) throws Exception {
		
		HttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);
		HttpResponse response = client.execute(getRequest);
		
		// For Testing Purposes Only
		// System.out.println("Sending get request to " + url + " ...");
		
		BufferedReader read = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		StringBuffer result = new StringBuffer();
		String temp = "";
		while ((temp = read.readLine()) != null) {
			result.append(temp);
		}
		
		// For Testing Purposes Only
		// System.out.println("Remote result: " + result.toString());
		
		return result.toString();
	}
	
}
