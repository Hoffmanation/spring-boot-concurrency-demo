package com.watertank.test.daoImp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watertank.test.dao.WatertankService;
import com.watertank.test.entity.WatertankWrapper;
import com.watertank.test.entity.WatertankWrapper.Watertank;

@Service
public class WatertankServiceImp implements WatertankService {

	@Autowired
	private WatertankWrapper watertankWrapper;

	@Override
	public Map<Integer, Watertank> getAllWatertanks() {
		return watertankWrapper.getWatertanks();
	};

	@Override
	public Watertank getAllWatertankById(Integer watertankId){
		synchronized (watertankId) {
			Watertank  watertank  = watertankWrapper.getWatertanks().get(watertankId)  ;
		if (watertank !=null)
			return watertank;
		else
			throw  new RuntimeException("Coudent find whattenk with id: " + watertankId); 
		}
	}

}
