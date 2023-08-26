<template>
	<view class="u-wrap">
		<view>
			<!-- 引入 u-grid 和 u-grid-item 组件 -->
			<u-grid :col="1">
				<u-grid-item v-for="(collect, index) in searchlist" :key="collect.postid" @click="goToB(collect)">
					<view class="movie-item">
						<view class="movie-title">{{ collect.title }}</view>
						<view class="image-content">
							<image class="movie-image" mode="aspectFill" :src="collect.thumbnall" @error="imageError">

							</image>
						</view>
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
				searchlist: [],
				current: 0,
			};
		},
		onLoad() {
			// 获取存储的用户信息
			const value2 = uni.getStorageSync("postkeyword");
			this.searchlist = value2 || []; // 将获取到的数据赋值给searchlist，若未获取到数据则默认为空数组

		},
		methods: {
			imageError(event) {
				// 图片加载失败时的处理函数
				console.log("图片加载失败", event);
			},
			goToB(collect) {

				var value3 = collect.postid;
				// var value4 = collect.uerid
				uni.setStorageSync("zzw", value3);
				uni.navigateTo({
					url: '/pages/content/content'
				});
			}
		},


	};
</script>


<style scoped lang="scss">
	@import "@/common/demo.scss";

	.movie-item {
		display: flex;
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
	}
</style>