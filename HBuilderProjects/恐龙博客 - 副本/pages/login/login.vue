<template>
	<view class="content">
		<view class="back-button" @tap="cancelLogin">
			<image src="/static/images/back.png" mode="widthFix"></image>
		</view>
		<view class="logo-bg-box">
			<view class="logo-bg-box-first">
				<view class="login-bg-item-top"></view>
				<view class="login-bg-item-bottom"></view>
			</view>
			<view class="logo-bg-box-second">
				<view class="login-bg-item-top"></view>
				<view class="login-bg-item-bottom"></view>
			</view>
		</view>
		<view class="logo-box">
			<image class="logo-item" src="@/static/images/logo@2x.png"></image>
			<!-- #ifdef MP-WEIXIN -->
			<button class="logo-btn" @tap="getProfile">
				<view class="weixin iconfont iconweix"></view>微信授权
			</button>
			<!-- #endif -->
			<!-- #ifdef MP-TOUTIAO -->
			<button class="logo-btn" @tap="getTtProfile">
				<view class="weixin iconfont iconweix"></view>微信授权
			</button>
			<!-- #endif -->
			<!-- #ifdef MP-QQ || MP-BAIDU-->
			<button class="logo-btn" @getuserinfo="getProfile" openType="getUserInfo">
				<view class="weixin iconfont iconweix"></view>授权登陆
			</button>
			<!-- #endif -->
			<!-- #ifdef MP -->
			<view @tap="cancelLogin" class="cancel-login">暂不登录，返回首页</view>
			<view class="login-title-info">登录即代表您同意《用户协议》和《隐私政策》</view>
			<!-- #endif -->


		</view>
		<!-- #ifdef APP-PLUS||H5 -->
		<view class="login-form logpos">
			
			<!-- 修改：手机登录修改为账号登录 -->
			<u-form ref="uForm" :label-style="labelStyle" :model="user">
				<u-form-item label="账号 :" prop="name">
					<u-input placeholder="请输入账号" v-model="user.name" />
				</u-form-item>
				<u-form-item label="密码 :" prop="password">
					<u-input type="password" placeholder="请输入密码" v-model="user.password" />
				</u-form-item>
			</u-form>
			<button type="primary" @click="login">登陆</button>
			<!-- 修改：手机登录修改为账号登录 -->
			
			<!-- 修改：已有账号修改为忘记密码？|注册账号 -->
			<view class="links">
				<view @tap="gotoForgetPassword">忘记密码？</view>
				<view>|</view>
				<view class="link-highlight" @tap="gotoRegistration">注册账号</view>
			</view>
			<!-- 修改：已有账号修改为忘记密码？|注册账号 -->
			
			<view class="login-bottom">
				<view class="login-title-info">登录即代表您同意<text>《用户协议》</text>和<text>《隐私政策》</text></view>
			</view>


		</view>
		<!-- 目前已无法显示 -->
		<!-- #endif -->
		<!-- <u-popup v-model="show" mode="bottom" :closeable="true">
			<view class="login-form">
				<u-form ref="uForm" :label-style="labelStyle">
					<u-form-item label="账号 :">
						<u-input placeholder="请输入账号" v-model="username" />
					</u-form-item>
					<u-form-item label="密码 :">
						<u-input type="password" placeholder="请输入密码" v-model="password" />
					</u-form-item>
				</u-form>
				<button type="primary" @tap="login">登陆</button>
				<view class="links">
					<view @tap="userphonelogin">手机登录</view>
					<view>|</view>
					<view @tap="gotoForgetPassword">忘记密码？</view>
					<view>|</view>
					<view class="link-highlight" @tap="gotoRegistration">注册账号</view>
				</view>
			</view>

		</u-popup> -->
		<u-popup v-model="passShow" mode="bottom" :closeable="true">
			<view class="login-form">
				<u-form ref="uForm" class="form" :label-style="labelStyle">
					<u-form-item label="用户名 :" label-width="160" prop="name">
						<u-input placeholder="请输入找回密码的用户名" v-model="user.name" />
					</u-form-item>
					<u-form-item label="手机号 :" label-width="160" prop="phonenum">
						<u-input placeholder="请输入找回密码的手机号" v-model="user.phonenum" />
					</u-form-item>
				</u-form>
				<button type="primary" @tap="getPassWords">找回密码</button>
				<view class="links">
					<view @tap="goLogin">立即登陆</view>
				</view>
			</view>
		</u-popup>

		
		<!-- 注册 -->
				<!-- <u-popup v-model="registShow" mode="bottom" :closeable="true"> -->
					<!-- <view class="login-form"> -->
						<!-- <u-form :model="user" ref="uForm" :label-style="labelStyle">
							<u-form-item label="用户名 :" label-width="160" prop="name">
								<u-input placeholder="请输入用户名" v-model="user.name" />
							</u-form-item>
							<u-form-item label="密码 :" label-width="160" prop="password">
								<u-input type="password" placeholder="请输入密码" v-model="user.password" />
							</u-form-item>
							<u-form-item label="确认密码 :" label-width="160" prop="newpassword">
								<u-input type="password" placeholder="请再次输入密码" v-model="user.newpassword" />
							</u-form-item>
							<u-form-item label="手机号 :" prop="phonenum" labelWidth="160" >
								<u-input type="text" placeholder="请输入手机号" v-model="user.phonenum"></u-input>
							</u-form-item>
							<u-form-item label="省份 :" prop="province" labelWidth="160" >
								<u-input type="text" placeholder="请输入省份" v-model="user.province"></u-input>
							</u-form-item>
							<u-form-item label="城市 :" prop="city" labelWidth="160" >
								<u-input type="text" placeholder="请输入城市" v-model="user.city"></u-input>
							</u-form-item>
							<u-form-item label="性别" labelWidth="160" prop="gender">
								<u-radio-group v-model="user.gender" @change="radioGroupChange">
									<u-radio @change="radioChange" v-for="(item, index) in listgd" :key="index" :name="item.val"
										:disabled="item.disabled" >
										{{item.txt}}
									</u-radio>
								</u-radio-group>
							</u-form-item>
							<u-form-item label="角色" labelWidth="160" prop="role">
								<u-radio-group v-model="user.role" @change="radioGroupChange">
									<u-radio @change="radioChange" v-for="(item, index) in listrl" :key="index" @click="clicklt(item.path)"  :name="item.val"
										:disabled="item.disabled" >
										{{item.txt}}
									</u-radio>
								</u-radio-group>
							</u-form-item>
						</u-form>
						<button type="primary" @click="register()">注册</button> -->
						<view class="links">已有账号？<view class="link-highlight" @tap="goLogin">点此登陆</view>
						</view>
					<!-- </view> -->
				<!-- </u-popup> -->
<!-- 注册 -->
	</view>

</template>

<script>
// import { user } from '../../api/auth';
	// import API from '@/api/api.js';
	export default {
		data() {
			return {
				show: false,
				passShow: false,
				registShow: false,
				labelStyle: {
					color: "#434343",
				},
				codeBtn: {
					text: '获取验证码',
					waitingCode: false,
					count: this.seconds
				},
				// username: '',
				// password: '',
				emails: '',
				reemail: '',
				rephone: '',
				recode: '',
				reusername: '',
				repassword: '',
				disableCodeBtn: false,
				captchaImg: '',
				seconds: 60,
				
				// // 表单关联
				// user:{
				// 	name:"",
				// 	password:"",
				// 	newpassword:"",
				// },
				
				
				// 表单关联
								user: {
									name: '',
									password: '',
									newpassword: '',
									avatart:'',
									phonenum:'',
									province:'',
									city:'',
									gender:'',
									role:'',
									create:'',
									valid:1
								},
								listgd: [{
									    val:'男',
										txt: '男',
										disabled: false //不禁用所有单选框
									},
									{
										val:'女',
										txt: '女',
										disabled: false
									}],
								addressData: {
									listgd: '男',
								},
								// u-radio-group的v-model绑定的值如果设置为某个radio的name，就会被默认选中
								listrl: [{
										val:'普通用户',
										txt: '普通用户',
										disabled: false
									},
									{
										val:'管理员',
										txt:'管理员',
										path:"/pages/read/read",
										disabled:false
									}],
									addressData: {
									    listrl: '普通用户',
									},
				
								rules: {
									// 字段名称
									name: [{
											required: true,
											message: '请填写用户名',
											trigger: ['change', 'blur'],
										},
									],
									password: [{
										required:true,
										message: '请输入密码',
										trigger: ['change', 'blur']
									}],
									newpassword: [{
											required: true,
											message: "确认密码不能为空",
											trigger: "blur"
										},
										{
											trigger: 'blur',
											validator: (rule, value, callback) => {
												if (value !== this.user.password) {
													callback(new Error('两次输入密码不一致'))
												} else {
													callback()
												}
											}
										}
									],
									
									province:[{
											required: true,
											message: '请填写省份',
											trigger: ['change', 'blur'],
										}
									],
									city:[{
											required: true,
											message: '请填写城市',
											trigger: ['change', 'blur'],
										}
									]
									
								}
				
							}
						},
		// onLoad() {
		// 	this.user.create=this.getTime();
		// 	console.log(this.user.create)
		// },


			
		methods: {
			//导航点击的跳转处理函数
			clicklt (url) {
				uni.navigateTo ({
					url
				})
			},
			
			userphonelogin: function() {
				this.show = false;
				this.registShow = false;
				this.passShow = false;
			},
			goLogin: function() {
				this.show = true;
				this.registShow = false;
				this.passShow = false;

			},
			cancelLogin: function() {
				uni.switchTab({
					url: '../index/index'
				})
			},
			gotoForgetPassword: function() {
				this.show = false;
				this.passShow = true;
			},
			gotoRegistration: function() {
				// this.show = false;
				// this.registShow = true;
				uni.navigateTo({
					url:"/pages/register/register"
				})
			},
			login: function() 
				{
					console.log("登录");
					uni.request({
						//地址
						url:"http://localhost:8080/dologin",
						//请求的方式
						method:"POST",
						//提交的数据
						data:this.user,
						//返回的数据（响应）
						success:(res)=>{
							console.log(res);
							//若登录成功即code=200，则保存用户信息，返回个人界面
							//若登录失败即code=201，则提示用户名或密码错误
							if ( res.data.code*1 == 200){
								//保存
								console.log("登录成功");
								console.log(res);
								uni.setStorageSync("user",res.data.result);
								uni.navigateBack();//返回上一个页面
							}else{
								//失败则提示
								// uni.showToast({
								// 	title: '失败',
								// 	//将值设置为 success 或者直接不用写icon这个参数
								// 	icon: 'success',
								// 	//显示持续时间为 2秒
								// 	duration: 2000
								// })  
								this.$u.toast("登录失败，用户名或密码错误");
							}
						}
					});
				}
			,
			// controller(){
			// 	uni
			// }
			
			getPassWords: function() {						
							uni.request({
								//地址
								url:"http://localhost:8080/retrievepassword",
								//请求的方式
								method:"POST",
								//提交的数据
								data:this.user,
								//返回的数据（响应）
								success:(res)=>{
									console.log(res);
									//若找回密码成功即code=200，则保存用户信息，返回个人界面
									//若找回密码失败即code=201，则提示用户名或密码错误
									if ( res.data.code*1 == 200){
										//保存
										console.log("找回密码成功");
										uni.showToast({
											title:"您的密码："+res.data.result,
											icon:"none",
											duration: 3000
										})
										
									}else{
										//失败则提示
										
										this.$u.toast("验证失败");
									}
								}
							});
						},
			register() {
						console.log("提交")
						//网络提交数据
						if(this.user!=""){
							uni.request({
								// 地址
								url: "http://localhost:8080/doregister",
								// 请求方式
								method:"POST",
								// 请求发送的数据
								data:this.user,
								// 接收响应
								success:(res)=>{
									if(res.data.code *1==200){
											// console.log(res.data.result);
										// uni.setStorageSync('user',res.data.result);
										
										uni.navigateBack()
										
											//注册成功后返回 登录页面
									}else{
										this.$u.toast('注册失败')  //提示框
									}
								}
							})
						}
							},
							radioChange(e) {
								// console.log(e);
								console.log(e,"radio",this.user.gender);
								console.log(e,"radio",this.user.role)
								// this.user.role=e.detail.value
							},
							// 选中任一radio时，由radio-group触发
							radioGroupChange(e) {
								// console.log(e);
								console.log(e,"group",this.user.gender);
								console.log(e,"group",this.user.role);
							},
			

			isemail: function(text) {
				var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
				return reg.test(text);
			},

			
			sendCode: function() {
				if (this.reemail == '' || !this.isemail(this.reemail)) {
					uni.showToast({
						title: '请输入正确的邮箱'
					})
					return;
				}

				if (this.codeBtn.waitingCode == false) {
					API.getEmailCode({
						email: this.reemail
					}).then(res => {
						console.log(res);
						if (res.status === 200) {
							uni.showToast({
								title: "验证码已发送"
							})
						}
					})
				}
				this.disableCodeBtn = true;
				this.codeBtn.waitingCode = true;
				this.codeBtn.count = this.seconds;
				this.codeBtn.text = this.codeBtn.count + 's';
				let countdown = setInterval(() => {
					this.codeBtn.count--;
					this.codeBtn.text = this.codeBtn.count + 's';
					if (this.codeBtn.count < 0) {
						clearInterval(countdown);
						this.codeBtn.text = '重新发送';
						this.codeBtn.waitingCode = false;
						this.disableCodeBtn = false;
					}
				}, 1000);
			},

			getTtProfile: function(e) {
				uni.showLoading({
					title: '正在登录...',
				})
				API.getTtProfile().then(res => {
						//console.log(res)
						if (this.isDetail) {
							uni.navigateBack({
								delta: 1,
							})
						} else {
							uni.switchTab({
								url: '/pages/mine/mine'
							})
						}
						tt.hideLoading()
					})
					.catch(err => {
						console.log(err)
						tt.hideLoading()
					})
			},


			getProfile: function(e) {
				uni.showLoading({
					title: '正在登录!',
					mask: true
				})
				// #ifdef MP-WEIXIN
				API.getProfiles().then(res => {
						if (this.isDetail) {
							uni.navigateBack({
								delta: 1,
							})
						} else {
							uni.switchTab({
								url: '/pages/mine/mine'
							})
						}

						uni.hideLoading()
					})
					.catch(err => {
						console.log(err)
						uni.hideLoading()
					})
				// #endif
				// #ifdef MP-BAIDU || MP-QQ
				API.getUserInfos(e).then(res => {
						if (this.isDetail) {
							uni.navigateBack({
								delta: 1,
							})
						} else {
							uni.switchTab({
								url: '/pages/mine/mine'
							})
						}

						uni.hideLoading()
					})
					.catch(err => {
						console.log(err)
						uni.hideLoading()
					})
				// #endif

			},
			
		},
		// 必须要在onReady生命周期，因为onLoad生命周期组件可能尚未创建完毕
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
	}
</script>

<style lang="scss" scoped>
	$color-primary: #49b44a;

	.content {
		width: 100vw;
		height: 100vh;
		overflow: hidden;
		background-color: #000;
	}

	.logo-bg-box {
		width: 100%;
		height: 9482rpx;
		opacity: .5;
		animation: move 60s linear infinite;

		.logo-bg-box-first,
		.logo-bg-box-second {
			width: 100%;
			height: 50%;
		}

		.login-bg-item-top {
			width: 100%;
			height: 50%;
			background: url(https://wx4.sinaimg.cn/bmiddle/e448bca1ly1gz9dh4shsmj20ku1voajp.jpg);
			background-size: 100% 100%;
		}

		.login-bg-item-bottom {
			width: 100%;
			height: 50%;
			background: url(https://wx4.sinaimg.cn/bmiddle/e448bca1ly1gz9dh4shsmj20ku1voajp.jpg);
			background-size: 100% 100%;
		}

		@-webkit-keyframes move {
			0% {
				transform: translateY(0);
			}

			100% {
				transform: translateY(-4741rpx);
			}
		}

		@keyframes move {
			0% {
				transform: translateY(0);
			}

			100% {
				transform: translateY(-4741rpx);
			}
		}

	}

	.img-captcha {
		width: 150upx;
		height: 60upx;
	}

	button[type="primary"] {
		background-color: $color-primary;
		border-radius: 0;
		font-size: 34upx;
		margin-top: 60upx;
	}

	.logo-box {
		padding-top: 115rpx;
		position: fixed;
		top: 15%;
		left: 50%;
		transform: translateX(-50%);
		z-index: 2;

		.logo-item {
			display: block;
			width: 400rpx;
			height: 66rpx;
			margin: 0 auto 120rpx;
		}

		.logo-btn {
			width: 418rpx;
			height: 88rpx;
			background-color: #fff;
			box-shadow: 0 2rpx 16rpx 0 rgba(0, 0, 0, .08);
			border-radius: 88rpx;
			font-size: 28rpx;
			line-height: 88rpx;
			color: #93b1b1;
			display: flex;
			-webkit-box-align: center;
			align-items: center;
			-webkit-box-pack: center;
			justify-content: center;

			.iconfont {
				font-size: 40rpx;
				margin-right: 20rpx;
			}

			.weixin {
				width: 10rpx;
				height: 10rpx;
				padding-left: 2rpx;
				line-height: 40rpx;
				font-size: 28rpx;
				border-radius: 50%;
				margin-right: 10rpx;
				color: #fff;
				background-color: #49b44a;
			}
		}

		.cancel-login {
			font-size: 28rpx;
			height: 60rpx;
			line-height: 60rpx;
			color: #fff;
			text-align: center;
			margin-top: 20rpx;
			z-index: 5;
		}

		.login-title-info {
			font-size: 20rpx;
			color: #fff;
			width: 500rpx;
			margin-top: 100rpx;
			text-align: center;
		}
	}

	.logpos {
		background: #fff;
		width: 750rpx;
		height: 800rpx;

		position: fixed;
		border-radius: 30rpx 30rpx 0 0;
		bottom: 0;
		left: 0;
	}

	.login-form {

		padding: 40rpx;

		.codebutton {
			font-size: 24upx;
			margin: 0;
			width: 180upx;
			text-align: center;

			&:after {
				border: none
			}

			&.active {
				background-color: $color-primary;
				color: $uni-text-color-inverse;
			}
		}

	}

	button[type="primary"] {
		background-color: $color-primary;
		border-radius: 0;
		font-size: 34upx;
		margin-top: 60upx;
	}

	.links {
		text-align: center;
		margin-top: 40upx;
		font-size: 26upx;
		color: #434343;

		view {
			display: inline-block;
			vertical-align: top;
			margin: 0 10upx;
		}

		.link-highlight {
			color: #434343;
		}
	}

	.login-bottom {
		position: absolute;
		bottom: 60rpx;
		width: 750rpx;
		text-align: center;

		.login-title-info {
			text {
				color: #18B566;
			}
		}
	}

	.back-button {
		position: fixed;
		top: 80rpx;
		left: 40rpx;
		z-index: 10000;

		image {
			width: 60rpx;
			height: 60rpx;
		}
	}
</style>
