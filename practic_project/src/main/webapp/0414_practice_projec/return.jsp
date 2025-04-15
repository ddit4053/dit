<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="kr.or.ddit.returnCa.vo.ReturnVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<ReturnVo> list = (List<ReturnVo>)request.getAttribute("list");
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	String result = gson.toJson(list);
	
	out.print(result);
	out.flush();

%>