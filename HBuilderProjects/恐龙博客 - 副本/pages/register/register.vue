<template>
	<view class="u-margin-left-26 u-margin-right-26">
		<!-- 顶部导航栏 -->
		<view class="">
			<u-navbar title="注册" :borderBottom="false"></u-navbar>
		</view>
		<!-- 分割线 -->
		<view class="line"></view>
		<!-- 页面注册板块 -->
		<!-- 注册 -->
				<!-- <u-popup v-model="registShow" mode="bottom" :closeable="true"> -->
					<!-- <view class="login-form"> -->
						<u-form :model="user" ref="uForm" class="form">
							<u-form-item label="用户名 :" label-width="160" prop="name" >
								<u-input placeholder="请输入用户名" v-model="user.name" class="form-item"/>
							</u-form-item>
							<u-form-item label="密码 :" label-width="160" prop="password" >
								<u-input type="password" placeholder="请输入密码" v-model="user.password" class="form-item" />
							</u-form-item>
							<u-form-item label="确认密码 :" label-width="160" prop="newpassword" >
								<u-input type="password" placeholder="请再次输入密码" v-model="user.newpassword" class="form-item" />
							</u-form-item>
							<u-form-item label="手机号 :" prop="phonenum" labelWidth="160" >
								<u-input type="text" placeholder="请输入手机号" v-model="user.phonenum" class="form-item"></u-input>
							</u-form-item>
							<u-form-item label="省份 :" prop="province" labelWidth="160" >
								<u-input type="text" placeholder="请输入省份" v-model="user.province" class="form-item"></u-input>
							</u-form-item>
							<u-form-item label="城市 :" prop="city" labelWidth="160" >
								<u-input type="text" placeholder="请输入城市" v-model="user.city" class="form-item"></u-input>
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
									<u-radio @change="radioChange" v-for="(item, index) in listrl" :key="index"  :name="item.val"
										:disabled="item.disabled" >
										{{item.txt}}
									</u-radio>
								</u-radio-group>
							</u-form-item>
						</u-form>
						<button type="primary" @click="register()">注册</button>
						<!-- <view class="links">已有账号？<view class="link-highlight" @tap="goLogin">点此登陆</view>
						</view> -->
					<!-- </view> -->
				<!-- </u-popup> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
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
					phonenum:[{
							required: true,
							message: '请填写手机号',
							trigger: ['change', 'blur'],
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
		methods: {
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
							// 必须要在onReady生命周期，因为onLoad生命周期组件可能尚未创建完毕
							
		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		}
	}
</script>

<style>

</style>
