package com.md.mdcms.rest.services;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;

import com.md.mdcms.backingbean.ApplUserBean;
import com.md.mdcms.backingbean.ApplicationConfigurationBean;
import com.md.mdcms.backingbean.StartConfigurationBean;
import com.md.mdcms.rest.model.ApplicationConfiguration;
import com.md.mdcms.rest.model.LogoffModel;

@Path("basic") // http://localhost:8080/mdlicense/webapi/basic
public class BasicController {

	// private static final Log LOG = LogFactory.getLog(BasicController.class);

	// String JOBNUMBER = "jobNumber";

	// @GET
	// @Produces({ MediaType.APPLICATION_JSON })
	// public ApplicationConfiguration getBasicEnvironment() {
	// ApplicationConfiguration applicationConfiguration = new
	// ApplicationConfiguration();
	// applicationConfiguration.setEnvironment("RENE22");
	// return applicationConfiguration;
	// }

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("environment") // http://localhost:8080/mdlicense/webapi/basic/environment
	public Response getBasicEnvironment() {

		ApplicationConfigurationBean applicationConfigurationBean = ApplicationConfigurationBean.getInstance();

		StartConfigurationBean startConfigurationBean = StartConfigurationBean.getInstance();

		// String currentJobNumber = (String) request.getAttribute(JOBNUMBER);
		// applicationConfiguration.setCurrentJobNumber(currentJobNumber);

		// if (applicationConfiguration == null) {
		// return Response.status(Status.NOT_FOUND).build();
		// }

		ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
		// applicationConfiguration.setEnvironment(ApplicationConfigurationBean.getInstance().getEnvironment());

		try {
			BeanUtils.copyProperties(applicationConfiguration, applicationConfigurationBean);
			BeanUtils.copyProperties(applicationConfiguration, startConfigurationBean);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok().entity(applicationConfiguration).build();

		// System.out.println("get activity id:" + activityId);
		// return activityRepository.findActivity(activityId);
	}

	// @POST
	// @Path("activity") // http://localhost:8080/ex/webapi/activities/activity
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	// public Activity createActivityParams(MultivaluedMap<String, String>
	// formParams) {

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("logoff/{jobNumber}") // http://localhost:8080/mdlicense/webapi/basic/logoff/123456
	public Response doLogoff(@Context HttpServletRequest request, @PathParam("jobNumber") String jobNumber) {

		// if (applicationConfiguration == null) {
		// return Response.status(Status.NOT_FOUND).build();
		// }
		HttpSession session = request.getSession(false);
		ApplUserBean userBean = (ApplUserBean) session.getAttribute(ApplUserBean.BEAN_NAME);

		// String jobNumber = (String) request.getAttribute(JOBNUMBER);

		userBean.logoff(jobNumber);

		// if (userBean.getUserSessions().isEmpty()) {
		// LOG.debug("userBean has not iSeries");
		// }

		LogoffModel logoffModel = new LogoffModel();
		ApplicationConfigurationBean applicationConfiguration = ApplicationConfigurationBean.getInstance();

		logoffModel.setLogoffAddress(applicationConfiguration.getLogoff());

		return Response.ok().entity(logoffModel).build();

		// System.out.println("get activity id:" + activityId);
		// return activityRepository.findActivity(activityId);
	}

}
