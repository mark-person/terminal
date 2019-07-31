

axios.interceptors.response.use(function(res) {
	var code = res.data.code;
	alert(code)
	if (code != 0) {
		alert(res.data.msg);
	}
	return res;
}, function(error) {
	return Promise.reject(error);
});


var V = {};
V.notNull = function(v) {
	return !v ? '不能为空' : '';
}
V.isNum = function(v) {
	return /^[0-9]+$/.test(v) ? '' : '必须为数字';	
}


function page(list, url) {
	var page = new Vue({
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
	
	// document.querySelector(".tableTemplate").style.display = "table";
	// document.querySelector(".blockTemplate").style.display = "block";
}


