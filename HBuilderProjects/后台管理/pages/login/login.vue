<template>
	<view class="content_all">
		<view class="content_1 content">
			<view class="header">
				hello !
			</view>
			<view style="margin-top: 20upx;font-size: 25px;">欢迎来到后台管理 ！</view>
			<view class="list">
				<view class="list-call" >
					<image class="img" src="/static/shilu-login/phone.png"></image>
					<input class="biaoti" v-model="user.name" type="text" maxlength="11" placeholder="输入用户名" />
				</view>
				<view class="list-call" style="margin-top: 20upx;">
					<image class="img" src="/static/shilu-login/block.png"></image>
					<input class="biaoti" v-model="user.password" type="text" maxlength="32" placeholder="请输入密码" password="true" />
				</view>
			</view>
			
			<view class="dlbutton" hover-class="dlbutton-hover" @tap="bindLogin()">
				<text>登录</text>
			</view>
			
		</view>
		<image src="http://cd7.yesapi.net/0B4163C86181759DD5DE6C761AF719A5_20200629155530_4fdd81610bea6fdaabbe27637f116be6.png" mode="aspectFill" style="width: 100%;height: 100%;"></image>
	</view>
</template>

<script>
	
	export default {
		onShow(){
			// var that = this
			// uni.request({
			// 	url:'https://hn216.api.yesapi.cn/',
			// 		method: "POST",
			// 		header: {
					  
			// 		},
			// 		data:{
			// 			model_name:'Meun_person',
			// 			where:'[["id", ">","0"]]',
			// 			app_key:'0B4163C86181759DD5DE6C761AF719A5',
			// 			service:'App.Table.FreeQuery',
			// 			perpage:1000, //每一页12个数据
			// 		},
			// 		success: function (res) {
			// 			// console.log(res.data.data.list)
			// 			console.log(res)
			// 			that.people = res.data.data.list
						
			// 		}
			// 	})
		},
		data() {
			return {
				user : {
					name:'',
					password:'',
				},
				
			};
		},
		methods: {
		    bindLogin() {
				
				// 打印
				console.log("正在登录");
				// 网络提交数据
				uni.request({
					// 地址
					url: "http://localhost:8080/dologin",
					// 请求方式
					method: "POST",
					// 传递的参数
					data: this.user,
					
					// 查看返回的数据
					success: (res) => {
						// console.log(this.user);
						console.log(res);
						// 登录成功，code = 200 -> 保存用户信息，返回个人中心
						if (res.data.code * 1 == 200 && res.data.result.role == "管理员") {
							// 保存数据在 App 端
							uni.setStorageSync("user", res.data.result);
							uni.showToast(
								{
									title:"登录成功",
									duration:200,
								},
								uni.redirectTo({
									url:'../admin/adminRe'
								})
							)
						}
						// 登录失败，提示用户名或者密码错误
						else {
							this.$u.toast("登录失败，用户名或者密码错误！");
							this.$refs.uForm.resetFields();
						}
						
				
					}
				})
				
				// // 管理界面中包含了账号管理，经常有人误删账号信息导致其他人登录不上，所以设置为静态的密码
				// // 账号：17554196160
				// // 密码：123456
				if("17554196160" == this.phoneno && "123456" == this.password){
					uni.setStorageSync('account', this.phoneno);
					uni.showToast(
					{
						title:"登录成功",
						duration:200,
					},
					uni.redirectTo({
						url:'../admin/admin'
					})
					
					)
					
				}
				// // 连接到云服务器中的登录方式
				// // 手机号正则
				// if (!(/^1(3|4|5|6|7|8|9)\d{9}$/.test(this.phoneno))) {
				//      uni.showToast({
				//         icon: 'none',
				//         title: '手机号不正确'
				//     });
				//     return;
				// }
				// // 密码正则
		  //       if (!(/^\d{6}$/.test(this.password))) {
		  //           uni.showToast({
		  //               icon: 'none',
		  //               title: '密码不是六位数字！'
		  //           });
		  //           return;
		  //       } 
				// var judge_logo = 1
				// for(var i = 0;i < this.people.length;i++){
				// 	if(this.people[i].user_num == this.phoneno && this.people[i].user_pass == this.password){
				// 		judge_logo = 2
				// 		uni.setStorageSync('judge_logo', judge_logo);
				// 		uni.setStorageSync('account', this.phoneno);
				// 		uni.showToast(
				// 		{
				// 			title:"登录成功",
				// 			duration:200,
				// 		},
				// 		uni.redirectTo({
				// 			url:'../admin/admin'
				// 		})
						
				// 		)
						
				// 	}
				// }
				// if(judge_logo == 1){
				// 	uni.showToast({
				// 		title:'手机号或密码错误！',
				// 		icon:'none'
				// 	})
				// }
				
		    }
		}
	}
</script>

<style>
	.content_all{
		display: flex;
		justify-content: center;
		flex-direction: row;
		align-items: center;
		height: 100vh;
		width: 100%;
		
	}
	.content_1{
		width: 50%;
		height: 50%;
		margin-left: 100upx;
		
	}
	.content {
		display: flex;
		flex-direction: column;
		/* justify-content:center; */
		/* align-items: center; */
		
		
	}
	.header {
		font-size: 50px;
	}
	
	
	.list {
		display: flex;
		flex-direction: column;
		padding-top: 40upx;
		/* padding-left: 70upx;
		padding-right: 70upx; */
	}
	.list-call{
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		height: 20upx;
		color: #333333;
		width: 500upx;
		margin-bottom: 3upx;
		
		padding: 20upx;
		background-color: #F6F4FC;
		
	}
	.list-call .img{
		width: 20upx;
		height: 20upx;
	}
	.list-call .biaoti{
		flex: 1;
		text-align: left;
		font-size: 12upx;
		line-height: 100upx;
		margin-left: 16upx;
	}
	
	.dlbutton {
		color: #FFFFFF;
		font-size: 12upx;
		width:200upx;
		height:55upx;
		background-color: #1890FF;
		
		align-items: center;
		display: flex;
		justify-content: center;
		
		line-height: 100upx;
		text-align: center;
		/* margin-left: auto;
		margin-right: auto; */
		margin-top: 30upx;
	}
	.dlbutton hover {
		background-color: #1890cc;
	}
	.xieyi{
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		font-size: 22upx;
		margin-top: 80upx;
		color: #FFA800;
		text-align: center;
		height: 40upx;
		line-height: 40upx;
	}
	.xieyi text{
		font-size: 22upx;
		margin-left: 15upx;
		margin-right: 15upx;
	}
</style>
