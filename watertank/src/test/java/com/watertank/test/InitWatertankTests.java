package com.watertank.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.watertank.test.app.WatertankApplication;
import com.watertank.test.daoImp.WatertankFacade;
import com.watertank.test.entity.WatertankWrapper.Watertank;

@SpringBootTest(classes = { WatertankApplication.class } ,webEnvironment=WebEnvironment.RANDOM_PORT)
class InitWatertankTests {
	
	@Autowired
	private WatertankFacade watertankFacade ;
	
	@LocalServerPort
    int randomServerPort;


	@Test
	void getAllWatertanks() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			 final String baseUrl = "http://localhost:" + randomServerPort + "/getAllWatertanks";
			URI uri = new URI(baseUrl);

			ResponseEntity<Map> result = restTemplate.getForEntity(uri, Map.class);
			ArrayList AllWatertanks = (ArrayList)   result.getBody().get("entity");
			
			// Verify Watertanks Collection is not empty ;
			Assert.notEmpty(AllWatertanks);
			assertEquals(AllWatertanks.size(), watertankFacade.getAllWatertanks().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
