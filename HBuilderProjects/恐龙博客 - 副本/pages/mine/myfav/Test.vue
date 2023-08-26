<template>
	<view class="u-wrap">
	<view>
		<!-- 使用 <u-grid> 组件，并设置 col 属性为 1 -->
		<u-grid :col="1">
			<!-- 使用 v-for 循环遍历 dianyings 数组 -->
			<u-grid-item v-for="(collect,index) in myfav" :key="collect.postid">
				<view class="movie-item">
					<!-- 显示电影标题 -->
					<view class="movie-title">{{collect.title}}</view>
					<!-- 显示电影封面图片 -->
					<view class="image-content">
						<image class="movie-image" mode="aspectFill" :src="collect.thumbnall" @error="imageError"></image>
						<!-- <image class="movie-image" style="width: 120px; height: 150px; background-color: #eeeeee;" mode="aspectFill"
							src="/static/postimage/1.png" @error="imageError"></image> -->
					</view>
				</view>
			</u-grid-item>
		</u-grid>
	</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				myfav:[],
				current:0,
			}
		},
		methods: {
			
			// changeu(index) {
			// 				this.current = index;
			// 				this.title1 = this.categories[index]['name']
			// 				uni.request({
			// 					// 地址
			// 					// 地址
			// 					url: "http://localhost:8080/index?categories=" + this.title1,
			// 					// 请求方式
			// 					method: "POST",
			// 					// 请求发送的数据
			// 					data: this.passage,
			// 					// 接收响应
			// 					//函数
			// 					success: (res) => {
			// 						//成功则code为200  
			// 						if (res.data.code * 1 == 200) {
			// 							try {
			// 								this.name = res.data.result;
			// 								uni.setStorageSync('passage', res.data.result);
			
			
			
			// 							} catch (e) {
			// 								this.$u.toast('异常')
			// 							}
			
			// 						} else {}
			// 					}
			// 				})
			
			// 			}

			
			
		},
		
		// onLoad() {
		// 			//this.$u.route("/uview-ui/components/u-full-screen/u-full-screen?id=1");
		// 			uni.request({
		// 				url: "http://localhost:8080/index",
		// 				method: "GET",
		// 				success: (res) => {
		
		// 					console.log(res);
		// 					this.name = res.name;
		// 					// 			//更新电影集合
		// 					//this = res.data.result;
		// 				}
		
		// 			})
		
		// 		},




		
	onLoad() {
		console.log("1111111111111111111")
		//获取存储的用户信息
		
		const value2 = uni.getStorageSync("posts");//mine.vue里，user信息（包括收藏文章id）被存在"posts"中
		this.myfav = value2;//把数据赋值给user
		console.log("收藏页面跳转成功");
		console.log(this.myfav);
		
		
	},
	}
</script>

<style>
	
	.movie-item {
		display: flex;
		/* 将电影图片和标题放在一行 */
		align-items: center;
	
		justify-content: space-between;
	}
	
	.movie-image {
		width: 120px;
		height: 75px;
		background-color: #eeeeee;
		align-self: flex-end;
	
	
	}
	
	.movie-title {
		font-size: 15px;
		margin-right: 20px;
		width: 150px;
		height: 75px;
		align-items: center;
	
	
	
	}

</style>
