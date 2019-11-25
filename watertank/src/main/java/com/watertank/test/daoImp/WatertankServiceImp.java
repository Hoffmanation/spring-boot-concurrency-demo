package com.watertank.test.daoImp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watertank.test.entity.WatertankWrapper;
import com.watertank.test.entity.WatertankWrapper.Watertank;

@Service
public class WatertankServiceImp implements com.watertank.test.dao.WatertankService {

	@Autowired
	private WatertankWrapper watertankWrapper ;

	@Override
	public Map<Integer,Watertank> getAllWatertanks(){
		return watertankWrapper.getWatertanks() ;
	};

	
	@Override
	public Watertank getAllWatertankById(Integer watertankId){
		Watertank  watertank  = watertankWrapper.getWatertanks().get(watertankId)  ;
		if (watertank !=null)
			return watertank;
		else
			throw  new RuntimeException("Coudent find whattenk with id: " + watertankId); 
	}


	@Override
	public Watertank updateWatertankById(Watertank watertank) {
		getAllWatertanks().put(watertank.getId(), watertank) ;
		return null;
	};
}
