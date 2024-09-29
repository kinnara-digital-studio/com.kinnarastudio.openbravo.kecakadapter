package com.kinnarastudio.openbravo.kecakadapter.webservice;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.service.json.DataEntityQueryService;
import org.openbravo.service.json.JsonConstants;
import org.openbravo.service.web.WebService;

public class RecordCountWebService implements WebService{
	public static final Logger log4j = Logger.getLogger(RecordCountWebService.class);

	@Override
	public void doGet(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log4j.info("doGet : path [" + path + "]");
		
		final String entityName = path.replaceAll("^/", "").replaceAll("\\?.+$", "");
		
		if(entityName.isEmpty()) {
			response.sendError(400, "Table entity is not provided");
		}
		
		final DataEntityQueryService queryService = OBProvider.getInstance().get(
		        DataEntityQueryService.class);
		
		queryService.setEntityName(entityName);
		queryService.setFilterOnReadableClients(true);
		
		Map<String, String> parameters = getParameterMap(request);
		
		final JSONObject jsonParameters = new JSONObject(parameters);
		queryService.setCriteria(jsonParameters);
		
		parameters.put(JsonConstants.USE_ALIAS, "true");
        parameters.put(JsonConstants.IS_WS_CALL, "true");
        parameters.put(JsonConstants.WHERE_AND_FILTER_CLAUSE, parameters.get(JsonConstants.WHERE_PARAMETER));
        
        parameters.forEach(queryService::addFilterParameter);
		
		final int count = queryService.count();

		final JSONObject jsonResponse = new JSONObject() {{
			put("response", new JSONObject() {{
				put("count", count);
			}});	
		}};

		
		response.setContentType("application/json");
		response.getWriter().write(jsonResponse.toString());
	}

	@Override
	public void doPost(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}

	@Override
	public void doDelete(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	@Override
	public void doPut(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
	protected Map<String, String> getParameterMap(HttpServletRequest request) {
	    final Map<String, String> parameterMap = new HashMap<String, String>();
	    for (@SuppressWarnings("rawtypes") Enumeration<?> keys = request.getParameterNames(); keys.hasMoreElements();) {
	      final String key = (String) keys.nextElement();
	      parameterMap.put(key, request.getParameter(key));
	    }
	    return parameterMap;
	}

}
