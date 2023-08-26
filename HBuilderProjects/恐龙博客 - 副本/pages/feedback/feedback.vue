<template>
	<view class="content">
		<view class="formPost">
			<u-form :model="user" ref="uForm" label-position="top">
				<u-form-item label="反馈内容">
					<u-input v-model="user.content" type="textarea" placeholder="请输入反馈内容"/>
				</u-form-item>
				<u-form-item label="用户名">
					<u-input v-model="user.name" placeholder="请输入用户名"/>	
				</u-form-item>
			</u-form>
			<u-button @click="submit" >提交</u-button>
		</view>

	</view>
</template>
<script>

	export default {
		data() {
			return {
				user:{
					name:'',
					content:'',
					userid:uni.getStorageSync("user").userid
				},
				
			}
		},

		methods: {
			submit(){
				console.log("提交")
				//网络提交数据
				uni.request({
					// 地址
					url: "http://localhost:8080/complain",
					// 请求方式
					method:"POST",
					// 请求发送的数据
					data:this.user,
					// 接收响应
					success:(res)=>{
						if(res.data.code *1==200){
							uni.navigateBack()
						}else{
							this.$u.toast('反馈失败')  //提示框
						}
					}
				})
				
			}
		}
	}
</script>

<style>
	.formPost {
		padding: 0 30rpx;
	}
</style>
