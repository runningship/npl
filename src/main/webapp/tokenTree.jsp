<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	var canvas;
	var context;
	var radius=30;
	var levelHeight=100;
	//var nodeSpaceing=50;
	var textOffset=-8;
	var textYOffset = 2;
	var Node = {
		text:"",
		left:null,
		right:null
	};
	function drawCircle(node) {
		if (canvas == null) {
			return false;
		}
		context = canvas.getContext('2d');
		context.globalCompositeOperation="";
		context.beginPath();
		context.arc(node.x, node.y, radius, 0, Math.PI * 2, true);
		context.closePath();
		context.fillStyle = 'rgba(0,255,0,0.25)';
		context.fill();
		context.strokeStyle = 'black';
		context.strokeText(node.text,node.x+textOffset,node.y+textYOffset);
	}
	function draw(root , nodeSpaceing){
		drawCircle(root);
		var subNodeSpaceing = nodeSpaceing-30;
		if(root.left!=null){
			//drawline
			root.left.x = root.x-nodeSpaceing;
			root.left.y = root.y+levelHeight;
			drawLine(root,root.left);
			draw(root.left , subNodeSpaceing);
		}
		if(root.right!=null){
			root.right.x = root.x+nodeSpaceing;
			root.right.y = root.y+levelHeight;
			drawLine(root,root.right);
			draw(root.right , subNodeSpaceing);
		}
	}
	function drawLine(node1,node2){
		context.beginPath();
		context.strokeStyle = 'red';
		context.moveTo(node1.x,node1.y+radius);
		context.lineTo(node2.x,node2.y-radius);
		context.stroke();
		context.closePath();
	}
	window.onload=function(){
		canvas = document.getElementById("canvas");
		//var root = JSON.parse("{}");
		var root = JSON.parse('${tree}');
// 		root.text="root";
// 		root.left=JSON.parse("{}");
// 		root.right = JSON.parse("{}");
// 		root.left.text="node1";
// 		root.right.text="node2";
		root.x=600;
		root.y=radius;
		draw(root , 200);
	}
</script>
<title>SemanticTree</title>
</head>
<body>
<canvas id="canvas" height=800px width=1680px></canvas>
</body>
</html>