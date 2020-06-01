package com.watertank.dao;

import java.util.Map;

import com.watertank.model.Watertank;
/**
 * DAO layer class for {@link Watertank} entity
 * 
 * @author Hoffman
 *
 */
public interface WatertankService {

	public Map<Integer, Watertank> getAllWatertanks();

	public Watertank getWatertankById(Integer watertankId);

}
