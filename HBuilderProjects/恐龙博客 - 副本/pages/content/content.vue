<template>
	<view>
		<!-- 文章内容 -->

		<view class="u-content">

			<u-parse :html="responseData"></u-parse>
		</view>

		<!-- 评论点赞收藏 -->
		<view class="ly_input">
			<input type="text" value="" placeholder="说点什么" v-model="searchName" @confirm="result" />
			<view class="comment">
				<view class="commentIndex">
				</view>
				<view class="" @click="Comment">
					评论
				</view>
			</view>
			<view class="" @click="like">
				喜欢
			</view>
			<view class="" @click="unlike">
				不喜欢
			</view>
			<view class="" @click="share">
				收藏
			</view>
		</view>




	</view>
</template>

<script>
	export default {

		data() {
			return {
				searchName: '',
				user: {
					postid: uni.getStorageSync("zzw"),
					userid: uni.getStorageSync("user").userid
				},
				responseData: uni.getStorageSync("h").content,
				// responseData: uni.getStorageSync("user").content
			};
		},
		// onLoad() {
		// 	this.getData();
		// },
		onLoad(query) {
			this.getData();
			//console.log(query.param1); // 输出：value1

			console.log(this.user.postid);
			// this.user.postid = query.param1;
			// this.$set(this.user, 'postid', query.param1);

			// console.log(query.param2); // 输出：value2
		},
		// onShow() {
		// 	console.log(uni.getStorageSync("h").content)
		// },
		methods: {
			getData() {
				uni.request({
					url: "http://localhost:8080/post", // 后端接口地址
					method: 'POST', // 请求方式，可以是GET、POST等
					data: this.user,
					success: (res) => {
						// 成功响应时的回调函数
						if (res.statusCode * 1 == 200) {
							uni.setStorageSync("h", res.data.result);
							// console.log(responseData)
							console.log("请求成功");

						}
					},

				});
			},
			result(e) {
				// 点击发送评论,之后输入值置为空
				this.$emit('result', e, 1)
				this.searchName = ''
			},
			Comment() {

				uni.navigateTo({
					url: '/pages/comment_content/comment',


				});
				console.log("评论");
			},
			like() {
				// 页面点赞事件参数传递
				// this.$emit('like', '页面点赞事件参数传递')
				uni.request({
					// 地址
					url: "http://localhost:8080//thumbup",
					// 请求方式
					method: "POST",
					// 请求发送的数据
					data: this.user,
					// 接收响应
					//函数
					success: (res) => {
						console.log(res);
						//成功那么code=200，保存用户信息，返回到个人中心
						//如果失败，提示“用户名或者密码错误”
						if (res.data.code * 1 == 200) {
							//保存
							uni.setStorageSync("h", res.data.result);

							console.log("喜欢");
						} else {
							//失败
							this.$u.toast("点赞失败");
						}
					}
				});

			},
			unlike() {
				// 页面点赞事件参数传递
				// this.$emit('like', '页面点赞事件参数传递')
				uni.request({
					// 地址
					url: "http://localhost:8080//DEthumbup",
					// 请求方式
					method: "POST",
					// 请求发送的数据
					data: this.user,
					// 接收响应
					//函数
					success: (res) => {
						console.log(res);
						//成功那么code=200，保存用户信息，返回到个人中心
						//如果失败，提示“用户名或者密码错误”
						if (res.data.code * 1 == 200) {
							//保存
							uni.setStorageSync("h", res.data.result);

							console.log("不喜欢");
						} else {
							//失败
							this.$u.toast("拉踩失败");
						}
					}
				});

			},
			share() {
				// 页面点赞事件参数传递
				// this.$emit('like', '页面点赞事件参数传递')
				uni.request({
					// 地址
					url: "http://localhost:8080/docollect",
					// 请求方式
					method: "POST",
					// 请求发送的数据
					data: this.user,
					// 接收响应
					//函数
					success: (res) => {
						console.log(res);
						//成功那么code=200，保存用户信息，返回到个人中心
						//如果失败，提示“用户名或者密码错误”
						if (res.data.code * 1 == 200) {
							//保存
							uni.setStorageSync("h", res.data.result);

							console.log("收藏成功");
							this.$u.toast("收藏成功");
						} else {
							//失败
							this.$u.toast("收藏失败");
						}
					}
				});

			},
		}
	};
</script>

<style lang="less">
	page {
		width: 100vw;
		height: 100vh;
		color: #000;
	}

	.ly_input {
		height: 150rpx;
		width: 750rpx;
		position: fixed;
		bottom: 0;
		box-shadow: -2rpx -6rpx 13rpx 0rpx rgba(0, 0, 0, 0.09);
		display: flex;
		align-items: center;
		justify-content: space-around;
		padding-bottom: constant(safe-area-inset-bottom);
		padding-bottom: env(safe-area-inset-bottom);

		input {
			width: 250rpx;
			height: 60rpx;
			border-radius: 30rpx;
			border: 1rpx solid #000000;
			padding-left: 10rpx;
		}

		.comment {
			font-size: 28rpx;
			color: #000;
			position: relative;

			.commentIndex {
				height: 20rpx;
				position: absolute;
				color: #000000;
				background: #000000;
				padding: 0 10rpx;
				font-size: 14rpx;
				border-radius: 10rpx;
				right: -10rpx;
				top: 0;
			}
		}


	}

	.u-content {

		border: 10px solid #ffffff;
		border-radius: 40px;
		padding: 15px;
		font-size: 25px;
		text-indent: 1em;
		left: 100;
		right: 100;
		// position: fixed;
		// bottom: 15rpx;
		// font-weight: 400;
		color: #000000;
		padding-bottom: 150px;


	}
</style>