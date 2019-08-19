


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

var Dict = {};
Dict.getDictVal = function(id, val) {
	var option = document.getElementById(id).querySelectorAll("option");
	for (var i = 0; i < option.length; i++) {
		if (option[i].value == val) {
			return option[i].text;
		}
	}
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

	document.querySelectorAll(".table-template").forEach(function(o, i) {o.style.display = "table";})
	document.querySelectorAll(".block-template").forEach(function(o, i) {o.style.display = "block";})
}
 
function page(list, url) {
	initLoading();
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
}

function modal(id, validateFun, okFun) {
	var m = new Vue({
		el: '#' + id,
		components: {'modal': httpVueLoader(contextPath + 'static/vue/template/modal.vue'), props: {pojo:Object, validate:Object, modal:Object}},
		data: {
	      	modal:{showModal:false,title:'',width:'500px'},
	      	pojo:{}
		},
		computed: {
			v:function() {
				if (typeof validateFun == "function") return validateFun(this.pojo);
			},
			isValid:function () {
				for (o in this.v) {
					if (this.v[o] != "") return false;
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
	      		     		self.modal.showModal = false;
	      		     		page.gotoPage(1);
	      		     	}
	      			})
	      		}
	      	}
		}
	})
	return m;
}

function editModal(title, obj, url, id) {
	loading.show();
	axios.post(contextPath + "demo/get", "id=" + id).then(function(res) {
		loading.hide();
		if (res.data.code == 0) {
			obj.pojo = res.data.pojo;
			obj.modal.title = title;
			obj.modal.showModal = true;
		}
	})
}

function showModal(title, obj) {
	obj.modal.title = title;
	obj.pojo = {};
	obj.modal.showModal = true;
}


// >>>>>>>>>>>>>>>>>>...new...

function xModal(id, okFun, validateFun) {
	var m = newModal(id, okFun);
	// 存对象
	var computed = {
		v:function() {
			if (typeof validateFun == "function") return validateFun(this.pojo);
		},
		isValid:function () {
			for (o in this.v) {
				if (this.v[o] != "") return false;
			}
			return true;
		}
	}
}



function newModal(id, okFun) {
	var m = new Vue({
		el: '#' + id,
		components: {'modal': httpVueLoader(contextPath + 'static/vue/template/modal.vue'), props: {param:Object, validate:Object, modal:Object}},
		data: {
	      	modal:{
	      		showModal:false,title:'',width:'500px',showOk:typeof okFun == "function",
	      		ok:function(param) {okFun(param)}
	      	},
	      	param:{}
		},
		computed: {},
		methods: {
	      	showModal:function(title) {
	      		this.modal.title = title;
	      		this.modal.showModal = true;
	      	},
	      	hideModal:function() {
	      		this.modal.showModal = false;
	      	}
		}
	})
	return m;
}

