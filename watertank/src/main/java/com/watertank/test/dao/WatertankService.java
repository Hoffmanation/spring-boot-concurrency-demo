package com.watertank.test.dao;

import java.util.Map;

import com.watertank.test.entity.WatertankWrapper.Watertank;

public interface WatertankService {

	public Watertank updateWatertankById(Watertank watertank) ;

	public Map<Integer, Watertank> getAllWatertanks();

	public Watertank getAllWatertankById(Integer watertankId);

}
