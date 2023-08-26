<template>
	<view class="u-wrap">
		<view class="u-demo-wrap">
			<view class="u-demo-title">热映大片</view>
			<view class="u-demo-area">
				<u-toast ref="uToast"></u-toast>
				<u-swiper @change="change" :height="250" :list="list" :title="title" :effect3d="effect3d"
					:indicator-pos="indicatorPos" :mode="mode" :interval="3000" @click="click"></u-swiper>
			</view>
		</view> 
		<view>
			<u-grid :col="3">
				<u-grid-item v-for="(dy, index) in dianyings" :key="dy.id">
					<view class="image-item" @click="play(dy.id)">
						<view class="image-content">
							<!-- index % 9 -> src 会变化，加上 : -->
							<image style="width: 120px; height: 150px; background-color: #eeeeee;" mode="aspectFill"
								:src="'../../static/filmpics/film' + (index % 9 + 1) + '.jpeg'" @error="imageError"></image>
						</view>
						<view class="image-title"> {{dy.ming}} </view>
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

				//轮播图
				list: [{
						image: '../../static/filmpics/film7.jpeg',
						title: '昨夜星辰昨夜风，画楼西畔桂堂东'
					},
					{
						image: '../../static/filmpics/film8.jpeg',
						title: '身无彩凤双飞翼，心有灵犀一点通'
					},
					{
						image: '../../static/filmpics/film9.jpeg',
						title: '谁念西风独自凉，萧萧黄叶闭疏窗，沉思往事立残阳'
					}
				],
				mytitle: "电影首页",
				title: true,
				mode: 'round',
				indicatorPos: 'bottomCenter',
				effect3d: false,
				//结束轮播图
				// 定义电影集合
				dianyings: [],
				
			}
		},
		computed: {

		},
		onLoad() {
			uni.request({
				url: "http://localhost:8080/dianyings",
				method: "GET",
				
				success: (res) => {
					console.log(res);
					
					// 更新电影集合
					this.dianyings = res.data.result;
				}
			})
		},
		
		methods: {

			//轮播图
			click(index) {
				this.$refs.uToast.show({
					title: `点击了第${index + 1}张图片`,
					type: 'success'
				})
			},
			change(index) {
				// console.log(index);
			}
			//结束轮播图

			,
			imageError() {

			},
			play(dyid) {
				console.log("播放");
				uni.navigateTo({
					url: "/pages/mypage/play",
					
				})
			}
		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
