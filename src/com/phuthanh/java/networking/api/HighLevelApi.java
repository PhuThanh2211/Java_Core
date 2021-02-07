package com.phuthanh.java.networking.api;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class HighLevelApi {

	public static void main(String[] args) {
		processURL();
	}
	
	private static void processURL() {
		try {
			URI baseURI = new URI("https://username:password@myserver.com:5000");
			URI uri1 = new URI("/catalogue/phones?os=android#samsung");
			URI uri2 = new URI("/catalogue/tvs?manufacturer=samsung");
			URI uri3 = new URI("/stores/locations?zip=12345");

			URI resolvedUri1 = baseURI.resolve(uri1);
			URI resolvedUri2 = baseURI.resolve(uri2);
			URI resolvedUri3 = baseURI.resolve(uri3);
			
			URL url1 = resolvedUri1.toURL();
			URL url2 = resolvedUri2.toURL();
			URL url3 = resolvedUri3.toURL();
			System.out.println("URL 1 = " + url1);
			System.out.println("URL 2 = " + url2);
			System.out.println("URL 3 = " + url3);
			
			URI relativizedURI1 = baseURI.relativize(resolvedUri1);
			URI relativizedURI2 = baseURI.relativize(resolvedUri2);
			URI relativizedURI3 = baseURI.relativize(resolvedUri3);
			System.out.println("Relative URI 1 = " + relativizedURI1);
			System.out.println("Relative URI 2 = " + relativizedURI2);
			System.out.println("Relative URI 3 = " + relativizedURI3);
			
		} catch (URISyntaxException e) {
			System.out.println("URI Bad Syntax: " + e.getMessage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private static void processURI() {
		try {
			URI uri = new URI("db://username:password@myserver.com:5000/catalogue/phones?os=android#samsung");
			
			System.out.println("Scheme = " + uri.getScheme());
			System.out.println("Scheme-specific part = " + uri.getSchemeSpecificPart());
			System.out.println("Authority = " + uri.getAuthority());
			System.out.println("User Info = " + uri.getUserInfo());
			System.out.println("Host = " + uri.getHost());
			System.out.println("Port = " + uri.getPort());
			System.out.println("Path = " + uri.getPath());
			System.out.println("Query = " + uri.getQuery());
			System.out.println("Fragment = " + uri.getFragment());

		} catch (URISyntaxException e) {
			System.out.println("URI bad syntax: " + e.getMessage());
		}
	}
}
