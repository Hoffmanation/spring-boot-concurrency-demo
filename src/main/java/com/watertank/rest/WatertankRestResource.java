package com.watertank.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watertank.dao.Imp.WatertankManager;
import com.watertank.model.Watertank;

/**
 * A Collection of {@link RestController} methods exposing endopints for CRUD
 * operations on the {@link Watertank} entity
 * 
 * @author Hoffman
 *
 */
@RestController
public class WatertankRestResource {

	@Autowired
	private WatertankManager watertankManager;

	/**
	 * Retrieve an {@link ArrayList} of all current {@link Watertank} 's
	 * 
	 * @param req
	 * @param session
	 * @return {@link Response}
	 */
	@RequestMapping(path = "getAllWatertanks", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public Response getAllWatertanks(@Context HttpServletRequest req, @Context HttpSession session) {
		List<Watertank> al = new ArrayList<Watertank>(watertankManager.getAllWatertanks().values());
		return Response.status(200).entity(al).build();
	}

	/**
	 * Retrieve the maximum capacity amount of a specific water tank container
	 * 
	 * @param watertankId
	 * @param req
	 * @param session
	 * @return {@link Response}
	 */
	@RequestMapping(path = "QueryMaxCapacity", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public Response QueryMaxCapacity(@RequestParam("id") Integer watertankId, @Context HttpServletRequest req, @Context HttpSession session) {
		String queryMaxCapacity = String.valueOf(watertankManager.getMaxCapacity(watertankId));
		return Response.status(200).entity(queryMaxCapacity).build();
	}

	/**
	 * Retrieve the current capacity amount of a specific water tank container
	 * 
	 * @param watertankId
	 * @param req
	 * @param session
	 * @return {@link Response}
	 */
	@RequestMapping(path = "QueryCurrentCapacity", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public Response QueryCurrentCapacity(@RequestParam("id") Integer watertankId, @Context HttpServletRequest req, @Context HttpSession session) {
		String queryCurrentCapacity = String.valueOf(watertankManager.getCurrentCapacity(watertankId));
		return Response.status(200).entity(queryCurrentCapacity).build();
	}

	/**
	 * Add water amount to a specific water tank container
	 * 
	 * @param liter
	 * @param watertankId
	 * @param req
	 * @param session
	 * @return {@link Response}
	 */
	@RequestMapping(path = "AddWater", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public Response AddWater(@RequestParam("liter") double liter, @RequestParam("id") Integer watertankId, @Context HttpServletRequest req, @Context HttpSession session) {
		String addWater = String.valueOf(watertankManager.addWater(liter, watertankId));
		return Response.status(200).entity(addWater).build();
	}

}
