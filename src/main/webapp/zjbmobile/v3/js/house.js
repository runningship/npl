var currentPage=1;
	var totalPageCount;
	var fufei;
	function loadData(clear){
		YW.ajax({
			url:'http://'+server_host+'/c/mobile/list?page='+currentPage,
			method:'post',
			cache:false,
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
	
	function SeeThis(id){
		if(fufei){
			api.openWin({
			    name: 'infoJJR',
			    pageParam: {id: id},
			    delay:300,
				url: '../html/infoJJR.html'
			});
		}else{
			api.openWin({
		        name: 'info',
		        pageParam: {pageName: 'info',title:'详情' , id:id},
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
				//window.location.reload();
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