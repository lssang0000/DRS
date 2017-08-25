<!DOCTYPE html>

<%@page import="java.util.Iterator"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dsp.engine.DatasetProvider"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.2/themes/default/style.min.css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.2/jstree.min.js"></script>
<script type="text/javascript" src="./js/script.js"></script>

</head>
<body>
	<!--<c:url value="/showMessage.html" var="messageUrl" />-->
	<!--<a href="${messageUrl}">Click to enter</a>-->

	<%
		int indexNum = 0;
		int doctypeNum = 0;
		int propertyNum = 0;

		DatasetProvider dsp = DatasetProvider.getInstance();
		JSONObject dataMap = dsp.getDataMap();
		ArrayList<String> dsList = dsp.getdatasetList();
		//System.out.println(dsp.getDataMap().toString());
		//System.out.println(dsp.datasetList().toString());
	%>

	<h1>lssang's Data-set Editor :D</h1>
	
	<h2>Created Data-set Models</h2>
	These are already created datasetModels. You can use restful service by using data-set names below. </br>
	Call Restful service 'search' in my server, and set a parameter 'dsname' as shown in example below. </br>
	GET Example : http://203.253.23.53:8080/dsp-0-1-1/search?dsname = 'test-bank' </br>
	POST Example : Do it your self!! </br>
	<div id="tree_models">
		<ul>
			<li id="folder_1">Data-set Models
				<ul>
				<%
				int dsId=0;
				for(int i = 0 ; i < dsList.size() ; i++){
					dsId++;
				%>
					<li id="dsmodel_<%=dsId%>"><%=dsList.get(i)%></li>
				<%
				}
				%>
				</ul>
			</li>
		</ul>
	</div>
	
	<h2>Data Map for Elasticsearch</h2>
	The tree below is a DATA MAP of my repository. </br>
	You can check items that you want to use as data-set. </br>
	(Each item represents column of your data-set as table)</br>
	
	<div id="tree_datamap">
		<ul>
			<%
				Iterator<String> keys = dataMap.keys();
				indexNum=0;
				while( keys.hasNext()){					
					indexNum++;
					String key = keys.next();
					JSONObject doctype = new JSONObject(dataMap.get(key).toString());
					//JSONObject doctype = dataMap.getJSONObject(key);
			%>
			<li id="index_<%=indexNum%>"><%=key%> - (index)
				<ul>
					<%
						Iterator<String> dt_keys = doctype.keys();
						doctypeNum=0;
						while (dt_keys.hasNext()) {
							doctypeNum++;
							String dt_key = dt_keys.next();
							JSONObject tmep = new JSONObject(doctype.get(dt_key).toString());
							JSONObject properties = new JSONObject(tmep.get("properties").toString());
							//JSONObject doctype = dataMap.getJSONObject(dt_key);
					%>
					<li id="doctype_<%=indexNum%>_<%=doctypeNum%>"><%=dt_key%> - (document type)
						<ul>
							<%
								Iterator<String> pt_keys = properties.keys();
								propertyNum=0;
								while (pt_keys.hasNext()) {
									propertyNum++;
									String pt_key = pt_keys.next();
							%>
								<li id="prop_<%=indexNum%>_<%=doctypeNum%>_<%=propertyNum%>"><%=pt_key%></li>
							<%
								}
							%>
						</ul>
					</li>
					<%
						}
					%>
				</ul>
			</li>
			<%
				}
			%>
		</ul>
	</div>



</body>
</html>
