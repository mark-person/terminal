function page(list, url) {
	var page = new Vue({
	    el:'#page',
	    data:{
	        pojo:{pageSize:3},
	        list:list.list,
	        sortType:[],
	        totalNum:Math.ceil(list.page.totalRows/list.page.pageSize),
	    	totalRows:list.page.totalRows,
	    	pageNumber:list.page.pageNumber
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
	    		loading.show();
	            axios.post(contextPath + url, Qs.stringify(this.pojo)).then(function(res) {
	            	loading.hide();
	            	var r = res.data;
	            	page.list = r.list;
	            	page.totalNum = Math.ceil(r.page.totalRows/r.page.pageSize);
	            	page.totalRows = r.page.totalRows;
	            	page.pageNumber = r.page.pageNumber;
	            });
	    	}
	    }
	});
	
	document.querySelector(".tableTemplate").style.display = "table";
	document.querySelector(".blockTemplate").style.display = "block";
	
}


