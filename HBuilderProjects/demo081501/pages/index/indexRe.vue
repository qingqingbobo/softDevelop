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
        				<view class="image-item" @click="playRe(dy.id)">
        					<view class="image-content">
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
				title: true,
				mode: 'round',
				indicatorPos: 'bottomCenter',
				effect3d: false,
				//结束轮播图
				dianyings: [],
			}
		},
		computed: {

		},
		
		onLoad() {
			console.log("请求电影信息");
			
			uni.request({
				url: "http://localhost:8080/dianyings",
				method: "GET",
				
				success: (res) => {
					console.log(res);
					
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
			
			playRe(dyid) {
				console.log("播放");
				uni.navigateTo({
					url: "",
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	// @import "@/common/demo.scss";  
</style>