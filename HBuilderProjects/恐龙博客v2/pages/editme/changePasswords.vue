<template>
	<view class="wrap u-margin-left-26 u-margin-right-26">
		<u-form :model="yonghu2" ref="uForm">
			<u-form-item label-width="140" label-position="left" label="原密码 :" prop="oripassword">
				<u-input :border="border" placeholder="请输入原密码" v-model="yonghu2.oripassword" type="password"></u-input>
			</u-form-item>
			<u-form-item label-width="140" label-position="left" label="新密码 :" prop="newpassword">
				<u-input :border="border" placeholder="请输入新密码" v-model="yonghu2.newpassword" type="password"></u-input>
			</u-form-item>
			<u-form-item label-width="140" label-position="left" label="确认密码 :" prop="rpassword">
				<u-input :password-icon="true" :border="border" type="password" v-model="yonghu2.rpassword"
					placeholder="再次填写确认"></u-input>
			</u-form-item>
			
		</u-form>
		<u-button type="success" shape="circle" @click="changePassWords">修改密码</u-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
				yonghu:{
					password:uni.getStorageSync("yonghu2").password,
				},
				
				yonghu2:{
					newpassword:'',
					oripassword:'',
					rpassword:'',
					userid: uni.getStorageSync("yonghu2").userid,
				},
				
				
				rules:{
					oripassword:[
						{
							required:true,
							message:"原密码不能为空",
							trigger:['change','blur']
						}
					],
					newpassword:[
						{
							required:true,
							message:"新密码不能为空",
							trigger:['change','blur']
						}
					]
				},
			}
		},
		
		
		onReady() {
			console.log("测试修改密码");
			this.$refs.uForm.setRules(this.rules);
		},
		
		methods: {
			//修改密码
			changePassWords: function() {
				console.log("测试提交按钮");
				console.log(this.yonghu2.oripassword);//打印原密码
				if (this.yonghu2.oripassword != this.yonghu.password){
					this.$u.toast("原密码输入错误");
				}else if (this.yonghu2.newpassword == ''){
					this.$u.toast("请输入新密码");
					
				}else{
					if(this.yonghu2.newpassword!=this.yonghu2.rpassword){
						//提示
						this.$u.toast("两次输入密码不同");
						//终止代码执行
						return 
					}else{
						uni.request({
							url:"http://localhost:8080/Editdata",
							data:{"password":this.yonghu2.newpassword, "userid":this.yonghu2.userid},
							method:"POST",
							success:(res)=>{
								console.log("我是分割线哈哈哈哈");
								console.log(res);
								//若code=200则注册成功，否则注册失败
								if(res.data.code*1==200){
									this.$u.toast("修改成功");
									
									uni.navigateBack()//返回个人主页界面
								}else{
									//提示登录失败
									this.$u.toast("修改失败")
								}
							}
						})
					}
				}
			
		}
	},
}
</script>

<style>
	

</style>
