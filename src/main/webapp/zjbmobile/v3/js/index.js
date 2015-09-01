var urlPrefix="";
var header,
	headerPos,
	main,
	mainPos,
	wrapPos;
	var ajpush;
    apiready = function(){
		
    	api.clearCache(
		    function(ret,err){
		        alert('clear cache');
		    }
		);

		//blockAlert(server_host);
		getCityInfo(function(city){
			if(!city || !city.cityPy){
				api.openWin({
				    name: 'citys',
				    pageParam:{parentPage:'root',title:'选择城市',pageName:'citys'},
					url: urlPrefix+'html/wrap.html'
				});
			}
		});
		
		
		getUserInfo(function(user){
    		if(user){
    			//全局变量
//  			ajpush = api.require('ajpush');
//  			
////		     blockAlert(ajpush);
//  			if(api.systemType=='ios'){
//  				bindAjpush();
//  			}else{
//  				ajpush.init(function(ret) {
//	    				if (ret && ret.status){
//	    					bindAjpush();
//	    				}
//	    			});
//  			}
				//用于第二次点我的页面时不用重新登录
    			user.online=false;
    			user.fufei = false;
    			api.setPrefs({
	                key:'user',
	                value:JSON.stringify(user)
                });
    		}
    	});
		
        header = $api.byId('header');
        $api.fixIos7Bar(header); 
        headerPos = $api.offset(header);
        main = $api.byId('main');
        mainPos = $api.offset(main);
		wrapPos = $api.offset($api.byId('wrap'));
		
        api.openFrame({
            name: 'houses',
//          url: 'http://mobile.zjb.tunnel.mobi/weixin/houseOwner/houses.jsp',
//			url: urlPrefix+'html/houses.html',
			url: urlPrefix+'v3/house.html',
            bounces: false,
            rect: {
                x: mainPos.l+1,
                y: headerPos.h,
                w: headerPos.w,
                h: mainPos.h
            }
        });
        
//      var baiduLocation = api.require('baiduLocation');
//	    baiduLocation.getLocation(
//	        function (ret, err) {
//	            if (ret.status) {
//		            navigator.geolocation.getCurrentPosition(function(option){
//		            	blockAlert(JSON.stringify(option));
//		            },function(){},function(){});
//	            } else {
//	                api.alert({ msg: err.msg });
//	            }
//	        }
//	    );

		
    };
    
    function setCity(){
    	//空实现即可
    }
    
    function bindAjpush(){
    	var param = {alias: user.tel};
		ajpush.bindAliasAndTags(param,function(ret) {
		     blockAlert('statuscode = '+ret.statusCode+'绑定到'+param.alias);
		});
	
		ajpush.setListener(function(ret) {
			if(api.deviceId!=ret.content){
				blockAlert('您的账号在别处登录,您在此处将下线');
				api.setPrefs({
                    key:'user',
                    value:''
                });
			}
	    });
	    
    }
    
    function openThis(name,url,obj){
    	$(obj).parent().find('.item').removeClass('active');
    	$(obj).addClass('active');
    	$('.headNav').css('display','none');
    	$('#'+name).css('display','');
    	var y=headerPos.h,h=mainPos.h;
        api.openFrame({
            name: name,
			//url: urlPrefix+'html/'+a+'.html',
//			url: urlPrefix+'v3/'+a+'.html',
            url: url,
            bounces: true,
            bgColor: '#fff',
            rect: {
                x: mainPos.l+1,
                y: y,
                w: headerPos.w,
                h: h
            }
        });
    }
    
    function openUserInfo(){
    	getUserInfo(function(user){
    		if(user && user.online){
    			api.openWin({
			        name: 'user',
			        pageParam: {pageName: 'user',title:'我的'},
					url: urlPrefix+'html/wrap.html',
					delay:300,
			        bgColor: '#fff'
			    });
    		}else{
			    api.openWin({
		            name: 'login',
		            pageParam: {pageName: 'login',title:'登录'},
					url: urlPrefix+'v3/login.html',
					bounces: true,
					scaleEnabled:true,
		            bgColor: '#fff'
		        });
    		}
	        
    	});
    	
    }
    
    
    function openSearchPanel(){
    	api.openWin({
		    name: 'search',
		    bounces: false,
		    softInputMode:'resize',
			url: urlPrefix+'html/search.html'
		});
    }
    
    function refreshHouses(){
//  	alert(222);
    	api.execScript({
		    frameName: 'houses',
		    script: 'refreshPage();'
		});
    }