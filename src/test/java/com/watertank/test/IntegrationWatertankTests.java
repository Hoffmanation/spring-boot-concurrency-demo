package com.watertank.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.watertank.app.WatertankApplication;
import com.watertank.util.WatertankHttpClientUtils;

/**
 * A Spring test jUnit  that will check and verify that the watertank multi thread concurrency operations work
 * @author Hoffman
 *
 */
@SpringBootTest(classes = { WatertankApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
class IntegrationWatertankTests {
	

	@Autowired
	private WatertankHttpClientUtils watertankUtils;

	@Value("${test.watertanks.container2}")
	public String initWatertanksContainers2;

	@Value("${test.watertanks.container1}")
	public String initWatertanksContainers1;

	@LocalServerPort
	int randomServerPort;
	
	/**
	 * Blow are test that check the water tank concurrency functionality.
	 */
	@Test
	void testWatertankIntegrationA() {
		int counter = 0;
		while (30 >= counter) {
			for (String watertankId : initWatertanksContainers2.split(",")) {
				counter++;

				Random r = new Random();
				long randomeLiter = r.nextInt((10 - 2) + 1) + 2;

				System.out.println("New test for Watertank # " + watertankId);
				System.out.println("Max Capacity: " + watertankUtils.getMaxCapacityHttpClient(watertankId, randomServerPort));

				double beforeAdded = watertankUtils.getCurrentCapacityHttpClient(watertankId, randomServerPort);
				System.out.println("Current Capacity Beffore adding: " + beforeAdded);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				boolean wasAdded = watertankUtils.addWaterHttpClient(watertankId, randomeLiter, randomServerPort);
				System.out.println("Is More Water Was Added to the Tenk: " + wasAdded);

				double afterAdded = watertankUtils.getCurrentCapacityHttpClient(watertankId, randomServerPort);
				System.out.println("Current Capacity Affter Adding: " + afterAdded);

				System.out.println("Finished Tested Watertank #" + watertankId + "\n");
				assertTrue(wasAdded == beforeAdded < afterAdded);
			}
		}

	}

	@Test
	void testWatertankIntegrationB() {
		String watertankId = "7";
		System.out.println("Integration Test For Watertank # " + watertankId);
		System.out.println("Max Capacity: " + watertankUtils.getMaxCapacityHttpClient(watertankId, randomServerPort));
		double beforeAdded = watertankUtils.getCurrentCapacityHttpClient(watertankId, randomServerPort);
		System.out.println("Current Capacity Beffore adding: " + beforeAdded);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean wasAdded = watertankUtils.addWaterHttpClient(watertankId, 7, randomServerPort);
		System.out.println("Is More Water Was Added to the Tenk: " + wasAdded);

		double afterAdded = watertankUtils.getCurrentCapacityHttpClient(watertankId, randomServerPort);
		System.out.println("Current Capacity Affter Adding: " + afterAdded);

		assertTrue(wasAdded == beforeAdded < afterAdded);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		wasAdded = watertankUtils.addWaterHttpClient(watertankId, 60, randomServerPort);
		System.out.println("Is More Water Was Added to the Tenk: " + wasAdded);

		afterAdded = watertankUtils.getCurrentCapacityHttpClient(watertankId, randomServerPort);
		System.out.println("Current Capacity Affter Adding: " + afterAdded);

		assertTrue(wasAdded != beforeAdded < afterAdded);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		wasAdded = watertankUtils.addWaterHttpClient(watertankId, 6, randomServerPort);
		System.out.println("Is More Water Was Added to the Tenk: " + wasAdded);

		afterAdded = watertankUtils.getCurrentCapacityHttpClient(watertankId, randomServerPort);
		System.out.println("Current Capacity Affter Adding: " + afterAdded);

		assertTrue(wasAdded == beforeAdded < afterAdded);
		System.out.println("Finished Tested Watertank #" + watertankId + "\n");
	}

}
