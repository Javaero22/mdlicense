package com.md.mdcms.rest.services;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.md.mdcms.rest.RequestHandler;
import com.md.mdcms.rest.RequestHandler2;
import com.md.mdcms.rest.model.RW7000BackingBeanManager;
import com.md.mdcms.rest.model.ResponseModel;

@Path("RW7000") // http://localhost:8080/mdlicense/webapi/
public class RW7000Controller {

	@Context
	private ServletContext context;

	@POST
	@Path("/process") // http://localhost:8080/mdlicense/webapi/RW7000/process
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response processButton(@Context HttpServletRequest request, RW7000BackingBeanManager rw7000) {

		System.out.println("Entered licensed serial number: " + rw7000.getSrlnbr().getValue());

		ResponseModel responseModel = new ResponseModel();
		responseModel.setJobNumber(rw7000.getJobNumber());

		RequestHandler2 requestHandler = new RequestHandler2();
		rw7000 = (RW7000BackingBeanManager) requestHandler.handleRequest(request, rw7000);

		return Response.ok().entity(rw7000).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("{jobNumber}") // http://localhost:8080/mdlicense/webapi/
	public Response getResponse(@Context HttpServletRequest request, @PathParam("jobNumber") String jobNumber) {

		// String JOBNUMBER = "jobNumber";
		//
		// if (jobNumber == null) {
		// return Response.status(Status.NOT_FOUND).build();
		// }
		//
		// ResponseModel responseModel = new ResponseModel();
		// responseModel.setJobNumber(jobNumber);
		//
		// request.setAttribute(JOBNUMBER, jobNumber);
		RequestHandler requestHandler = new RequestHandler();

		// ResponseModel responseModel = new ResponseModel();

		RW7000BackingBeanManager rw7000 = (RW7000BackingBeanManager) requestHandler.handleRequest(request, jobNumber,
				RW7000BackingBeanManager.class);

		// responseModel.setJobNumber(rw7000.getJobNumber());
		// responseModel.setScreen(rw7000.getResponseState().getScreen());
		// responseModel.setBackingBean(rw7000);

		// RW7000BackingBeanManager bbm = new RW7000BackingBeanManager(request,
		// jobNumber);

		// rw7000.setLinkTitle("Reseller");
		// StringField stringField = new StringField();
		// stringField.setLabel("Partner Area René 22: ");
		// rw7000.setSrlnbr(stringField);

		// try {
		// BeanUtils.copyProperties(rw7000, bbm);
		// } catch (IllegalAccessException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InvocationTargetException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// rw7000.setSrlnbr(bbm.getSrlnbr().getHtml());
		// rw7000.setSrlnbrLabel(bbm.getSrlnbr().getLabel());

		// rw7000.setScreenTitle(bbm.getScreenTitle());
		// rw7000.setLinkTitle(bbm.getButtonBean().getNavigation().getLinkTitle());

		// List<INavigation> navigationList =
		// rw7000.getButtonBackingBean().getNavigation().getRequestcodes();
		// List<Navigation> navigationsListNew = new ArrayList<Navigation>();
		// for (INavigation iNavigation : navigationList) {
		// Navigation navigation = new Navigation();
		//
		// navigation.setLabel(iNavigation.getLabel());
		//
		// navigationsListNew.add(navigation);
		// }
		//
		// rw7000.setRequestcodes(navigationsListNew);

		// rw7000.setSrlnbr(bbm.getSrlnbr());

		// responseModel.setBackingBean(rw7000);

		return Response.ok().entity(rw7000).build();
	}

}
