<template>
	<view class="wrap">
		<u-form :model="user" ref="uForm">
			<u-form-item label-width="140" label-position="left" label="姓名" prop="xingming">
				<u-input :border="border" placeholder="请输入姓名" v-model="user.xingming" type="text"></u-input>
			</u-form-item>
			<u-form-item label-width="140" label-position="left" label="用户名" prop="zhanghao">
				<u-input :border="border" placeholder="请输入用户名" v-model="user.zhanghao" type="text"></u-input>
			</u-form-item>
			<u-form-item label-width="140" label-position="left" label="密码" prop="mima">
				<u-input :password-icon="true" :border="border" type="password" v-model="user.mima" placeholder="请输入密码"></u-input>
			</u-form-item>
			<u-form-item label-width="140" label-position="left" label="确认密码" prop="rmima">
				<u-input :password-icon="true" :border="border" type="password" v-model="user.rmima" placeholder="再输一次密码"></u-input>
			</u-form-item>
			<u-form-item label-width="140" label-position="left" label="年龄" prop="age">
				<u-input :border="border" placeholder="请输入年龄" v-model="user.age" type="text"></u-input>
			</u-form-item>
			<u-form-item label-width="140" label-position="left" label="用户类别" prop="leibie">
				<u-radio-group v-model="user.leibie" @change="radioGroupChange">
					<u-radio 
						@change="radioChange" 
						v-for="(item, index) in list" :key="index" 
						:name="item.val"
						:disabled="item.disabled" >
						{{item.txt}}
					</u-radio>
				</u-radio-group>
			</u-form-item>
		</u-form>
		<u-button @click="submit">提交</u-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				border: true,
				user: { },
				list:[{
					val: '1',
					txt:"超级用户",
					disabled: false
				},
				{
					val: '2',
					txt:"普通用户",
					disabled: false
				},
				{
					val: '3',
					txt:"电影推介",
					disabled: false
				}]
			}
		},
		
		methods: {
			submit() {
				console.log(this.user);
				
				// 验证两次输入的密码是否相同
				if (this.user.mima != this.user.rmima) {
					uni.$u.toast("两次输入的密码不一致，请重新输入！");
					this.$refs.uForm.resetFields();
					return
				}
				
				uni.request({
					url: "http://localhost:8080/doRegister",
					method: "POST",
					data: this.user,
					
					success: (res) => {
						console.log(res);
						
						if (res.data.code * 1 == 200) {
							uni.navigateTo({
								url: "/pages/mypage/login"
							});
						}
						else {
							uni.setStorageSync(this.user, "");
							console.log(this.user);
							uni.$u.toast("用户信息错误！");
							this.$refs.uForm.resetFields();
						}
					}
				})
				
			},

			radioChange(e) {
				//console.log(e.detail)
				console.log(e,"radio",this.user.leibie)
				//this.user.leibie = e.detail.value
				},
			
			radioGroupChange(e){
				console.log(e,"group",this.user.leibie)
				}
		}
	}
	
 </script>
 
 <style>
 </style>