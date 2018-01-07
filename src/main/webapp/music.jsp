<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/music.js"></script>
<script type="text/javascript">
//自定义部分----------------
var notes = [
  ['A4',1],['B4',1],['C5',3],['B4',1],['C5',2],['E5',2],['B4',6],
  ['E4',1],['E4',1],['A4',3],['G4',1],['A4',2],['C5',2],['G4',4],
  ['0',2],['E4',1],['E4',1],['F4',3],['E4',1],['F4',1],['C5',3],['E4',4],
  ['0',1],['C5',1],['C5',1],['C5',1],['B4',3],['F#4',1],['F#4',2],['B4',2],['B4',2]
] ;
//自定义部分-----------
//创建播放器
var player = new simple_player();
//播放
player.play(notes);
</script>
<title></title>
</head>
<body>

</body>
</html>