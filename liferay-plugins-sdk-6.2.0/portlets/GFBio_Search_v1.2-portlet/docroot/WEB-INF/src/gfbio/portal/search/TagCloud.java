package gfbio.portal.search;

import java.io.IOException;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
public class TagCloud extends GenericPortlet
{

	protected static final String JSP_VIEW = "/tagcloud_view.jsp";

	protected void doView(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		response.setContentType(request.getResponseContentType());
		getPortletContext().getRequestDispatcher(JSP_VIEW).include(request,
				response);
	}
}