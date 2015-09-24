var payWay='offline';
apiready=function(){
	getUserInfo(function(user){
		$('#uname').text(user.uname);
		$('#tel').text(user.tel);
		payWay = user.payWay;
		if(api.systemType=='ios' && payWay!='online'){
			//ios版离线支付方式
			$('#bottomIOS').css("display","");
			
		}else{
			$('#bottomAll').css("display","");
		}
		if(api.systemType=='ios' && api.appVersion==user.iosShenHeVersion){
			$('#deadTimeWrap').css('display','none');
		}
		if(user.lname){
			$('#lname').text(user.lname);
		}
//		if(user.cname && user.dname){
//			$('#cname').text(user.cname);
//			$('#dname').text(user.dname);
//		}else{
//			$('#compWrap').css('display','none');
//			$('.account').css('height','144px');
//		}
		if(user.mobileDeadtime){
			$('#deadtime').text(user.mobileDeadtime);
		}else{
			$('#deadTimeWrap').css('display','none');
//			$('.account').css('height','144px');
		}
		if(user.fufei){
			$('#deadtime').css('color','blue');
		}else{
			$('#deadtime').text($('#deadtime').text()+'(已过期)');
			$('#deadtime').css('color','red');
		}
	});
}

function updateDeadtime(uid){
	YW.ajax({
		url:'http://'+server_host+'/c/mobile/user/getMobileDeadtime?uid='+uid,
		method:'get',
		cache:false,
		returnAll:false
	},function(ret , err){
		$('#deadtime').text(ret.mobileDeadtime);
	});
//	getUserInfo(function(user){
//		$('#deadtime').text(user.mobileDeadtime);
//	});
}
function closexx(){
//	api.execScript({
//	    name: 'login',
//	    script: 'closexx();'
//	});
	
	api.closeWin({
	   name: 'user'
	});
}

	function SeeThis(type){
        api.actionSheet({
            cancelTitle: '取消',
            buttons: ['出售','出租']
        },function(ret,err){
        	if(ret.buttonIndex==1){
        		api.openWin({
		             name: 'wrap_wode',
		             pageParam: {type: type},
		 			url: '../html/wrap_wode.html',
		             bgColor: '#fff'
		         });
        	}else if(ret.buttonIndex==2){
        		api.openWin({
		             name: 'wrap_wode',
		             pageParam: {type: type+'Rent'},
		 			url: '../html/wrap_wode.html',
		             bgColor: '#fff'
		         });
        	}
        });
     }
	
	function ShareUs(){
        api.openWin({
            name: 'share',
			url: '../html/share.html',
            bgColor: '#fff'
        });
     }
	
function logout(){
	api.setPrefs({
	    key: 'user',
	    value: ''
	});
	
	YW.ajax({
		url:'http://'+server_host+'/c/mobile/user/logout?tel='+$('#tel').text(),
		method:'post',
		cache:false,
		returnAll:false
	},function(ret , err){
	});
	api.execScript({
	    name: 'root',
	    script: 'refreshHouses();'
	});
		
	closexx();
}

function xufei(){
	api.openWin({
        name: 'order',
		bounces: true,
		url: 'pay.html',
        bgColor: '#fff'
    });		
}

function clearCache(){
	api.setPrefs({
	    key:'tel',
	    value:''
    });
    api.setPrefs({
	    key:'pwd',
	    value:''
    });
    alert('缓存清理成功');
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
