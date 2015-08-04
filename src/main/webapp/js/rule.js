var Rule=function(patten , func){
	this.patten = patten;
	this.func = func;
	this.matchAndCacu = function(tokenList){
		var reg = new RegExp(patten , 'g');
		var str = tokenList.toTokenStr();
		var result = reg.exec(str);
		if(result==null){
			return tokenList;
		}
		var left  = str.substr(0,result.index);
		//计算匹配到的元素在tokenlist 中下标的起始位置
		var startIndex = left.split(',').length-1;
		//计算sub token list
		var pattenLength = patten.split(',').length;
		var subTokenList =tokenList.subList(startIndex, pattenLength);
		//稍后考虑多匹配
		var newToken = func(subTokenList);
		tokenList = tokenList.replace(startIndex , pattenLength , newToken);
		return tokenList;
	};
}

function Token(cx , val , startPos){
	this.cx  = cx;
	//val 可以是str,也可以是复杂object
	this.val = val;
	//排序使用
	this.startPos = startPos;
}

function TokenPatten(name,patten){
	this.name = name;
	this.patten = patten;
}

function TokenList(){
	this.tokens =[];
	this.push=function(token){
		this.tokens.push(token);
	};
	this.toTokenStr = function(){
		var result = '';
		for(var i=0;i< this.tokens.length;i++){
			result+= this.tokens[i].cx;
			//最后一个不加,
			if(i!=this.tokens.length-1){
				result+=',';
			}
		}
		return result;
	};
	this.subList = function(start , length){
		var result = new TokenList();
		for(var i=0;i<length;i++){
			result.push(this.tokens[start+i]);
		}
		return result;
	};
	this.replace = function(start,length,newToken){
		var result = new TokenList();
		for(var i=0;i<start;i++){
			result.push(this.tokens[i]);
		}
		result.push(newToken);
		for(var i=start+length;i<this.tokens.length;i++){
			result.push(this.tokens[i]);
		}
		return result;
	};
}