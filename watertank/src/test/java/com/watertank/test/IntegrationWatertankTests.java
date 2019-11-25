package com.watertank.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.watertank.test.app.WatertankApplication;
import com.watertank.test.daoImp.WatertankFacade;

@SpringBootTest(classes = { WatertankApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
class IntegrationWatertankTests {

	@Autowired
	private WatertankFacade watertankFacade;
	
	@Value("${test.watertanks.container2}")
	public String initWatertanksContainers2;
	
	@Value("${test.watertanks.container1}")
	public String initWatertanksContainers1;

	@LocalServerPort
	int randomServerPort;


	@Test
	void testWatertankIntegrationA() {
		int counter = 0 ;
		while (30 >=counter) {
			for (String watertankId : initWatertanksContainers2.split(",")) {
				counter++ ;
				
				Random r = new Random();
				long randomeLiter =  r.nextInt((10 - 2 ) + 1) + 2;
				
				System.out.println("New test for Watertank # " + watertankId);
				System.out.println("Max Capacity: "+watertankFacade.getQueryMaxCapacity(watertankId,randomServerPort));
				
				double beforeAdded = watertankFacade.getQueryCurrentCapacity(watertankId,randomServerPort) ;
				System.out.println("Current Capacity Beffore adding: "+beforeAdded );
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				boolean wasAdded = watertankFacade.addWater(watertankId,randomeLiter,randomServerPort) ;
				System.out.println("Is More Water Was Added to the Tenk: " +wasAdded);
				
				double afterAdded = watertankFacade.getQueryCurrentCapacity(watertankId,randomServerPort) ;
				System.out.println("Current Capacity Affter Adding: "+afterAdded );
				
				System.out.println("Finished Tested Watertank #" + watertankId +"\n");
				assertTrue(wasAdded == beforeAdded < afterAdded);
			}
		}


	}
	
	
	
	  @Test 
	  void testWatertankIntegrationB() { 
		  	String watertankId = "7" ;
		  	

			
			System.out.println("Integration Test For Watertank # " + watertankId);
			System.out.println("Max Capacity: "+watertankFacade.getQueryMaxCapacity(watertankId,randomServerPort));
			double beforeAdded = watertankFacade.getQueryCurrentCapacity(watertankId,randomServerPort) ;
			System.out.println("Current Capacity Beffore adding: "+beforeAdded );
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			boolean wasAdded = watertankFacade.addWater(watertankId,7,randomServerPort) ;
			System.out.println("Is More Water Was Added to the Tenk: " +wasAdded);
			
			double afterAdded = watertankFacade.getQueryCurrentCapacity(watertankId,randomServerPort) ;
			System.out.println("Current Capacity Affter Adding: "+afterAdded );
			
			assertTrue(wasAdded == beforeAdded < afterAdded);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			 wasAdded = watertankFacade.addWater(watertankId,60,randomServerPort) ;
			System.out.println("Is More Water Was Added to the Tenk: " +wasAdded);
			
			 afterAdded = watertankFacade.getQueryCurrentCapacity(watertankId,randomServerPort) ;
			System.out.println("Current Capacity Affter Adding: "+afterAdded );
			
			assertTrue(wasAdded != beforeAdded < afterAdded);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			 wasAdded = watertankFacade.addWater(watertankId,6,randomServerPort) ;
			System.out.println("Is More Water Was Added to the Tenk: " +wasAdded);
			
			 afterAdded = watertankFacade.getQueryCurrentCapacity(watertankId,randomServerPort) ;
			System.out.println("Current Capacity Affter Adding: "+afterAdded );
			
			assertTrue(wasAdded == beforeAdded < afterAdded);
			System.out.println("Finished Tested Watertank #" + watertankId +"\n");
	  }

}
