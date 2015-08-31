var sendingVerifyCode;
function getVerfiyCode(btn){
	if(sendingVerifyCode){
		return;
	}
	$(btn).css('background','#ddd');
	var tel = $('#tel').val();
	var pwd = $('#pwd').val();
	var set = $('.blue');
	if(!set){
		return;
	}
	if(!tel){
		alert('请先填写有效手机号码');
		return;
	}
	//提示信息
	YW.ajax({
		url:'http://'+server_host+'/c/mobile/user/sendVerifyCode',
		method:'post',
		data:{
        	values: {tel:tel}
    	},
		cache:false,
		returnAll:false
	},function(ret , err){
		if(ret){
			setcode();
		}else{
			alert(err.msg);
		}
		
	});
}

function setcode(){
	var times=60;
	var clock =  setInterval(function(){
		times--;
		if(times<1){
			$('.getcode').html('获取验证码');
			$('.getcode').attr('class','btn blue w40b btn_act getcode');
			clearInterval(clock);
			return;
		}
		$('.getcode').html('已发送('+times+')');
		$('.getcode').attr('class','btn gray w40b btn_act getcode');
	},1000);
}

function doReg(){
	var tel = $('#tel').val();
	var pwd = $('#pwd').val();
	var uname = $('#uname').val();
	var code = $('#verifyCode').val();
	if(!tel){
		alert('请输入正确的手机号码');
		return;
	}
	if(!uname){
		alert('请输入用户名');
		return;
	}
	if(!pwd){
		alert('请输入登录密码');
		return;
	}
	//提示信息
	YW.ajax({
		url:'http://'+server_host+'/c/mobile/user/verifyCode',
		method:'post',
		data:{
        	values: {tel:tel , code:code,pwd:pwd , uname:uname}
    	},
		cache:false,
		returnAll:false
	},function(ret , err){
		if(ret && ret.result=='1'){
			alert('注册成功');
			setTimeout(function(){
				api.closeWin({
				    name: 'reg'
			    });
			},1000);
		}else{
			alert(ret.msg);
		}
		
	});
}

function doModifyPwd(){
	var tel = $('#tel').val();
	var pwd = $('#pwd').val();
	var code = $('#verifyCode').val();
	if(!tel){
		alert('请输入正确的手机号码');
		return;
	}
	if(!pwd){
		alert('请输入登录密码');
		return;
	}
	//提示信息
	YW.ajax({
		url:'http://'+server_host+'/c/mobile/user/modifyPwd',
		method:'post',
		data:{
        	values: {tel:tel , code:code,pwd:pwd}
    	},
		cache:false,
		returnAll:false
	},function(ret , err){
		if(ret.result){
			alert('修改密码成功');
			api.hideProgress();
		}else{
			alert(ret.msg);
		}
	});
}

function selectCity(){
	api.openWin({
	    name: 'citys',
	    pageParam:{parentPage:'reg',title:'选择城市',pageName:'citys'},
		url: '../html/wrap.html'
	});
}

//call by wrap
function setCity(pinyin,name){
	$('#city').text(name);
}


function closexx(){
	api.closeWin({
	    name: 'reg'
    });
}

apiready=function(){
	api.getPrefs({
	    key:'city'
    },function(ret,err){
    	var v = ret.value;
    	if(!v){
    		//默认合肥
  		$('#city').text('合肥');
    	}else{
    		city = JSON.parse(v);
    		$('#city').text(city.name);
    	}
    });
};