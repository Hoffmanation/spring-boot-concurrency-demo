package com.watertank.util;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Utility class for the Water-Tank application
 * 
 * @author Hoffman
 *
 */
@Component
@SuppressWarnings("rawtypes")
public class WatertankHttpClientUtils {

	/**
	 * An HTTP client method for invoking /QueryCurrentCapacity endpoint
	 * 
	 * @param watertankId
	 * @param port
	 * @return
	 */
	public double getCurrentCapacityHttpClient(String watertankId, int port) {
		Double queryCurrentCapacity = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			final String baseUrl = "http://localhost:" + port + "/QueryCurrentCapacity";
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl).queryParam("id", watertankId);

			ResponseEntity<Map> result = restTemplate.getForEntity(builder.toUriString(), Map.class);
			queryCurrentCapacity = Double.valueOf((String) result.getBody().get("entity"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryCurrentCapacity;
	}

	/**
	 * An HTTP client method for invoking /AddWater endpoint
	 * 
	 * @param watertankId
	 * @param port
	 * @return
	 */
	public Boolean addWaterHttpClient(String watertankId, long liter, int port) {
		Boolean waterAdded = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			final String baseUrl = "http://localhost:" + port + "/AddWater";
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl).queryParam("id", watertankId).queryParam("liter", liter);

			ResponseEntity<Map> result = restTemplate.getForEntity(builder.toUriString(), Map.class);
			String stringBoolean = (String) result.getBody().get("entity");
			waterAdded = Boolean.parseBoolean(stringBoolean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return waterAdded;
	}

	/**
	 * An HTTP client method for invoking /QueryMaxCapacity endpoint
	 * 
	 * @param watertankId
	 * @param port
	 * @return
	 */
	public double getMaxCapacityHttpClient(String watertankId, int port) {
		Double queryMaxCapacity = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			final String baseUrl = "http://localhost:" + port + "/QueryMaxCapacity";
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl).queryParam("id", watertankId);

			ResponseEntity<Map> result = restTemplate.getForEntity(builder.toUriString(), Map.class);
			queryMaxCapacity = Double.valueOf((String) result.getBody().get("entity"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryMaxCapacity;
	}

}
