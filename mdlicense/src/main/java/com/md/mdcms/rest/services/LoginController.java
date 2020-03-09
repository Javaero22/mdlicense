package com.md.mdcms.rest.services;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.md.mdcms.rest.LoginHandler;
import com.md.mdcms.rest.model.LoginInitBackingBeanManager;
import com.md.mdcms.rest.model.LoginModel;

@Path("login") // http://localhost:8080/mdlicense/webapi/
public class LoginController {

	@Context
	private ServletContext context;

	@Context
	private SecurityContext securityContext;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response doLogin(@Context HttpServletRequest request) {

		LoginHandler loginHandler = new LoginHandler();
		loginHandler.handleLogin(securityContext, request);

		LoginInitBackingBeanManager loginInitBackingBeanManager = new LoginInitBackingBeanManager(securityContext,
				request);

		LoginModel responseModel = new LoginModel();
		responseModel.setJobNumber(loginInitBackingBeanManager.getJobNumber());
		responseModel.setResponseState(loginInitBackingBeanManager.getResponseState());

		// HttpSession session = request.getSession(false);
		// Object foo = session.getAttribute("foo");
		// if (foo != null) {
		// System.out.println(foo.toString());
		// } else {
		// foo = "bar";
		// session.setAttribute("foo", "bar");
		// }

		// if (applicationConfiguration == null) {
		// return Response.status(Status.NOT_FOUND).build();
		// }

		return Response.ok().entity(responseModel).build();

		// System.out.println("get activity id:" + activityId);
		// return activityRepository.findActivity(activityId);
	}

}
