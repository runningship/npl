<%@page import="org.bc.textreco.Base64Util"%>
<%@page import="org.bc.textreco.BinarizationUtil"%>
<%@page import="java.io.File"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
BufferedImage img = BinarizationUtil.getImage("D:\\code\\text-reco\\std\\chars\\yahei\\normal\\65.jpg");
BufferedImage result = BinarizationUtil.processByMaxOffset(img , 0.70f);
String base64Data = Base64Util.decode(result);
System.out.println(base64Data);
request.setAttribute("base64Data", base64Data);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
</script>
<title></title>
</head>
<body>
<img src="data:image/gif;base64,${base64Data}" />
</body>
</html>