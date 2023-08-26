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
				<u-input :password-icon="true" :border="border" type="password" v-model="user.mima"
					placeholder="请输入密码"></u-input>
			</u-form-item>
			<u-form-item label-width="140" label-position="left" label="确认密码" prop="rmima">
				<u-input :password-icon="true" :border="border" type="password" v-model="user.rmima"
					placeholder="再输一次密码"></u-input>
			</u-form-item>
			<u-form-item label-width="140" label-position="left" label="年龄" prop="age">
				<u-input :border="border" placeholder="请输入年龄" v-model="user.age" type="text"></u-input>
			</u-form-item>
			<u-form-item label-width="140" label-position="left" label="用户类别" prop="leibie">
				<radio-group @change="radioChange" class="uni-padding-wrap">
					<label class="u-radio">
						<radio value="1" />
						超级用户
					</label>
					<label class="u-radio">
						<radio value="2" />
						普通用户
					</label>
					<label class="u-radio">
						<radio value="3" />
						电影推荐
					</label>
				</radio-group>
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
				user: { } 
			}
		},
		methods: {
			submit() {
				console.log(this.user)
				// 检查两次密码是否一致
				// 不同，提醒用户
				if (this.user.mima != this.user.rmima) {
					this.$u.toast("两次输入的密码不同");
					this.$refs.uForm.resetFields();
					// 终止代码继承往下走
					return
				}
				
				// 密码一致，发送“注册”的请求
				uni.request({
					url: "http://localhost:8080/doRegister",
					method: "POST",
					data: this.user,
					
					success: (res) => {
						console.log(res);
						
						// 判断是否在数据库中注册成功
						if (res.data.code * 1 == 200) {
							// 放回登录界面
							uni.navigateBack();
						}
						else {
							// 注册失败提示
							this.$u.toast("注册失败，请重新尝试！");
						}
					}
				})
				
			},
			radioChange(e) {
				//console.log(e.detail)
			   this.user.leibie = e.detail.value
			}
		}
	}
</script>

<style>

</style>