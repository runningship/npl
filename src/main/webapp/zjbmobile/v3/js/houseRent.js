var currentPage=1;
	var totalPageCount;
	var fufei;
	var searchParams = JSON.parse('{}');
	function loadData(clear){
		YW.ajax({
			url:'http://'+server_host+'/c/mobile/rent/list?page='+currentPage,
			method:'post',
			cache:false,
			data:{values:searchParams},
			returnAll:false
		},function(ret , err){
			if(ret){
				totalPageCount = ret.page.totalPageCount;
					if(ret.page.data.length==0){
						$('#noResultMsg').css('display','block');
					}else{
						$('#noResultMsg').css('display','none');
					}
				if(clear){
					buildHtmlWithJsonArray('repeat',ret.page.data , false,false);
				}else{
					buildHtmlWithJsonArray('repeat',ret.page.data , false,true);
				}
				api.parseTapmode();
			}else{
			}
		});
	}
	
	function getRentType(code){
		if(code=='1'){
			return '整租';
		}else if(code=='2'){
			return '合租';
		}else{
			return code;
		}
	}
	
	function setSearchParamsAndSearch(params){
		searchParams = JSON.parse(params);
		loadData(true);
	}
	
	function SeeThis(id){
		if(fufei){
			api.openWin({
			    name: 'infoRentJJR',
			    pageParam: {id: id},
			    delay:300,
				url: '../html/infoRentJJR.html'
			});
		}else{
			api.openWin({
		        name: 'infoRent',
		        pageParam: {pageName: 'infoRent',title:'详情' , id:id},
				url: '../html/wrap.html',
				delay:300,
		        bgColor: '#fff'
		    });
		}
		$('#'+id).css('color','#999');
     }
	
		apiready = function(){
			getUserInfo(function(user){
				if(user){
					fufei = user.fufei;
				}
			});
			api.setRefreshHeaderInfo({
				loadingImg: 'widget://image/loading.jpg'
				//bgColor:'white'
				},
				function(ret,err){
		    	api.refreshHeaderLoadDone();
		    	buildHtmlWithJsonArray('repeat',[] , false,false);
		    	currentPage=1;
		    	loadData(true);
			});
			
			api. addEventListener({name:'scrolltobottom'}, 
				function(ret, err){
					//设置提示信息
					if(currentPage<totalPageCount){
						currentPage++;
					}else{
						alert('已是最后一页');
						return;
					}
					setTimeout(loadData,100);
				}
			);
			
			loadData();
		};

function isFufei(){
	return fufei;
}

function refreshPage(){
	window.location.reload();
}