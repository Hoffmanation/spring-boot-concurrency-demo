package com.watertank.app;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.watertank.dao.Imp.WatertankManager;
import com.watertank.model.Watertank;

/**
 * A Wrapper class that will hold the Watertank entity and will initialize the
 * create-a-Leak thread for every watertank container.
 * This class is serving as a in-Memory DB
 * 
 * @author Hoffman
 *
 */
@Component
@ConfigurationProperties(prefix = "app")
public class WatertankInMemoryDB {
	private static final Logger log = LoggerFactory.getLogger(WatertankInMemoryDB.class);
	private Set<Watertank> watertanks = new HashSet<>();
	private ConcurrentHashMap<Integer, Watertank> watertanksStorage;

	/**
	 * Spring Dependency Injection
	 */
	@Autowired
	private WatertankManager watertankManager;

	/**
	 * Init method will create all {@link Watertank} instance and will save it in an in-memory {@link Map} cache
	 * Will initialize 'create leak operation' in a new thread @see {@link WatertankManager#createALeak(double, long, Integer)}
	 * Will initialize 'renew Water Tank operation' in a new thread @see {@link WatertankManager#renewWaterTank()}
	 */
	@PostConstruct
	public void init() {
		watertanksStorage = new ConcurrentHashMap<>();
		for (Watertank watertank : watertanks) {
			log.info("Initialized a new Watertank with id: " + watertank.getId());
			watertanksStorage.put(watertank.getId(), watertank);
			watertankManager.createALeak(watertank.getLiterOfLeak(), watertank.getWaterLeakRate(), watertank.getId());
		}
		watertanks = new HashSet<>();
		watertankManager.renewWaterTank() ;
	}

	/**
	 * Get the water-tank in-memory cached DB
	 * @return Map<Integer, Watertank>
	 */
	public Map<Integer, Watertank> getWatertanks() {
		return watertanksStorage;
	}

	/**
	 * Metyhod will bind properties under 'app' @see {@ConfigurationProperties}
	 * @param watertanks
	 */
	public void setWatertanks(Set<Watertank> watertanks) {
		this.watertanks = watertanks;
	}


}
