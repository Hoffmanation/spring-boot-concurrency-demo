package com.watertank.test.entity;

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

import com.watertank.test.daoImp.WatertankFacade;

/**
 * A Wrapper class that will hold the Watertank entity and will initialize the create-a-Leak thread for every watertank container.
 * @author The Hoff
 *
 */
@Component
@ConfigurationProperties(prefix = "app")
public class WatertankWrapper {
	private Set<Watertank> watertanks = new HashSet<>();
	private ConcurrentHashMap<Integer, Watertank> watertanksStorage;

	private static final Logger log = LoggerFactory.getLogger(WatertankWrapper.class);

	@Autowired
	private WatertankFacade watertankFacade;

	@PostConstruct
	public void init() {
		watertanksStorage = new ConcurrentHashMap<>();
		for (Watertank watertank : watertanks) {
			log.info("Initialized a new Watertank with id: " + watertank.getId());
			watertanksStorage.put(watertank.getId(), watertank);
			watertankFacade.createALeak(watertank.getLiterOfLeak(), watertank.getWaterLeakRate(), watertank.getId());
		}
		watertanks = new HashSet<>() ;
	}

	public Map<Integer, Watertank> getWatertanks() {
		return watertanksStorage;
	}

	public void setWatertanks(Set<Watertank> watertanks) {
		this.watertanks = watertanks;
	}

	public static class Watertank {

		private Integer id;
		private double maxCapacity;
		private double currentCapacity;
		private int waterLeakRate;
		private double literOfLeak;

		public Watertank() {

		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public double getMaxCapacity() {
			return maxCapacity;
		}

		public void setMaxCapacity(double maxCapacity) {
			this.maxCapacity = maxCapacity;
		}

		public double getCurrentCapacity() {
			return currentCapacity;
		}

		public void setCurrentCapacity(double currentCapacity) {
			this.currentCapacity = currentCapacity;
		}

		public int getWaterLeakRate() {
			return waterLeakRate;
		}

		public void setWaterLeakRate(int waterLeakRate) {
			this.waterLeakRate = waterLeakRate;
		}

		public double getLiterOfLeak() {
			return literOfLeak;
		}

		public void setLiterOfLeak(double literOfLeak) {
			this.literOfLeak = literOfLeak;
		}

		@Override
		public String toString() {
			return "Watertank [id=" + id + ", maxCapacity=" + maxCapacity + ", currentCapacity=" + currentCapacity
					+ ", waterLeakRate=" + waterLeakRate + ", literOfLeak=" + literOfLeak + "]";
		}

	}
}
