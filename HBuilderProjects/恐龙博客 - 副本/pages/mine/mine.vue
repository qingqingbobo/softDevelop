<template>
	<view class="content">
		<view class="my-topbg">
			<view class="user-card">
				<view class="my-top" v-if="logined">
					<view bindtap="toHome" class="my-head">
						<image mode="aspectFill"
							:src="user.avatart"></image>
					</view>
					<view class="my-top-cell">
						<view class="my-name">
							<view class="name">{{user.name}}</view>
							<view class="pag">{{user.province}}·{{user.city}}</view>
						</view>
						<view class="my-post">
							{{user.signature?user.signature:'这个人很懒什么都没写'}}
						</view>
					</view>
					<view @tap="goMyEdit" class="pick-code">编辑资料</view>
				</view>
				<view @tap="toAuthorized" class="my-top" v-else>
					<view class="my-top-cell">
						<view class="my-name">
							<view class="name">点击登录</view>
						</view>
						<view class="my-post">
							暂无
						</view>
					</view>
					<view class="my-head">
						<image mode="aspectFill" src="@/static/images/user.png"></image>
					</view>
				</view>
				<view class="my-other">
					<view class="my-hbox">
						<view class="item">
							<view @tap="toFavs" class="item-box">
								<view class="cell">
									<view class="num">{{post.collectnum}}</view>
									<view class="name">收藏</view>
								</view>
							</view>
						</view>
						<view class="item">
							<view @tap="toLikes" class="item-box">
								<view class="cell">
									<view class="num">{{post.thumbupnum}}</view>
									<view class="name">点赞</view>
								</view>
							</view>
						</view>
						<view class="item">
							<view @tap="toComment" class="item-box">
								<view class="cell">
									<view class="num">{{post.commentnum}}</view>
									<view class="name">评论</view>
								</view>
							</view>
						</view>
						<view class="item">
							<view @tap="toUserPoints" class="item-box">
								<view class="cell">
									<view class="num">{{post.readnum}}</view>
									<view class="name">浏览量</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		
		<view class="fix-card">
			<view class="flex-wrp flex-other">
				<view @tap="gotoProduction" class="flex-item">
					<text>我的作品</text>
					<view class="value">查看我的作品</view>
					<image src=""></image>
				</view>
			</view>
			<!-- <view class="flex-wrp flex-other">
				<view @tap="gotoRead" class="flex-item">
					<text>浏览量</text>
					<view class="value">查看我的浏览量</view>
					<image src=""></image>
				</view>
			</view> -->
		</view>
		
		
		<view class="fix-card">
			<view class="flex-wrp flex-other">
				<view @tap="feedBack" class="flex-item">
					<text>我要反馈</text>
					<view class="value">给我们提个意见或建议</view>
					<image src="@/static/images/icon-link.png"></image>
				</view>
				<view @tap="toContact" class="flex-item">
					<text>联系客服</text>
					<view class="value">联系客服</view>
					<image src="@/static/images/icon-link.png"></image>
				</view>
				<view  class="flex-item" data-type="投诉">
					<text>版本说明</text>
					<view class="value">v 1.1.1</view>
					<!-- <image src="@/static/images/icon-link.png"></image> -->
				</view>
				<view @tap="bindLogout" class="flex-item" data-type="意见">
					<text>退出登录</text>
					<view class="value">退出登录</view>
					<image src="@/static/images/icon-link.png"></image>
				</view>
			</view>
		</view>
		<view class="copyright-footer">
			<image src=""></image>
			<view class="text" v-if="company_title">{{company_title}}</view>
			<view class="text">恐龙科技平台技术支持</view>
		</view>
		<u-popup v-model="show" mode="center" :mask="true" :closeable="true" border-radius="20">
			<view class="kefu-justify-center">
				<view class="title">关注我们</view>
				<view class="image">
					<image src="http://uploadfile.yunnangateway.com/s2/c19/2020/0225/20200225112817823.png"
						mode="widthFix"></image>
				</view>
				<view class="save" @tap="save">保存图片</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import API from '@/api/api.js'
	export default {
		data() {
			return {
				UserInfo: '',
				show: false,
				privacyid: 707, //隐私政策页面id
				about: 846, //关于我们页面id
				company_title: '恐龙小程序',
				honour: '',
				account: '',
				credit: '',
				logined:false,
				user:{
					name:'',
					signature:'',
					province:'',
					avatart:'',
					city:''
					
				},
				post:{
					collectnum:'',
					thumbupnum:'',
					commentnum:'',
					readnum:''
				}

			}
		},
		onLoad() {
			//console.log("onshow");
			//1.获取存储的用户信息
			// const value=uni.getStorageSync("user");
			// this.user=value;
			// console.log(this.user);
		},

		methods: {

			feedBack: function() {
				uni.navigateTo({
					url: "/pages/feedback/feedback"
				})
			},
			gotoProduction: function() {
				uni.navigateTo({
					url: '/pages/production/prodc2'
				})
			},
			gotoRead: function() {
				uni.navigateTo({
					url: '/pages/read/read'
				})
			},
			gotoPrivacy: function() {
				uni.navigateTo({
					url: '../page/page?id=' + this.privacyid
				})
			},

			toUserPoints: function() {
				uni.navigateTo({
					url: '/pages/read/read'
				})
			},

			toRank: function() {
				uni.navigateTo({
					url: '../rank/rank',
				})
			},
			
			toContact:function(){
				uni.navigateTo({
					url:"/pages/oncon"
				})
				
			},


			sign: function() {
				API.updateCredit({
						action: "sign"
					}).then(res => {
						this.getMineInfo()
						uni.showToast({
							title: res.message,
							icon: 'success',
							duration: 900
						})
						if (res.status == 201) {
							uni.navigateTo({
								url: '/pages/credits/rank'
							})
						}
					})
					.catch(err => {
						console.log(err)
					})
			},
			// gotoVesion: function() {
			// 	uni.navigateTo({
			// 		url: "/pages/page/version"
			// 	})
			// },
			goMyEdit: function() {
				uni.navigateTo({
					url: '../editme/editme'
				})
			},
			// 跳转收藏夹
			toFavs: function() {
				console.log("我是分割线111111");
				if (this.user == '') {
					console.log("我是分割线2222222222");
					uni.navigateTo({
						url: '../login/login'
					})
				} else {
					console.log("我是分割线");
					//console.log(typeof(this.user));
					
					//网络提交数据
					uni.request({
						//地址
						url:"http://localhost:8080/GetCollect",
						//请求的方式
						method:"POST",
						//提交的数据
						data:this.user,
						//返回的数据（响应）
						success:(res)=>{
							console.log("发送成功");
							// console.log(res);
							//console.log(res);
							//若登录成功即code=200，则保存用户信息，返回个人界面
							//若登录失败即code=201，则提示用户名或密码错误
							if ( res.data.code*1 == 200){
								//保存
								uni.setStorageSync("posts",res.data.result);//收藏文章的信息存在posts中
								console.log(res);
								//跳转至收藏页面
								uni.navigateTo({
									url: './myfav/myfav'
								});
								
								}else{
									console.log("user信息已发送，可能是后台校验出错");
									console.log(res);
								}}
						});
						
					
				}
			},
			toLikes: function() {
				if (this.user == '') {
					uni.navigateTo({
						url: '../login/login'
					})
				} else {
					uni.navigateTo({
						url: './mylike/mylike'
					})
				}
			},
			toComment: function() {
				if (this.user == '') {
					uni.navigateTo({
						url: '../login/login'
					})
				} else {
					uni.navigateTo({
						url: './mycomment/mycomment'
					})
				}
			},
			save: function() {
				uni.downloadFile({
					url: 'http://uploadfile.yunnangateway.com/s2/c19/2020/0225/20200225112817823.png',
					success: (res) => {
						if (res.statusCode === 200) {
							uni.saveImageToPhotosAlbum({
								filePath: res.tempFilePaths,
								success: function() {
									uni.showToast({
										title: "保存成功"
									})
								}
							});
						}
					}
				});
			},
			getMineInfo: function() {
				let that=this;
				API.checkUser().then(res => {
						if (res.status == 200) {
							API.getMineInfo().then(res => {
									console.log(res);
									this.UserInfo = res.user,
										this.account = res.account,
										this.credit = res.credit,
										this.subscribe = res.subscribe
									this.honour = res.honour
								})
								.catch(err => {
									console.log(err)
								})
						} else {
							uni.showModal({
								title: '温馨提示',
								content: '检测出授权已过期, 是否注销授权?',
								success: function(res) {
									if (res.cancel) {
										uni.showToast({
											title: '取消注销',
											icon: 'success',
											duration: 900
										})
									} else {
										that.bindLogout()
									}
								},
								fail: function(err) {
									console.log(err)
								}
							})
						}
					})
					.catch(err => {
						console.log(err)
					})
			},
			// 退出登录功能
			bindLogout: function() {
				API.Loginout()
				uni.showToast({
					title: '正在退出!',
					icon: 'loading',
					duration: 1500
				})
				this.user = ""
				uni.removeStorageSync('videoAdLogs')
				uni.reLaunch({
					url: "/pages/index/index"
				})
			},
			toAuthorized: function() {
				uni.navigateTo({
					url: '/pages/login/login'
				})
			}

		},
		//当页面展示的时候会调用
		onShow() {
			//console.log("onshow");
			//1.获取存储的用户信息
			const value=uni.getStorageSync("user");
			this.user=value;
			// console.log(value);
			//2.判断是否存在，如果不存在logined赋值为false 否则赋值为true
			uni.request({
				url:"http://localhost:8080/personnum",
				//请求的方式
				method:"POST",
				//提交的数据
				data:this.user,
				//返回的数据（响应）
				success:(res)=>{
					if ( res.data.code*1 == 200){
						//保存
						uni.setStorageSync("post",res.data.result);
						this.post=uni.getStorageSync("post");
					}
				}
			});
			if(value){
				this.logined=true;
			}else{
				this.logined=false;
			}
		},
	}
</script>

<style lang="scss" scoped>
	.content {
		background: #f4f6f6;
	}

	.my-topbg {
		position: relative;
		background: linear-gradient(136deg, #ebfcfa, #f4f6f6);
		padding-bottom: 140rpx;
		margin-bottom: -140rpx;



		.my-top {
			padding: 200rpx 0 40rpx 35rpx;
			margin-bottom: 20rpx;
			position: relative;
			display: flex;
			align-items: center;

			.my-head {
				margin-right: 30rpx;
				position: relative;
				display: flex;
				align-items: center;

				image {
					width: 102rpx;
					height: 102rpx;
					border-radius: 999rem;
					overflow: hidden;
				}
			}

			.pick-code {
				height: 60rpx;
				text-align: center;
				position: relative;
				width: 171rpx;
				background: linear-gradient(311deg, #55aa7f, #5fbf8e);
				font-size: 26rpx;
				color: #f7fffe;
				line-height: 60rpx;
				box-sizing: border-box;
				border-radius: 999rem;
				margin-right: 20rpx;

			}

			.my-top-cell {
				flex: 1;
				overflow: hidden;
				white-space: nowrap;

				.my-name {
					font-size: 42rpx;
					color: #333;
					line-height: 60rpx;
					font-weight: 700;
					display: flex;
					align-items: center;

					.name {
						overflow: hidden;
						white-space: nowrap;
						margin-right: 7rpx;
						width:300rpx;
						overflow:hidden;
						text-overflow:ellipsis;
						
					}

					.pag {
						height: 33rpx;
						background: linear-gradient(293deg, #37e2ca, #06d58b);
						border-radius: 4rpx;
						padding: 0 8rpx;
						margin-left: 12rpx;
						white-space: nowrap;
						text-overflow: ellipsis;
						margin-right: 8rpx;
						line-height: 33rpx;
						font-size: 24rpx;
						color: #fff;
						font-weight: 400;
					}
				}


				.my-post {
					display: flex;
					align-items: center;

					.tag {
						background: #fba117;
						border-radius: 4rpx;
						font-size: 22rpx;
						color: #fff;
						padding: 0 10rpx;
						line-height: 32rpx;
						white-space: nowrap;
						overflow: hidden;
						text-overflow: ellipsis;
						margin-right: 20rpx;

					}
				}
			}
		}

		.my-other {
			position: relative;
			display: flex;
			align-items: center;
			justify-content: space-between;

			.my-hbox {
				display: flex;
				align-items: center;
				padding: 0 20rpx;
				flex: 1;

				.item {
					position: relative;
					flex: 1;

					.item-box {
						height: 118rpx;
						border-radius: 10rpx;
						display: flex;
						align-items: center;

						.cell {
							flex: 1;
							text-align: center;

							.num {
								font-size: 38rpx;
								color: #333;
								font-family: DINAlternate-Bold, DINAlternate;
								font-weight: 700;
								white-space: nowrap;
								text-overflow: ellipsis;
							}

							.name {
								font-size: 24rpx;
								color: #a0acaa;
								margin-top: 7rpx;
								white-space: nowrap;
								text-overflow: ellipsis;
							}
						}
					}
				}
			}
		}


	}

	.fix-card {
		position: relative;
		background: #fff;
		border-radius: 20rpx;
		margin: 20rpx;

		.fix-header {
			position: relative;
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 32rpx 32rpx 0;

			.name {
				font-size: 32rpx;
				color: #384645;
				font-weight: 700;
			}

			.actions {
				font-size: 26rpx;
				color: #a0acaa;
				display: flex;
				align-items: center;

				image {
					width: 20rpx;
					height: 20rpx;
				}
			}
		}

		.flex-wrp {
			display: flex;
			align-items: center;
			flex-wrap: wrap;
			padding: 10rpx 0 20rpx;

			.t-item {
				
				position: relative;
				text-align: center;
				width: 25%;
				padding: 30rpx 0;

				image {
					width: 56rpx;
					height: 56rpx;
					display: block;
					margin: 0 auto;
				}

				text {
					font-size: 26rpx;
					color: #333;
					margin-top: 20rpx;
					display: block;
				}
				.contact{
					position: absolute;
					width: 100%;
					height: 120rpx;
					top:0;
					left:0;
					opacity: 0;
				}
			}
		}

		.flex-other {
			flex-direction: column;
			padding: 0;
			display: flex;
			align-items: center;

			.flex-item {
				position: relative;
				padding: 34rpx 26rpx;
				width: 100%;
				box-sizing: border-box;
				line-height: 42rpx;
				display: flex;
				align-items: center;

				text {
					font-size: 30rpx;
					color: #333837;
				}

				.value {
					font-size: 26rpx;
					color: #a0acaa;
					margin-left: auto;
				}
				

				image {
					width: 24rpx;
					height: 24rpx;
					margin-left: 8rpx;
				}
			}
		}
	}

	.copyright-footer {
		text-align: center;
		font-size: 24rpx;
		color: #b6b9bb;
		padding: 40rpx 0;
		line-height: 36rpx;

		image {
			width: 60rpx;
			height: 60rpx;
			display: block;
			margin: 0 auto 8rpx;
		}
	}

	.kefu-justify-center {
		flex-grow: 1;
		flex-direction: column;
		display: flex;
		width: 500rpx;
		height: 700rpx;
		text-align: center;

		.title {
			padding: 20rpx 30rpx;
			font-size: 30rpx;
		}

		.image {
			width: 400rpx;
			margin: 0 auto;

			image {
				width: 400rpx;
			}
		}

		.save {
			width: 200rpx;
			margin: 20rpx auto;
			padding: 20rpx 30rpx;
			border: 1px solid #00aa7f;
			background: #00aa7f;
			border-radius: 5px;
			font-size: 28rpx;
			color: #fff;
		}
	}
</style>
