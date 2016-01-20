<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.bc.textreco.entity.Eigen"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="org.bc.textreco.FontTrainer"%>
<%@page import="org.bc.textreco.Base64Util"%>
<%@page import="org.bc.textreco.BinarizationUtil"%>
<%@page import="java.io.File"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String str = request.getParameter("char");
if(StringUtils.isEmpty(str)){
	out.write("参数不正确");
	return;
}
FontTrainer trainer = new FontTrainer();
char ch=str.charAt(0);
BufferedImage img = ImageIO.read(new File("D:\\code\\text-reco\\std\\chars\\yahei\\normal\\"+(int)ch+".jpg"));
Eigen eigen = trainer.learn(img, String.valueOf(ch));
request.setAttribute("eigen", eigen);
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
${eigen.value }
</body>
</html>