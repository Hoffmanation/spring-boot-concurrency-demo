package com.watertank.test.daoImp;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.watertank.test.dao.WatertankService;
import com.watertank.test.entity.WatertankWrapper.Watertank;

@Service
public class WatertankFacade {
	
	
	private static final Logger log = LoggerFactory.getLogger(WatertankFacade.class);

	
	@Autowired
	private WatertankService watertankServiceStub;
	
	public Map<Integer, Watertank> getAllWatertanks() {
		return watertankServiceStub.getAllWatertanks();
	};

	public double QueryMaxCapacity(Integer watertankId) {
		return watertankServiceStub.getAllWatertankById(watertankId).getMaxCapacity();
	}

	public double QueryCurrentCapacity(Integer watertankId) {
		return watertankServiceStub.getAllWatertankById(watertankId).getCurrentCapacity();
	}

	public boolean canAddVolumeToWatertank(double liter, Integer watertankId) {
		return QueryMaxCapacity(watertankId) >= (QueryCurrentCapacity(watertankId) + liter);
	}
	
	

	public boolean AddWater(double liter, Integer watertankId) {
		if (canAddVolumeToWatertank(liter, watertankId)) {
			Watertank currentWatertank = watertankServiceStub.getAllWatertankById(watertankId);
			currentWatertank.setCurrentCapacity(currentWatertank.getCurrentCapacity() + liter);
			return true ;
		}
		return false;

	}
	
	public void createALeak(double literOfLeak, long everySec ,Integer watertankId) {
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
		  @Override
		  public void run() {
			  Watertank watertank =watertankServiceStub.getAllWatertankById(watertankId) ;
			  if (watertank.getCurrentCapacity() > literOfLeak) {
				  watertank.setCurrentCapacity(watertank.getCurrentCapacity()-literOfLeak);
			}
			  else {
				  watertank.setCurrentCapacity(0);
			  }
			  
		  }
		}, everySec, everySec, TimeUnit.SECONDS);
		
	}
	
	
	public double getQueryMaxCapacity(String watertankId, int port) {
		Double queryMaxCapacity = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			final String baseUrl = "http://localhost:" + port + "/QueryMaxCapacity" ;

			
			UriComponentsBuilder builder = UriComponentsBuilder
				    .fromUriString(baseUrl)
				    // Add query parameter
				    .queryParam("id", watertankId);

			ResponseEntity<Map> result = restTemplate.getForEntity(builder.toUriString(), Map.class);
			queryMaxCapacity = Double.valueOf((String) result.getBody().get("entity"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return queryMaxCapacity;
	}

	public double getQueryCurrentCapacity(String watertankId, int port) {
		Double queryCurrentCapacity = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			final String baseUrl = "http://localhost:" + port + "/QueryCurrentCapacity";
			
			UriComponentsBuilder builder = UriComponentsBuilder
				    .fromUriString(baseUrl)
				    // Add query parameter
				    .queryParam("id", watertankId);

			ResponseEntity<Map> result = restTemplate.getForEntity(builder.toUriString(), Map.class);
			queryCurrentCapacity = Double.valueOf((String) result.getBody().get("entity"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return queryCurrentCapacity;
	}

	public Boolean addWater(String watertankId, long liter , int port) {
		Boolean waterAdded = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			final String baseUrl = "http://localhost:" + port + "/AddWater";
		
			UriComponentsBuilder builder = UriComponentsBuilder
				    .fromUriString(baseUrl)
				    // Add query parameter
				    .queryParam("id", watertankId)
			.queryParam("liter", liter);
			
			ResponseEntity<Map> result = restTemplate.getForEntity(builder.toUriString(), Map.class);
			String stringBoolean = (String) result.getBody().get("entity") ;
			waterAdded = Boolean.parseBoolean(stringBoolean);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return waterAdded;
	}

}
