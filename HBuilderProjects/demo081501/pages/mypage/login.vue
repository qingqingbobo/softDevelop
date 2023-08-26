<template>
	<!-- 全局边距 -->
	<view class="u-margin-left-26 u-margin-right-26">
		<!-- 表单 -->
		<u-form ref="uForm" :model="user" >
			<!-- 用户名 -->
			<u-form-item left-icon='account' label="姓名" label-width='120' prop="zhanghao">
				<u-input type="text" placeholder="请输入姓名" v-model="user.zhanghao"></u-input>
			</u-form-item>
			<!-- 密码 -->
			<u-form-item left-icon='lock' label="密码" label-width='120' prop="mima">
				<!-- :password-icon is variable，not const 动态设置控件 -->
				<u-input type="password" placeholder="请输入密码" :password-icon="passwordIcon" v-model="user.mima"></u-input>
			</u-form-item>
			<!-- 记住密码 -->
			<u-form-item label="记住密码" label-width='150'>
				<u-switch slot="right"></u-switch>
			</u-form-item>
		</u-form>
		
		<!-- 提交按钮 -->
		<u-button @click="submit">提交</u-button>
		<!-- 注册按钮 -->
		<u-button @click="register">注册</u-button>
		
		<!-- 超链接 -->
		<!-- 显示小手指，需要自定义样式 -->
<!-- 		<view class="u-demo-area">
			<u-link :color="color" :font-size="fontsize" :href="href" under-line=true>注册</u-link>
		</view> -->
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 绑定，用 冒号
				passwordIcon: false,
				color: '#aa55ff',
				href: "http://localhost:8081/#/pages/mypage/register",
				underline: true,
				fontsize: 33,
				// form 表单关联
				user: {
					zhanghao: '',
					mima: '',
				},
				// 添加规则
				rules: {
					zhanghao: [
						{
							required: true,
							message: "请输入用户名",
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
		
		onLoad() {
			
		},
		
		// 方法
		methods: {
			// 点击事件
			submit() {
				// 打印
				console.log("登录");
				// 网络提交数据
				uni.request({
					// 地址
					url: "http://localhost:8080/doLogin",
					// 请求方式
					method: "POST",
					// 传递的参数
					data: this.user,
					// 查看返回的数据
					success: (res) => {
						console.log(res);
						// 登录成功，code = 200 -> 保存用户信息，返回个人中心
						if (res.data.code * 1 == 200) {
							// 保存数据在 App 端
							uni.setStorageSync("user", res.data.result);
							uni.navigateBack();
						}
						// 登录失败，提示用户名或者密码错误
						else {
							this.$u.toast("登录失败，用户名或者密码错误！");
							this.$refs.uForm.resetFields();
						}
						

					}
				})
				
			},
			
			register() {
				console.log("注册");
				// 跳转到注册页面
				uni.navigateTo({
					url: "/pages/mypage/register"
				})
			}
		},
		
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		}
	}
</script>

<style>
	/* 自定义样式 */
	.u-demo-area{
		cursor: pointer;
	}
</style>