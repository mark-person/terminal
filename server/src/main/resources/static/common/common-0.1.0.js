

$.ajaxSetup({
	error: function(r, textStatus, errorThrown) {
		alert("error:" + r.status + "|" + this.url);
	},
	complete: function(r, textStatus, errorThrown) {
	    var code = JSON.parse(r.responseText).code;
		if (code != 0) {
			alert(r.responseText);
		}
	}
});


