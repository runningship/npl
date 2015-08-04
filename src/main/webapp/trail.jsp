<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
var index = 1;
$(document).bind('keyup',function(){
	if(event.keyCode==38){
		//up
		if(index<9){
			index++;
		}
	}else if(event.keyCode=40){
		//down
		if(index>1){
			index--;
		}
	}
	$('#map').attr('src' , 'images/'+index+'.jpg');
});
</script>
</head>
<body>
<img id="map" src="images/1.jpg"/ style="width:1200px;height:600px">
</body>
</html>