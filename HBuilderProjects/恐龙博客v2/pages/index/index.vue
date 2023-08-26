<template>
	<view class="u-wrap">
		<view class="u-demo-wrap">
			<view class="u-demo-title">首页
			</view>


			<view class="u-demo-area">
				<u-toast ref="uToast">

				</u-toast>
				<view class="search-box" @click="turn">
					<input type="text" placeholder="请输入关键词" @search="handlesearch">
				</view>
				<view class="search-results">
					<!-- 根据搜索结果渲染列表等内容 -->
					<view v-for="result in searchResults" :key="result.id">
						{{ result}}
					</view>
				</view>
				<u-swiper @change="change" :height="250" :list="list" :title="title" :effect3d="effect3d"
					:indicator-pos="indicatorPos" :mode="mode" :interval="3000" @click="click">
				</u-swiper>
				<!-- 公告 -->
				<u-collapse>
					<u-collapse-item :title="item.head" v-for="(item, index) in itemList" :key="index">
						{{item.body}}
					</u-collapse-item>
				</u-collapse>
				<!-- 公告 end -->
				<u-tabs :list="categories" :is-scroll="true" :current="current" @change="changeu">
					<u-grid-item v-for="(dy,index) in name" :key="dy.postid" @click="pageclick">
						<text>{{dy.title}}</text>

					</u-grid-item>
				</u-tabs>
			</view>

		</view>

		<view>
			<!-- 使用 <u-grid> 组件，并设置 col 属性为 1 -->
			<u-grid :col="1">
				<!-- 使用 v-for 循环遍历 dianyings 数组 -->
				<u-grid-item v-for="(dy,index) in name" :key="dy.postid" @click="goToB(dy)">
					<view class="movie-item">
						<!-- 显示电影标题 -->
						<view class="movie-title">{{dy.title}}</view>
						<!-- 显示电影封面图片 -->
						<image class="movie-image" mode="aspectFill" :src="dy.thumbnall" @error="imageError">
						</image>
					</view>
				</u-grid-item>
			</u-grid>
		</view>
		<u-divider>没有更多了</u-divider>
	</view>

</template>
<script>
	export default {
		data() {
			return {
				searchValue: '', // 用于存储搜索框的值
				searchResults: [], // 用于存储搜索结果
				title1: "",
				name: [],
				//轮播图
				list: [{
						image: '/static/images/cquu.jpeg',

					},
					{
						image: '/static/images/中软国际.jpg',

					},
					{
						image: '/static/images/csdn.jpg',

					},
				],
				title: true,
				mode: 'round',
				indicatorPos: 'bottomCenter',
				effect3d: false,
				backmessage: "",
				//结束轮播图
				//控制页面显示多少个 


				itemList: [{
					head: "公告",

					body: "本博客由中国科学院重庆分院第一小组制作，十分感谢各位用户提出宝贵的建议",
					open: true,
					disabled: true
				}, {
					head: "重要提示",


					body: "本博客旨在分享、交流技术共同进步，希望所有用户一起维护博客氛围",
					open: false,
				}, ],
				
				categories: [{
					name: '全部'
				}, {
					name: 'AIGC'
				}, {
					name: 'java'
				}, {
					name: 'python'
				}, {
					name: '人工智能'
				}, {
					name: '其他'
				}, {
					name: '前沿技术'
				}, {
					name: '前端'
				}, {
					name: '后端'
				}, {
					name: '大数据'
				}, {
					name: '小程序'
				}, {
					name: '数学'
				}, {
					name: '数据库'
				}, {
					name: '数据结构与算法'
				}, {
					name: '服务器'
				}, {
					name: '硬件'
				}, {
					name: '移动开发'
				}, {
					name: '编程语言'
				}, {
					name: '资讯'
				}],
				current: 0,

			}


		},
		computed: {

		},
		//一加载请求电影信息
		onLoad() {

			// uni.request({
			// 	url: "http://localhost:8080/index",
			// 	method: "GET",
			// 	success: (res) => {
					
			// 		console.log(res);
			// 		this.name = res.name;
			// 		console.log("this.name: ");
			// 		console.log(this.name);
			// 		// 			//更新电影集合
			// 		//this = res.data.result;
			// 	}

			// })
			uni.request({
				// 地址
				// 地址
				url: "http://localhost:8080/index?categories=" + "全部",
				// 请求方式
				method: "POST",
				// 请求发送的数据
				// data: this.user,
				// 接收响应
				//函数
				success: (res) => {
					//成功则code为200  保存用户信息 
					if (res.data.code * 1 == 200) {
						try {
							this.name = res.data.result;
							uni.setStorageSync('users', res.data.result);
						} catch (e) {
							this.$u.toast('异常')
						}
			
					} else {}
				}
			})

		},
		methods: {


			//轮播图
			click(index) {
				this.$refs.uToast.show({
					title: `第${index + 1}个广告位`,
					type: 'success'
				})
				if (index == 0) {
					window.location.href = "https://cqu.edu.cn/"
				}
				// if (index == 1) {
				// 	window.location.href = "https://www.zhihu.com/"
				// }
				if (index == 2) {
					window.location.href = "https://www.csdn.net/"
				}
			},
			turn() {
				uni.navigateTo({
					url: "../search/search"
				});
			},

			change(index) {}
			//结束轮播图
			,

			imageError() {},
			changeu(index) {
				this.current = index;
				this.title1 = this.categories[index]['name']
				uni.request({
					// 地址
					// 地址
					url: "http://localhost:8080/index?categories=" + this.title1,
					// 请求方式
					method: "POST",
					// 请求发送的数据
					// 接收响应
					//函数
					success: (res) => {
						//成功则code为200  保存用户信息 
						if (res.data.code * 1 == 200) {
							try {
								this.name = res.data.result;
								uni.setStorageSync('users', res.data.result);
							} catch (e) {
								this.$u.toast('异常')
							}

						} else {}
					}
				})

			},
			goToB(collect) {

				var value3 = collect.postid;
				console.log("value3");
				console.log(value3);
				// var value4 = collect.uerid
				uni.setStorageSync("zzw", value3);
				uni.navigateTo({
					url: '/pages/content/content'
				});
			}

		}
	}
</script>
<style scoped lang="scss">
	@import "@/common/demo.scss";

	.search-results {
		margin-top: 20px;
	}

	// .search-box {
	// 	width: 100%;
	// 	height: 50px;
	// 	background-color: #ffffff;
	// 	border-radius: 5px;
	// 	padding: 0 10px;
	// 	display: flex;
	// 	align-items: center;

	// 	input {
	// 		flex: 1;
	// 		border: none;
	// 		outline: none;
	// 		font-size: 16px;
	// 		color: #333333;
	// 		background-color: transparent;
	// 	}
	// }
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
		font-size: 16px;
		margin-right: 20px;
		width: 200px;
		height: 75px;
	}
	
	.search-box {
			border: 1px solid #ccc;
			/* 设置边框样式 */
			border-radius: 4px;
			/* 设置边框圆角 */
			padding: 5px;
			/* 可选：设置内边距 */
			/* 这里可以根据需要继续添加其他样式 */
		}
		.u-demo-wrap {
				
				background: linear-gradient(136deg, #ebfcfa, #f4f6f6);
				/* 设置从中心向四周的红绿渐变 */
			}
</style>