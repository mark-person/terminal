
function formatJson(txt, compress){
    var indentChar = '    ';   
    if(/^\s*$/.test(txt)){   
        alert('数据为空,无法格式化! ');   
        return;   
    }
    try{
    	var data = eval('(' + txt + ')');
    }   
    catch(e){   
        alert('数据源语法错误,格式化失败! 错误信息: ' + e.description, 'err');   
        return;   
    };   
    var draw = [], last = false, This = this, line = compress ? '' : '\n', nodeCount = 0, maxDepth = 0;   
       
    var notify = function(name, value, isLast, indent, formObj) {   
        nodeCount++; 
        for (var i=0, tab=''; i<indent; i++) tab += indentChar;  
        tab = compress ? '' : tab;
        maxDepth = ++indent;
        if (value && value.constructor == Array) {  
            draw.push(tab + (formObj ? ('"' + name + '":') :'') + '[' + line); 
            for (var i = 0 ;i < value.length; i++)   
                notify(i,value[i],i == value.length-1, indent, false);   
            draw.push(tab+']' + (isLast ? line:(',' + line))); 
        } else if (value && typeof value == 'object') { 
                draw.push(tab + (formObj ? ('"' + name + '":') : '') + '{' + line);  
                var len = 0, i = 0;   
                for(var key in value) len++;   
                for(var key in value) notify(key,value[key], ++i == len, indent, true);   
                draw.push(tab+'}' + (isLast?line:(','+line)));  
         } else {   
          	if (typeof value=='string') value='"' + value + '"';   
            draw.push(tab + (formObj?('"'+ name + '":'):'') + value + (isLast ? '' : ',') + line);   
    	};   
    };   
    var isLast = true, indent = 0;   
    notify('', data, isLast, indent, false);   
    return draw.join('');   
}


function replaceSelection(id, text) {
	var editor = document.getElementById(id);
	if (!editor.setSelectionRange) return;
	
	var selectionStart = editor.selectionStart;
	var selectionEnd = editor.selectionEnd;
	var selectStr = editor.value.substring(selectionStart, selectionEnd);
	if (selectStr && selectStr.substring(selectStr.length - 1) == " ") {
		text += " ";
	}
	var leftStr = editor.value.substring(0, selectionStart);
	var rightStr = editor.value.substring(selectionEnd, editor.value.length);
	var selectStr = editor.value.substring(selectionStart, selectionEnd)
	editor.value = leftStr + text + rightStr;
    editor.focus();
}
