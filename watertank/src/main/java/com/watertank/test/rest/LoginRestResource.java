package com.watertank.test.rest;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.watertank.test.app.UserValidator;
import com.watertank.test.entity.Credentials;
import com.watertank.test.entity.User;


@RestController
public class LoginRestResource {


	@Autowired
	private com.watertank.test.dao.UserService userStub;
	@Autowired
	private UserValidator validatorStub;

	public static final String USER = "user";
	public static final String AUTH_HEADER = "Authorization";
	public static final String BEARER = "Bearer";

	@RequestMapping(path = "registration", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public Response registration(@RequestBody(required = true) User user, BindingResult bindingResult,@Context HttpServletRequest req, @Context HttpSession session) {
		Set<String>  errors = validatorStub.validateRegistrationForm(user, bindingResult) ;
		if (!errors.isEmpty()) {
			return Response.status(Status.OK).entity(errors).build();
		}
		User created = userStub.createOrSaveUser(user);
		session.setAttribute(USER,created);
		return Response.status(Status.CREATED).entity(created).build();
	}

	@RequestMapping(path = "login", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public Response login(@RequestBody Credentials credentials, @Context HttpServletRequest req, @Context HttpSession session,BindingResult bindingResult) {
		Set<String>  errors = validatorStub.validateLoginFrom(credentials, bindingResult) ;
		if (!errors.isEmpty()) {
			return Response.status(Status.OK).entity(errors).build();
		}
		User  user  = userStub.findByUserPasswordAndEmail(credentials.getPassword(), credentials.getEmail());
		session.setAttribute(USER,user);
		return Response.status(Status.OK).entity(user).build();
	}


	@RequestMapping(path = "logout", produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public Response logout(@Context HttpServletRequest req,@Context HttpSession session) {
		session.removeAttribute(USER);
		session.invalidate();
		return Response.status(Status.OK).entity("Successfully logged out user from app").build();
	}


}
