<template>
	<view class="page" :model="postData">
		<view class="feedback-body">
			<input v-model="postData.title" placeholder="   请输入博客标题" />
			<textarea placeholder="请输入内容(强制HTML文件)" style="border-bottom:1px solid   #F1F1F3;"
				v-model="postData.content" class="feedback-textare" />
		</view>
		<choose :count="count" :imgList="imgList" @changes="fileChange"></choose>
		<compress ref="compress" :maxwh="maxwh" :quality="quality"> </compress>


		<view class="swiper-list">
			<view class="uni-list-cell uni-list-cell-pd feedback-title">
				<view class="uni-list-cell-db ">是否允许评论</view>
				<switch :checked="isYasuo" @change="changeIndicatorDots" />
			</view>
		</view>
		<!-- 标签 -->
		<view class='feedback-title'>
			<text>标签：</text>
		</view>
		<view class="feedback-body">
			<input class="feedback-input" v-model="postData.tags" placeholder="请输入标签" />
		</view>
		<!-- 类别 -->
		<view class='feedback-title'>
			<text>类别：</text>
		</view>
		<view class="feedback-body">
			<input class="feedback-input" v-model="postData.categories" placeholder="请输入类别" />
		</view>

		<button type="default" class="feedback-submit" @tap=" publish">提交</button>

	</view>
</template>

<script>
	import choose from "../../components/template/image/choose.vue"
	import compress from "../../components/template/image/compress.vue"
	export default {
		name: 'newsPublish',
		components: {
			choose,
			compress
		},
		data() {
			return {
				isYasuo: true,
				count: 6,
				maxwh: 280,
				quality: 1,
				// title: '发布博客',
				msgContents: ["界面显示错乱", "启动缓慢，卡出翔了", "UI无法直视，丑哭了", "偶发性崩溃"],
				stars: [1, 2, 3, 4, 5],
				imgList: [],
				// sendDate: {
				// 	score: 0,
				// 	content: "",
				// 	contact: "",
				// }
				postData: {

					title: '',
					categories: '',
					content: '',
					tags: '',
					allowcomment: 1,
					userid: 1

				},
			}
		},
		onLoad() {

		},
		methods: {
			compressImg(e) {
				console.log(e)
			},
			changeIndicatorDots(e) {
				this.isYasuo = !this.isYasuo
			},
			// fileChange(e) {
			// 	this.imgList = e;
			// },
			// chooseStar(e) { //点击评星
			// 	this.sendDate.score = e;
			// },
			// previewImage() { //预览图片
			// 	uni.previewImage({
			// 		urls: this.imgList
			// 	});
			// },

			publish() {
				// 进行发布博客的逻辑处理
				// 可以通过调用后端接口将博客标题和内容发送到后端进行处理
				// 例如使用uni.request或axios等网络请求库发送POST请求
				console.log("publish");
				uni.request({
					url: 'http://localhost:8080/publish',
					method: 'POST',
					data: this.postData,
					success: (res) => {
						console.log(res);
						//成功那么code=200，保存用户信息，返回到个人中心
						//如果失败，提示“用户名或者密码错误”
						if (res.data.code * 1 == 200) {
							//保存
							// uni.setStorageSync("user", res.data.result);
							// uni.navigateBack(); //退回上一个页面
							this.$u.toast("发布成功");
						} else {
							//失败
							this.$u.toast("发布失败");
						}
					}
				});
			}

			// send() { //发送提交

			// 	function requst(imgs, data) {
			// 		console.log(JSON.stringify(imgs));
			// 		uni.uploadFile({
			// 			url: "https://service.dcloud.net.cn/feedback",
			// 			files: imgs,
			// 			formData: data,
			// 			success: (res) => {
			// 				if (res.statusCode === 200) {
			// 					uni.showToast({
			// 						title: "发布成功!"
			// 					});
			// 					this.imgList = [];
			// 					this.sendDate = {
			// 						score: 0,
			// 						content: "",
			// 						contact: ""
			// 					}
			// 				}
			// 			},
			// 			fail: (res) => {
			// 				// console.log(res)
			// 			}
			// 		});
			// 	}

			// 	if (this.isYasuo) {

			// 		this.$refs.compress.yasuoImg(this.imgList).then(e => {
			// 			// console.log([this.imgList,e])
			// 			let imgs = e.map((value, index) => {
			// 				//var base64= value.tempFilePath
			// 				return {
			// 					name: "image" + index,
			// 					uri: value.path,
			// 					base64: value.tempFilePath

			// 				}
			// 			})
			// 			// console.log(imgs)
			// 			requst(imgs, this.sendDate)
			// 		})
			// 	} else {

			// 		let imgs = this.imgList.map((value, index) => {
			// 			return {
			// 				name: "image" + index,
			// 				uri: value.path,
			// 				base64: value.tempFilePath
			// 			}
			// 		})
			// 		requst(imgs, this.sendDate)
			// 	}
			// }


		}
	}
</script>

<style>
	page {
		background-color: #EFEFF4;
	}

	.input-view {
		font-size: 28upx;
	}

	.close-view {
		text-align: center;
		line-height: 14px;
		height: 16px;
		width: 16px;
		border-radius: 50%;
		background: #FF5053;
		color: #FFFFFF;
		position: absolute;
		top: -6px;
		right: -4px;
		font-size: 12px;
	}
</style>