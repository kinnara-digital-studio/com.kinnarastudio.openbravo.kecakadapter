package com.kinnarastudio.openbravo.kecakadapter.webservice;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.dal.service.OBDal;
import org.openbravo.service.json.DataEntityQueryService;
import org.openbravo.service.web.WebService;

import com.kinnarastudio.openbravo.kecakadapter.webservice.RecordCountWebService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetColumnsWebService implements WebService {
	public static final Logger log4j = Logger.getLogger(GetColumnsWebService.class);

	@Override
	public void doGet(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log4j.info("doGet : path [" + path + "]");
		
		final String entityName = path.replaceAll("^/", "").replaceAll("\\?.+$", "");
		log4j.info("entityName [" + entityName + "]");
		
		final DataEntityQueryService queryService = OBProvider.getInstance().get(
		        DataEntityQueryService.class);
		
		queryService.setEntityName(entityName);
		queryService.setFilterOnReadableClients(true);
		
		final int count = queryService.count();

		final JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("status", "success");
		jsonResponse.put("count", count);
		response.setContentType("application/json");
		response.getWriter().write(jsonResponse.toString());
	}
	
	public void doPost(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
	public void doPut(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
	public void doDelete(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
}
