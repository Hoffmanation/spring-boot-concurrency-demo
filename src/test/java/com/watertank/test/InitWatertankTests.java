package com.watertank.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.watertank.app.WatertankApplication;
import com.watertank.dao.Imp.WatertankManager;
import com.watertank.model.Watertank;

/**
 * A Spring test jUnit  that will check and verify that the In-Memory {@link Watertank} DB initialized and cached successfully
 * @author Hoffman
 *
 */
@SpringBootTest(classes = { WatertankApplication.class } ,webEnvironment=WebEnvironment.RANDOM_PORT)
class InitWatertankTests {
	
	@Autowired
	private WatertankManager watertankManager ;
	
	@LocalServerPort
    int randomServerPort;


	@SuppressWarnings("rawtypes")
	@Test
	void getAllWatertanks() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			 final String baseUrl = "http://localhost:" + randomServerPort + "/getAllWatertanks";
			URI uri = new URI(baseUrl);

			ResponseEntity<Map> result = restTemplate.getForEntity(uri, Map.class);
			ArrayList AllWatertanks = (ArrayList)   result.getBody().get("entity");
			
			// Verify that Water-tanks Collection is not empty ;
			assertTrue(!AllWatertanks.isEmpty());
			assertEquals(AllWatertanks.size(), watertankManager.getAllWatertanks().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
