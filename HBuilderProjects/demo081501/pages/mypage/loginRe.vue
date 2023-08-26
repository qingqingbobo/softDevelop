<template>
	<view class="u-margin-left-26 u-margin-right-26">
		<u-form ref="uForm" :model="user">
			<u-form-item left-icon="account" label="姓名" label-width="120" prop="zhanghao">
				<u-input type="text" placeholder="请输入姓名" v-model="user.zhanghao"></u-input>
			</u-form-item>
			<u-form-item left-icon="lock" label="密码" label-width="120" prop="mima">
				<u-input type="password" placeholder="请输入密码" :password-icon="passwordicon" v-model="user.mima"></u-input>
			</u-form-item>
			<u-form-item label="记住密码" label-width="150">
				<u-switch slot="right"></u-switch>
			</u-form-item>
<!-- 			<u-form-item label="性别" >
				<u-input type="select" placeholder="请选择性别" border=true v-model="sex" @click="show = true" />
			</u-form-item> -->
		</u-form>
		
<!-- 		<u-action-sheet :list="actionSheetList" v-model="show" @click="actionSheetCallback"></u-action-sheet> -->

		<u-button @click="submit">提交</u-button>
		<!-- 注册按钮 -->
		<u-button @click="register">注册</u-button>
		
		<view class="u-demo-area">
			<u-link :color="color" :font-size="fontsize" :href="href" > 忘记密码 </u-link>
		</view>
		
<!-- 		<view class="u-demo-area">
			<u-link :color="color" :font-size="fontsize" :href="href" :under-line="underline">注册</u-link>
		</view> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				passwordicon: false,
				color: "#FF00FF",
				fontsize: 44,
				href: "http://localhost:8081/#/pages/mypage/resetpwd",
				underline: true,
				user: {
					zhanghao: '',
					mima: '',
					// sex: ''
				},
				// show: false,
				// actionSheetList: [
				// 					{
				// 						text: '男'
				// 					},
				// 					{
				// 						text: '女'
				// 					},
				// 					{
				// 						text: '保密'
				// 					}
				// 				],
				rules: {
					zhanghao: [
						{
							required: true,
							message: "请输入姓名",
							trigger: ['change', 'blur'],
						}
					],
					mima: [
						{
							required: true,
							message: "请输入密码",
							trigger: ['change', 'blur'],
						}
					]
				}
			}
		},
		
		methods: {
			submit() {
				// 打印
				console.log("登录");
				
				uni.request({
					url: "http://localhost:8080/doLogin",
					method: "POST",
					data: this.user,
					success: (res) => {
						console.log(res);
					}
				})
				
				// if success
				uni.navigateBack()
			},
			// 点击actionSheet回调
			actionSheetCallback(index) {
				this.user.sex = this.actionSheetList[index].text;
			},
			
			register() {
				console.log("注册");
				// 跳转到注册页面
				uni.navigateTo({
					url: "/pages/mypage/registerRe"
				})
			}
		},
		
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		}
	}
</script>

<style>
	.u-demo-area {
		cursor: pointer;
	}
</style>