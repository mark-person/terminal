
var AXIOS_CONFIG = {
	autoLoading:true
};

var loading = {
	show:function() {},
	hide:function() {}
	
}

axios.interceptors.request.use(function (config) {
	if (AXIOS_CONFIG.autoLoading) loading.show();

	
	// 改用config.baseURL = contextPath;
	config.url = contextPath + config.url;
	
	if (config.headers["Content-Type"] && config.headers["Content-Type"].indexOf("multipart") >= 0) {
		return config;
	}
	config.transformRequest=[function (data) {
		if (typeof data == "object")  return  Qs.stringify(data, {arrayFormat:'repeat'});
         return data;
	}]
	return config;
}, function (error) {
	if (AXIOS_CONFIG.autoLoading) loading.hide();
	return Promise.reject(error);
});

axios.interceptors.response.use(function(res) {
	
	
	if (AXIOS_CONFIG.autoLoading) loading.hide();
	
	var code = res.data.code;
	if (code != 0) {
		alert(res.data.msg);
	}
	return res.data;
}, function(error) {
	if (AXIOS_CONFIG.autoLoading) loading.hide();
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


var common = {};
common.listDict = function(code) {
	if (!dict[code]) {alert("没有找到数据字典:" + code);return;}

	var disabled = dict[code + "_disabled"];
	if (!disabled) return dict[code];
	
	var newDict = {};
	for (i in dict[code]) {
		if (disabled.split(",").indexOf(i) == -1) {
			newDict[i] = dict[code][i];
		}
	}
	return newDict;
}

// 改成在axio中初始化
function initLoading() {
	document.body.insertAdjacentHTML("beforeend",'<div id="loading"><loading v-if="showLoading"></loading></div>')
	
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
}
 
function page(url, list) {
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
	    	sort:function(orderName, orderType) {
	    		orderType = (orderType == 'asc' ? 'desc' : 'asc');
	    		this.sortType[orderName] = orderType;
	    		this.p.orderName = orderName;
	    		this.p.orderType = orderType;
	    		this.goto();
	    	},
	    	goto:function(n) {
	    		this.p.pageNumber = n ? n : 1;
	    		this.p.pageSize = this.page.pageSize;
	    		loading.show();
	            axios.post(url, this.p).then(function(res) {
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



