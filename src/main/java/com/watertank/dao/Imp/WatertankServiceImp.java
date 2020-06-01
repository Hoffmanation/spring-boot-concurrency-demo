package com.watertank.dao.Imp;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watertank.app.WatertankInMemoryDB;
import com.watertank.dao.WatertankService;
import com.watertank.model.Watertank;

/**
 * DAO Impl layer class for the {@link Watertank} entity
 * 
 * @author Hoffman
 *
 */
@Service
public class WatertankServiceImp implements WatertankService {
	private static final Logger loger = LoggerFactory.getLogger(WatertankServiceImp.class);

	/**
	 * Spring Dependency Injection
	 */
	@Autowired
	private WatertankInMemoryDB watertankInMemoryDB;

	/**
	 * Get all Cached waterTanks
	 */
	@Override
	public Map<Integer, Watertank> getAllWatertanks() {
		return watertankInMemoryDB.getWatertanks();
	};

	/**
	 * Get a specific Cached waterTanks
	 */
	@Override
	public Watertank getWatertankById(Integer watertankId) {
		synchronized (watertankId) {
			Watertank watertank = watertankInMemoryDB.getWatertanks().get(watertankId);
			if (watertank != null)
				return watertank;
			else
				loger.error("An invalid request for a non existed water-tank with id# {}",watertankId);
				throw new RuntimeException("Coudent find whattenk with id: " + watertankId);
		}
	}

}
