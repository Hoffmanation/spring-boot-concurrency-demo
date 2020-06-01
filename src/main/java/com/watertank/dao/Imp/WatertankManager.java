package com.watertank.dao.Imp;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watertank.dao.WatertankService;
import com.watertank.model.Watertank;

/**
 * A Manager class will handle the Business-Logic layer for the
 * {@link Watertank} entity
 * 
 * @author Hoffman
 *
 */
@Service
public class WatertankManager {
	private static final Logger log = LoggerFactory.getLogger(WatertankManager.class);

	/**
	 * Spring Dependency Injection
	 */
	@Autowired
	private WatertankService watertankServiceStub;

	/**
	 * Get All the Water-tanks
	 * 
	 * @return
	 */
	public Map<Integer, Watertank> getAllWatertanks() {
		return watertankServiceStub.getAllWatertanks();
	};

	/**
	 * Get the maximum capacity amount of a specific water tank container
	 * 
	 * @param watertankId
	 * @return {@link Double}
	 */
	public double getMaxCapacity(Integer watertankId) {
		return watertankServiceStub.getWatertankById(watertankId).getMaxCapacity();
	}

	/**
	 * Get the current capacity amount of a specific water tank container
	 * 
	 * @param watertankId
	 * @return {@link Double}
	 */
	public double getCurrentCapacity(Integer watertankId) {
		return watertankServiceStub.getWatertankById(watertankId).getCurrentCapacity();
	}

	/**
	 * Check if more water can be added to a specific water-tank
	 * 
	 * @param liter
	 * @param watertankId
	 * @return {@link Boolean}
	 */
	public boolean canAddVolumeToWatertank(double liter, Integer watertankId) {
		return getMaxCapacity(watertankId) >= (getCurrentCapacity(watertankId) + liter);
	}

	/**
	 * Add more water to a specific water-tank
	 * 
	 * @param liter
	 * @param watertankId
	 * @return {@link Boolean}
	 */
	public boolean addWater(double liter, Integer watertankId) {
		if (canAddVolumeToWatertank(liter, watertankId)) {
			Watertank currentWatertank = watertankServiceStub.getWatertankById(watertankId);
			double originalCapacity = currentWatertank.getCurrentCapacity();
			currentWatertank.setCurrentCapacity(originalCapacity + liter);
			return true;
		}
		return false;

	}

	/**
	 * Method will execute new {@link Runnable} in order to create a water leak on a
	 * specific water-tank
	 * 
	 * @param literOfLeak - the amount of liters to subtract from the water-tank
	 *                    (The leak)
	 * @param everySec    - the amount of time to wait between the {@link Runnable}
	 *                    execution
	 * @param watertankId - the water-tank id
	 */
	public void createALeak(double literOfLeak, long everySec, Integer watertankId) {
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				log.info("New leak of {} liters was created for watertank# {}", literOfLeak, watertankId);
				Watertank watertank = watertankServiceStub.getWatertankById(watertankId);
				// In case the amount of lean to subtract is not bigger the current amount of
				// water left in the water - tank
				if (watertank.getCurrentCapacity() > literOfLeak) {
					watertank.setCurrentCapacity(watertank.getCurrentCapacity() - literOfLeak);
				} else {
					// In case the amount of lean to subtract is bigger - just empty the whole water
					// - tank
					watertank.setCurrentCapacity(0);
				}

			}
		}, everySec, everySec, TimeUnit.SECONDS);

	}

	/**
	 * Method will execute new {@link Runnable} in order to add water to every
	 * water-tank that his water capacity is equals to 0 (An empty water-tank)
	 */
	public void renewWaterTank() {
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				//Retrieve the water-tank DB cache and iterate over it 
				Map<Integer, Watertank> watertanks = watertankServiceStub.getAllWatertanks();
				for (Watertank watertank : watertanks.values()) {
					//Configure a min and max values to adding new water liters to a specific tank
					int min = (int) (watertank.getMaxCapacity() / 2);
					int max = (int) (watertank.getMaxCapacity() - 5);
					long randomeLiter = (long) (Math.random() * (max - min + 1) + min);
					//Calculate the percentage of the current water-tank liter capacity
					double per = watertank.getMaxCapacity() * watertank.getCurrentCapacity() / 100;
					//In case the tank is empty or the percentage of the current water-tank is below 5 - add random amount of liters to the tank
					if (watertank.getCurrentCapacity() == 0 || per < 5) {
						log.info("A renewal for watertank# {}", watertank.getId());
						watertank.setCurrentCapacity(randomeLiter);
					}
				}

			}
		}, 10, 15, TimeUnit.SECONDS);

	}

}
