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
        url: 'c/rule/add',
        dataType:'json',
        data:a,
        mysuccess: function(data){
           $('#action').val('');
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
规则名:<input name="name"  id="name"/>
<br /><br />
处理方式:<textarea rows="10" cols="60" id="action" name="action"></textarea>
<input type="submit"/>
</form>
</body>
</html>