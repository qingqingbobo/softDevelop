<template>
	<view class="content">
		<view class="card-bg">
			<leo-list :show-icon="true" :show-arrow="true" :list="list"></leo-list>
		</view>
	</view>
</template>

<script>
	import leoList from '@/components/leo-list/leo-list.vue'
	export default {
		data() {
			return {
				// list: [{
				// 	name: "主标题",
				// 	subTitle: "副标题显示内容",
				// 	icon: "/static/icon2.png",
				// 	path: "",
				// }
				
				// ]
				list:[]
			}
		},
			
		components: {
			leoList
		},
		onLoad() {
			console.log("获取作品列表");
					
			var value = uni.getStorageSync("user");
			console.log("value");
			console.log(value.userid);
			uni.request({
				url: "http://localhost:8080/home",
				method: "POST",
				data: {userid: value.userid},
				success: (res) => {
					console.log("res: ");
					console.log(res);
					this.list = res.data.result;
					
				}
						
			})
		},
		methods: {

		}
	}
</script>

<style lang="scss" scoped>
	page {
		background: #f5f7fa;
	}
	.content {
		/* #ifdef APP-PLUS */
		padding-top: var(--status-bar-height);
		/* #endif */
		/* #ifndef APP-PLUS */
		padding-top: 1rpx;
		/* #endif */
		padding-bottom: 1rpx;
		// min-height: calc(100vh - var(--status-bar-height));
		// background: linear-gradient(to, #3377ff,  #f5f7fa 564rpx);
		
		background: #f5f7fa;
	}
	
	.card-bg {
		margin: 24rpx 24rpx;
		padding: 24rpx 32rpx;
		background: #ffffff;
		border-radius: 20rpx;
		box-shadow: 0px 4rpx 20rpx 0rpx rgba(235,241,244,0.35);
	}
</style>
