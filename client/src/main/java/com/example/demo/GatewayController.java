package com.example.demo;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Objects;


@RestController
@RequestMapping(value="/gateway")
public class GatewayController {
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	Environment env;
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String getData() {
		System.out.println("Returning data from gateway");
		return "Hello from gateway";
	}
	@RequestMapping(value = "/server-data", method = RequestMethod.GET)
	public String getServerData() throws URISyntaxException {
		System.out.println("Returning data from serer through gateway");
		try {
			String msEndpoint = env.getProperty("endpoint.server");
			System.out.println("Endpoint name : [" + msEndpoint + "]");
			return restTemplate.getForObject(new URI(Objects.requireNonNull(msEndpoint)), String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}  return "Exception occurred";
	}

}
