
axios.interceptors.request.use(function (config) { 
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
	        param:{},
	        list:list.list,
	        sortType:[],
	        p:list.page
	    },
	    methods: {
	    	sortPage:function(orderName, orderType) {
	    		orderType = (orderType == 'asc' ? 'desc' : 'asc');
	    		this.sortType[orderName] = orderType;
	    		this.param.orderName = orderName;
	    		this.param.orderType = orderType;
	    		this.gotoPage();
	    	},
	    	gotoPage:function(n) {
	    		this.param.pageNumber = n ? n : 1;
	    		this.param.pageSize = this.p.pageSize;
	    		loading.show();
	            axios.post(contextPath + url, this.param).then(function(res) {
	            	loading.hide();
	            	page.list = res.list;
	            	page.p = res.page;
	            });
	    	}
	    }
	});
	return page;
}

function modal(id, okFun, validateFun) {
	var m = new Vue({
		el: '#' + id,
		components: {'modal': httpVueLoader(contextPath + 'static/vue/template/modal.vue'), props: {param:Object, modal:Object}},
		data: {
	      	modal:{
	      		showModal:false,title:'',width:'500px',showOk:typeof okFun == "function",
	      		ok:function() {if (!m.isValid) return;okFun(m.param);}
	      	},
	      	param:{}
		},
		computed: {
			v:function() {
				if (typeof validateFun == "function") return validateFun(this.param);
			},
			isValid:function () {
				for (o in this.v) {
					if (this.v[o] != "") return false;
				}
				return true;
			}
		},
		methods: {
			show:function(title, param) {
	      		this.modal.title = title;
	      		this.param = param ? param : {};
	      		this.modal.showModal = true;
	      	},
	      	hide:function() {
	      		this.modal.showModal = false;
	      	}
		}
	})
	return m;
}




