package com.watertank.test.dao;

import java.util.Map;

import com.watertank.test.entity.WatertankWrapper.Watertank;
/**
 * DAO layer class for WatertankService entity
 * 
 * @author The Hoff
 *
 */
public interface WatertankService {

	public Map<Integer, Watertank> getAllWatertanks();

	public Watertank getWatertankById(Integer watertankId);

}
