var uid;
	var uname;
	var addMonth=3;
	var payType = 1;
	var userInfo;
    var packageInfo;
    var info;
    var amount =100;
    var addingRecord=false;
	apiready = function(){
		api.addEventListener({
		    name:'keyback'
		},function(ret,err){
		    if(addingRecord){
				alert('操作进行中，请稍后..');
			}else{
				closexx();	
			}
		});
		getUserInfo(function(user){
			userInfo = user;
			uid = user.uid;
			uname = user.uname;
		});
		getFeeInfo();
	};
	
	function closexx(){
		if(addingRecord){
			return;
		}
		api.closeWin({
		    name: 'order'
	    });
	}

function aliPayIOS(){
	var subject = '中介宝手机版费用'+userInfo.tel;
	var body = '中介宝手机版费用'+userInfo.tel;
	
	var url = 'http://'+server_host+'/pay/mobile/alipayapi.jsp?&uid='+userInfo.uid+'&monthAdd='+addMonth+'&WIDout_trade_no='+new Date().getTime()+'&WIDsubject='+subject+'&WIDtotal_fee=0.01&WIDbody='+body;
	api.openApp({
	    androidPkg: 'android.intent.action.VIEW',
//		iosUrl : url,
	    mimeType: 'text/html',
	    uri: url
	},function(ret,err){
	    api.confirm({
		    msg: '支付完成',
		    buttons:['确定']
		},function(ret,err){
		    api.execScript({
			    name: 'user',
			    script: 'updateDeadtime('+uid+');'
			});
			closexx();
		});
	});
//	api.openWin({
//	    name: 'aliWebPay',
//		url: url
//	});
}

function refreshUserInfo(){
	YW.ajax({
		url: 'http://'+server_host+'/c/mobile/user/getUserFufeiInfo?uid='+userInfo.uid,
		method:'get',
		cache:false,
		returnAll:false
	},function(ret , err){
		if(ret){
			userInfo.mobileDeadtime = ret.mobileDeadtime;
			userInfo.fufei = ret.fufei;
			api.setPrefs({
	            key:'user',
	            value:JSON.stringify(userInfo)
            });
			
			//更新user页面的服务到期时间
			api.setPrefs({
			    key:'user',
			    value:JSON.stringify(userInfo)
		    });
			api.execScript({
			    name: 'user',
			    script: 'updateDeadtime('+uid+');'
			});
			closexx();
		}
	});
}

function aliPay(){
	
	if(api.systemType=='android'){
		aliPayIOS();
		return;
	}
	var obj = api.require('aliPay');
	var subject = '中介宝手机版费用'+userInfo.tel;
	var body = '中介宝手机版费用'+userInfo.tel;
	var tradeNO = new Date().getTime();
	var notifyURL = 'http://'+server_host+'/c/mobile/user/alipayCallback'
	
	obj.config({
	  partner:'2088711888914717',
	  seller:'2088711888914717',
	  rsaPriKey:'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMfIHTbOyuw9ZVoh7Tn/0JF+MPvcgbh1lWdmSi9zOfxSPEEivrlJ2gkzv+Rqtn38Ie2Kh6j4w+lSdwGDYnd5sDsoXa8iOv8boD1gsGmCmOe9d16uKaVB9YTpolSiEGy/1Q5CsHOp2CsHvW6ASbt+pJrM328i+hpc6szrsBJybvLRAgMBAAECgYB2iUi0LUx/kQoiyYB86kjxGqOrvLEHJlUoTav0rXSZPp3bs+bf/26sCRVxTNPMup3S2GAXpMpxFOnhbvgslXo3Aolqh03WxtWu+pSUs1jZmW/enAdfYvLEuX9EHidDB4XkvIHVpeE7T4L4LYhc/l3HsM93fky/z3SVoFq7zabrtQJBAPHntYH5Z/6RW7nXGEa8aPikME9eGxeWVOS4qRqKpdjp3G7SrI3XojB+dtz80BbSqf6BHn7VnWaZXCDsqAdj7z8CQQDTbBYTQTArUlL/l0vFHg3fchFAh+DQzVT6MDOnas2XoJu4hx4xARl2a5y5CtFNvog1veLKSSu2svLRHuLSTqnvAkAcgLrIR8TTH/l42jlIDGcp9N6kW2hBzTrPgFqcf/2uo0+P107xn5jCsgP7YeZ66fORw1D+jNjw/9z1HC1oQYQtAkBPMRlDtRM55ug33JABEbTYkX1s0nifPYoq/IsclqDTvtEVWWcxq9vBw6U8mpSzrj6PAsVESAwbrwPM2OjVJan5AkEAjRuPCLR1rTNoKEJevzC3tfeabtO0pd8D4DRQtAkPpnOsAlRs/vYTewwXV9TmgN1A3bnAdSNrexOAC7pZ/g0aNA==',
	  rsaPubKey:'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB',
	  notifyURL:notifyURL
	},function(ret,err) {
	  
	});

	obj.pay({
	    subject:subject,
	    body:body,
	    //amount:amount,
	    amount: 0.01,
	    tradeNO:tradeNO
	},function(ret,err) {
		if(ret.statusCode=='9000' ){
			alert('支付成功');
			addChargeRecord(addMonth,amount);
		}else{
			blockAlert(ret.msg);
		}
	});

	
}
    
function selectBuyMonth(month,id){
	addMonth = month;
	amount = $('#'+id).text();
	$('#info img').css('display','none');
	$('#month'+month).css('display','block');
}

function selectPayWay(payway){
	$('#zhifu .gou').css('display','none');
	$('#'+payway).css('display','block');
}
function getFeeInfo(){
	YW.ajax({
		url: 'http://'+server_host+'/c/mobile/user/feeInfo',
		method:'post',
		cache:false,
		returnAll:false
	},function(ret , err){
		if(ret){
			//默认季付
			amount= ret.seasonPay;
			$('#monthPay').text(ret.monthPay);
			$('#seasonPay').text(ret.seasonPay);
			$('#yearPay').text(ret.yearPay);
		}
	});
}
function addChargeRecord(monthAdd , fee , tradeNO){
	addingRecord=true;
	if(!tradeNO){
		tradeNO = new Date().getTime();
	}
	YW.ajax({
		url: 'http://'+server_host+'/c/mobile/user/paySuccess',
		method:'post',
		cache:false,
		timeout:20,
		data:{values:{
			monthAdd:monthAdd,userId:uid,uname:uname,tradeNO:tradeNO,fee:fee,payType:payType
		}},
		returnAll:false
	},function(ret , err){
		if(ret && ret.result){
			userInfo.mobileDeadtime = ret.mobileDeadtime;
			userInfo.fufei = ret.fufei;
			api.setPrefs({
	            key:'user',
	            value:JSON.stringify(userInfo)
            });
			
			//更新user页面的服务到期时间
			api.setPrefs({
			    key:'user',
			    value:JSON.stringify(userInfo)
		    });
			api.execScript({
			    name: 'user',
			    script: 'updateDeadtime('+uid+');'
			});
			//到支付成功页面
			api.openWin({
		        name: 'payOK',
				//url: 'order.html',
		        pageParam: {amount: amount,paytime: ret.paytime},
				bounces: true,
				url: 'payOK.html',
		        bgColor: '#fff'
		    });
		    addingRecord=false;
			closexx();
		}else{
			alert('重试同步数据..请勿操作');
			setTimeout(function(){
				addChargeRecord(monthAdd , fee , tradeNO);
			},1000);
		}
	});
}