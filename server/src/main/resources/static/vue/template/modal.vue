

<template>
<div class="modal-mask">
<div class="modal-wrapper">
	<div class="modal-container" :style="'width:'+width">
		<div class="modal-header"><h3>{{title}}</h3></div>
		<div class="modal-body"><slot name="body"></slot></div>
		<div class="modal-footer">&nbsp;
			<div class="modal-default-button">
				<button @click="$emit('ok')" style="border:1px solid blue">确定</button>
				<button @click="$emit('close')">关闭</button>
			</div>
		</div>
	</div>
</div>
</div>
</template>


<script>
module.exports = {
	props: {modal},
    data: function() {
        return {
            title:this.modal.title,
            width:this.modal.width
        }
    }
}
</script>