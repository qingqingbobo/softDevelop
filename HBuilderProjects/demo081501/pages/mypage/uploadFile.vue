<template>
	<view>
		<u-upload ref="uUpload" :action="action" :auto-upload="false" :form-data="upFileData" ></u-upload>
		<u-button @click="submit">提交</u-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 非真实地址
				action: 'http://localhost:8080/upload',
				upFileData: {
					id: 2,
					type: 'number'
				}
			}
		},
		methods: {
			submit() {
				this.$refs.uUpload.upload();
			},
		}
	}
</script>
