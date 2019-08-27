
axios.interceptors.request.use(function (config) {
	if (config.url.indexOf("uploadImg") > -1) {
		return config;
	}
	config.transformRequest=[function (data) {
		if (typeof data == "object")  return  Qs.stringify(data, {arrayFormat:'repeat'});
         return data;
      }]
      return config;
}, function (error) {
     return Promise.reject(error);
});

axios.interceptors.response.use(function(res) {
	var code = res.data.code;
	if (code != 0) {
		alert(res.data.msg);
	}
	return res.data;
}, function(error) {
	return Promise.reject(error);
});


var V = {};
V.notNull = function(v) {
	return v === '' || v === undefined ? '必填' : '';
}
V.isInt = function(v) {
	if (this.notNull(v) != '') return '';
	return /^[0-9]+$/.test(v) ? '' : '必须为整数';	
}
V.isNum = function(v) {
	if (this.notNull(v) != '') return '';
	return isNaN(v) ? '必须为数字' : '';
}

function initLoading() {
	loading = new Vue({
		el: '#loading',
		components: {'loading': httpVueLoader(contextPath + 'static/vue/template/loading.vue')},
		data: {
			showLoading: false,
	      	delayLoading: true
		},
		methods: {
	      	show:function() {
	      		this.delayLoading = true;
				setTimeout(function() {loading.showLoading = (loading.delayLoading) ? true : false;}, 300);
	      	},
	      	hide:function() {
	      		this.delayLoading = false;
	      		loading.showLoading = false;
	      	}
		}
	})
	
	document.querySelectorAll(".block-template").forEach(function(o, i) {o.style.display = "block";})
}
 
function page(list, url) {
	initLoading();
	var page = new Vue({
	    el:'#page',
	    data:{
	        p:{},
	        list:list.list,
	        sortType:[],
	        page:list.page
	    },
	    methods: {
	    	sortPage:function(orderName, orderType) {
	    		orderType = (orderType == 'asc' ? 'desc' : 'asc');
	    		this.sortType[orderName] = orderType;
	    		this.p.orderName = orderName;
	    		this.p.orderType = orderType;
	    		this.gotoPage();
	    	},
	    	gotoPage:function(n) {
	    		this.p.pageNumber = n ? n : 1;
	    		this.p.pageSize = this.p.pageSize;
	    		loading.show();
	            axios.post(contextPath + url, this.p).then(function(res) {
	            	loading.hide();
	            	page.list = res.list;
	            	page.page = res.page;
	            });
	    	}
	    }
	});
	return page;
}

function modal(id, okFun, validateFun) {
	var m = new Vue({
		el: '#' + id,
		components: {'modal': httpVueLoader(contextPath + 'static/vue/template/modal.vue'), props: {p:Object, modal:Object, firstList:Array}},
		data: {
	      	modal:{
	      		showModal:false,title:'',width:'500px',showOk:typeof okFun == "function",
	      		ok:function() {if (!m.isValid) return;okFun(m.p);}
	      	},
	      	p:{}
		},
		computed: {
			v:function() {
				if (typeof validateFun == "function") return validateFun(this.p);
			},
			isValid:function () {
				for (o in this.v) {
					if (this.v[o] != "") return false;
				}
				return true;
			}
		},
		methods: {
			show:function(title, p) {
	      		this.modal.title = title;
	      		this.p = p ? p : {};
	      		this.modal.showModal = true;
	      	},
	      	hide:function() {
	      		this.modal.showModal = false;
	      	}
		}
	})
	return m;
}



function formatJson(txt, compress){
    var indentChar = '    ';   
    if(/^\s*$/.test(txt)){   
        alert('数据为空,无法格式化! ');   
        return;   
    }   
    try{var data=eval('('+txt+')');}   
    catch(e){   
        alert('数据源语法错误,格式化失败! 错误信息: '+e.description,'err');   
        return;   
    };   
    var draw=[],last=false,This=this,line=compress?'':'\n',nodeCount=0,maxDepth=0;   
       
    var notify=function(name,value,isLast,indent,formObj){   
        nodeCount++; 
        for (var i=0,tab='';i<indent;i++ )tab+=indentChar;  
        tab=compress?'':tab;  
        maxDepth=++indent;
        if(value&&value.constructor==Array){  
            draw.push(tab+(formObj?('"'+name+'":'):'')+'['+line); 
            for (var i=0;i<value.length;i++)   
                notify(i,value[i],i==value.length-1,indent,false);   
            draw.push(tab+']'+(isLast?line:(','+line)));  
        }else   if(value&&typeof value=='object'){ 
                draw.push(tab+(formObj?('"'+name+'":'):'')+'{'+line);  
                var len=0,i=0;   
                for(var key in value)len++;   
                for(var key in value)notify(key,value[key],++i==len,indent,true);   
                draw.push(tab+'}'+(isLast?line:(','+line)));  
            }else{   
                    if(typeof value=='string')value='"'+value+'"';   
                    draw.push(tab+(formObj?('"'+name+'":'):'')+value+(isLast?'':',')+line);   
            };   
    };   
    var isLast=true,indent=0;   
    notify('',data,isLast,indent,false);   
    return draw.join('');   
}


