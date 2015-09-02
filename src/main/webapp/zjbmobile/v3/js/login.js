var deviceId;
var tel;
function doLogin(){
//	var a=$('form[name=form1]').serialize();
//	var args = JSON.parse("{}");
//	args.body = a;
	tel = $('#tel').val();
	var pwd = $('#pwd').val();
	if(!tel){
		alert('请输入正确的手机号码');
		return;
	}
	if(!pwd){
		alert('请输入登录密码');
		return;
	}
	YW.ajax({
		url:'http://'+server_host+'/c/mobile/user/login',
		method:'post',
		data:{
        	values: {tel:tel,pwd:pwd,deviceId:api.deviceId}
    	},
		cache:false,
		returnAll:false
	},function(ret , err){
		if(ret && ret.result==0){
				api.setPrefs({
				    key: 'tel',
				    value: tel
				});
				api.setPrefs({
				    key: 'pwd',
				    value: pwd
				});
				ret.online=true;
				api.setPrefs({
				    key: 'user',
				    value: JSON.stringify(ret)
				});
				
				ret.pageName='user';
				ret.title='我的';
				ret.pageUrl='../html/user.html';
				api.execScript({
				    name: 'root',
				    script: 'refreshHouses();'
				});
				//closexx();
        		api.openWin({
			        name: 'user',
			        delay:1000,
			        pageParam: ret,
					url: '../html/wrap.html',
			        bgColor: '#fff'
			    });
		}else{
			alert(ret.msg);
		}
		
	});
}

//function pushApi(){
//	var push = api.require('push');
//	push.bind({
//	    userName:deviceId,
//	    userId:tel
//	},function(ret,err){
//	});
//
//}

apiready=function(){
	api.getPrefs({
	    key:'tel'
    },function(ret,err){
    	$('#tel').val(ret.value);
    });
    
    api.getPrefs({
	    key:'pwd'
    },function(ret,err){
    	$('#pwd').val(ret.value);
    });
    
	api.getPrefs({
	    key:'city'
    },function(ret,err){
    	var v = ret.value;
    	if(!v){
    		//默认合肥
  		$('#city').val('选择城市');
    	}else{
    		city = JSON.parse(v);
    		$('#city').text(city.name);
    	}
    });
    deviceId = api.deviceId;
};
function closexx(){
	api.closeWin({
	    name: 'login'
    });
}

function selectCity(){
	api.openWin({
	    name: 'citys',
	    pageParam:{parentPage:'login',title:'选择城市',pageName:'citys'},
		url: '../html/wrap.html'
	});
}

//call by wrap
function setCity(pinyin,name){
	$('#city').text(name);
}

function clearCache(){
	api.setPrefs({
	    key:'city',
	    value:''
    });
}
function openModifyPwd(){

	api.openWin({
        name: 'reg',
        pageParam: {pageName: 'pwd'},
		url: 'pwd.html',
		bounces: true,
		scaleEnabled:true,
        bgColor: '#fff'
    });
}

function openReg(){
//	api.openWin({
//        name: 'reg',
//        pageParam: {pageName: 'reg',title:'注册'},
//		url: 'wrap.html',
//        bgColor: '#fff'
//    });

	api.openWin({
        name: 'reg',
        pageParam: {pageName: 'login'},
		url: 'reg.html',
		bounces: true,
		scaleEnabled:true,
        bgColor: '#fff'
    });
	
//	window.location='reg.html';
}

function startSoftInput(){
	$('#wrap').addClass('move');
}
function endSoftInput(){
	$('#wrap').removeClass('move');
}