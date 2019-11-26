package com.watertank.test.daoImp;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watertank.test.dao.WatertankService;
import com.watertank.test.entity.WatertankWrapper;
import com.watertank.test.entity.WatertankWrapper.Watertank;
/**
 * A Service Impl class For the Watertank Entity
 * 
 * @author The Hoff
 *
 */
@Service
public class WatertankServiceImp implements WatertankService {

	
	private static final Logger log = LoggerFactory.getLogger(WatertankServiceImp.class);

	@Autowired
	private WatertankWrapper watertankWrapper;

	@Override
	public Map<Integer, Watertank> getAllWatertanks() {
		synchronized (this) {
			return watertankWrapper.getWatertanks();			
		}
	};

	@Override
	public Watertank getWatertankById(Integer watertankId){
		synchronized (watertankId) {
			Watertank  watertank  = watertankWrapper.getWatertanks().get(watertankId)  ;
		if (watertank !=null)
			return watertank;
		else
			throw  new RuntimeException("Coudent find whattenk with id: " + watertankId); 
		}
	}

}
