
axios.interceptors.response.use(function(res) {
	var code = res.data.code;
	if (code != 0) {
		alert(res.data.msg);
	}
	return res;
}, function(error) {
	return Promise.reject(error);
});


var V = {};
V.notNull = function(v) {
	return !v ? '必填' : '';
}
V.isNum = function(v) {
	return /^[0-9]+$/.test(v) ? '' : '必须为数字';	
}

function page(list, url) {
	page = new Vue({
	    el:'#page',
	    data:{
	        pojo:{},
	        list:list.list,
	        sortType:[],
	        p:list.page
	    },
	    methods: {
	    	sortPage:function(orderName, orderType) {
	    		orderType = (orderType == 'asc' ? 'desc' : 'asc');
	    		this.sortType[orderName] = orderType;
	    		this.pojo.orderName = orderName;
	    		this.pojo.orderType = orderType;
	    		this.gotoPage(1);
	    	},
	    	gotoPage:function(n) {
	    		this.pojo.pageNumber = n;
	    		this.pojo.pageSize = this.p.pageSize;
	    		loading.show();
	            axios.post(contextPath + url, Qs.stringify(this.pojo)).then(function(res) {
	            	loading.hide();
	            	page.list = res.data.list;
	            	page.p = res.data.page;
	            });
	    	}
	    }
	});
	
	document.querySelectorAll(".tableTemplate").forEach(function(o, i) {o.style.display = "table";})
	document.querySelectorAll(".blockTemplate").forEach(function(o, i) {o.style.display = "block";})
}

function modal(id, validateFun, okFun) {
	Vue.component(id, {template: '#modal-template', props: {pojo:Object, validate:Object}});
	var m = new Vue({
		el: '#' + id,
		data: {
	      	showModal:false,
	      	pojo:{}
		},
		computed: {
			validate:function() {
				return validateFun(this.pojo);
			},
			isValid:function () {
				for (o in this.validate) {
					if (this.validate[o] != "") return false;
				}
				return true;
			}
		},
		methods: {
	      	ok:function() {
	      		if (!this.isValid) return;
	      		if (typeof okFun == "function") okFun(this.pojo);
	      		else {
	      			loading.show();
	      			var self = this;
	      			axios.post(contextPath + okFun, Qs.stringify(this.pojo)).then(function(res) {
	      				loading.hide();
	      		     	if (res.data.code == 0) {
	      		     		self.showModal = false;
	      		     		page.gotoPage(1);
	      		     	}
	      			})
	      		}
	      	}
		}
	})
	return m;
}

function initModal(url, id, obj) {
	loading.show();
	axios.post(contextPath + "demo/get", "id=" + id).then(function(res) {
		loading.hide();
		if (res.data.code == 0) {
			obj.pojo = res.data.pojo;
			obj.showModal = true;
		}
	})
}




