<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/buildHtml.js"></script>
<script type="text/javascript">
function doSave(){
	var a=$('form[name=form1]').serialize();
    BC.ajax({
        type: 'POST',
        url: 'c/base/cmd/add',
        dataType:'json',
        data:a,
        mysuccess: function(data){
           $('#content').val('');
           $('#name').val('');
           alert(data.result);
        }
    });
}
</script>
<title></title>
</head>
<body>
<form name="form1" onsubmit="doSave();return false;">
方法名:<input name="name"  id="name"/>
<br /><br />
方法体:<textarea rows="20" cols="100" id="content" name="conts"></textarea>
<input type="submit"/>
</form>
</body>
</html>