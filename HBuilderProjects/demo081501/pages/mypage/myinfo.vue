<template>
	<view v-if="logined">
		<u-navbar :is-back="false" title="　" :border-bottom="false">
			<view class="u-flex u-row-right" style="width: 100%;">
				<view class="camera u-flex u-row-center">
					<u-icon name="camera-fill" color="#000000" size="48"></u-icon>
				</view>
			</view>
		</u-navbar>
		<view class="u-flex user-box u-p-l-30 u-p-r-20 u-p-b-30">
			<view class="u-m-r-10">
				<u-avatar :src="pic" size="140"></u-avatar>
			</view>
			<view class="u-flex-1">
				<view class="u-font-18 u-p-b-20"> {{yonghu.xingming}} </view>
				<view class="u-font-14 u-tips-color"> {{yonghu.age}} 岁 </view>
			</view>
			<view class="u-m-l-10 u-p-10">
				<u-icon name="scan" color="#969799" size="28"></u-icon>
			</view>
			<view class="u-m-l-10 u-p-10">
				<u-icon name="arrow-right" color="#969799" size="28"></u-icon>
			</view>
		</view>
		
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="star" title="收藏"></u-cell-item>
				<u-cell-item icon="photo" title="相册"></u-cell-item>
				<u-cell-item icon="coupon" title="卡券"></u-cell-item>
			</u-cell-group>
		</view>
		
		<view class="u-m-t-20" @click="quit">
			<u-cell-group>
				<u-cell-item icon="setting" title="退出"></u-cell-item>
			</u-cell-group>
		</view>
	</view>
	
	<!-- 没有登录的时候只显示“登录”选项 -->
	<view class="u-m-t-20" v-else>
		<u-cell-group>
			<u-cell-item icon="man-add-fill" title="登录" @click="doLogin"></u-cell-item>
		</u-cell-group>
	</view>
	
</template>

<script>
	export default {
		data() {
			return {
				pic:'https://uviewui.com/common/logo.png',
				show:true,
				logined: false,
				yonghu : {
					xingming: "游客",
					age: "18"
				}
			}
		},
		onLoad() {
			
		},
		
		// 当页面展示的时候会调用
		onShow() {
			console.log("界面显示");
			// 1. 获取存储的用户信息
			const value = uni.getStorageSync("user");
			// 把数据赋值给变量 yonghu
			this.yonghu = value;
			
			console.log(value);
			// 2. 判断用户信息是否存在。如果不存在，logined = false；如果存在，logined = true
			if (value) {
				this.logined = true;
			}
			else {
				this.logined = false;
			}
		},
		
		methods: {
			doLogin() {
				uni.navigateTo({
					url: '/pages/mypage/login',

				});
			},
			// 退出
			quit () {
				// 清空本地存储的值
				uni.setStorageSync("user", "");
				// 修改登录状态
				this.logined = false;
			}
		}
	}
</script>

<style lang="scss">
page{
	background-color: #ededed;
}

.camera{
	width: 54px;
	height: 44px;
	
	&:active{
		background-color: #ededed;
	}
}
.user-box{
	background-color: #fff;
}
</style>
