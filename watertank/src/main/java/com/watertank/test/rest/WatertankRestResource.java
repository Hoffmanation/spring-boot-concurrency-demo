package com.watertank.test.rest;

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

import com.watertank.test.daoImp.WatertankFacade;
import com.watertank.test.entity.WatertankWrapper.Watertank;

/**
 * A Controller class for App Watertank Bean CRUD  -  RestFull API calls
 * 
 * @author The Hoff
 *
 */
@RestController
public class WatertankRestResource {

	@Autowired
	private WatertankFacade watertankStub;

	@RequestMapping(path = "getAllWatertanks", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public Response getAllWatertanks(@Context HttpServletRequest req, @Context HttpSession session) {
		List<Watertank> al = new ArrayList<Watertank>( watertankStub.getAllWatertanks().values());
		return Response.status(200).entity(al).build();
	}

	@RequestMapping(path = "QueryMaxCapacity", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public Response QueryMaxCapacity(@RequestParam("id") Integer watertankId, @Context HttpServletRequest req,
			@Context HttpSession session) {
		String queryMaxCapacity =String.valueOf(watertankStub.QueryMaxCapacity(watertankId)) ;
		return Response.status(200).entity(queryMaxCapacity).build();
	}

	@RequestMapping(path = "QueryCurrentCapacity", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public Response QueryCurrentCapacity(@RequestParam("id") Integer watertankId, @Context HttpServletRequest req,
			@Context HttpSession session) {
		String queryCurrentCapacity =String.valueOf(watertankStub.QueryCurrentCapacity(watertankId)) ;
		return Response.status(200).entity(queryCurrentCapacity).build();
	}

	@RequestMapping(path = "AddWater", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public Response AddWater(@RequestParam("liter") double liter, @RequestParam("id") Integer watertankId,
			@Context HttpServletRequest req, @Context HttpSession session) {
		String addWater =String.valueOf(watertankStub.AddWater(liter, watertankId)) ;
		return Response.status(200).entity(addWater).build();
	}

}
