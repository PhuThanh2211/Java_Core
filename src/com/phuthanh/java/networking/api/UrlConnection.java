package com.phuthanh.java.networking.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class UrlConnection {

	public static void main(String[] args) {
		processVersion4();
	}
	
	private static void processVersion1() {
		try {
			URL url = new URL("http://example.org");
			
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String line = "";
			while (line != null) {
				line = inputStream.readLine();
				System.out.println(line);
			}
			
			inputStream.close();
			
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
	}
	
	private static void processVersion2() {
		try {
			URL url = new URL("http://example.org");
			URLConnection urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.connect();

			BufferedReader inputStream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String line = "";
			while (line != null) {
				line = inputStream.readLine();
				System.out.println(line);
			}
			
			inputStream.close();
			
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
	}
	
	private static void processVersion3() {
		try {
			URL url = new URL("http://example.org");
			URLConnection urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.connect();

			Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
				String key = entry.getKey();
				List<String> values = entry.getValue();
				
				System.out.println("----key = " + key);
				for (String value : values) {
					System.out.println("value = " + value);
				}
			}
			
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
	}
	
	private static void processVersion4() {
		try {
			URL url = new URL("https://api.flickr.com/services/feeds/photos_public.gne?tags=cats");
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("User-Agent", "Chrome");
			urlConnection.setReadTimeout(15000);
			
			int responseCode = urlConnection.getResponseCode();
			System.out.println("Response Code: " + responseCode);
			
			if (responseCode != 200) {
				System.out.println("Error reading web page");
				System.out.println(urlConnection.getResponseMessage());
				return;
			}
			
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String line;
			while ((line = inputStream.readLine()) != null) {
				System.out.println(line);
			}
			
			inputStream.close();
			
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
	}
}

















