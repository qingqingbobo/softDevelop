<template>
	<view class="content">
		<view class="content-container">
			<view class="item-container">
				<text class="item-top">{{data}}</text>
				<view class="item" style="flex-direction:row">
					<image bindtap="onMemberClick" class="item-left" mode="aspectFill" src="@/static/images/msgmsg.png"></image>
					<view class="item-right">
						<view class="item-right-text-container">
							<image class="item-right-arrow" src="@/static/images/pic_white_arrow.png">
							</image>
							<rich-text class="item-right-text" :selectable="true" :nodes="title"></rich-text>
						</view>
					</view>
				</view>
			</view>
			<view class="item-container">
				<text class="item-top">{{data}}</text>
				<view class="item" style="flex-direction:row">
					<image bindtap="onMemberClick" class="item-left" mode="aspectFill" src="@/static/images/msgmsg.png"></image>
					<view class="item-right">
						<view class="item-right-text-container">
							<image class="item-right-arrow" src="@/static/images/pic_white_arrow.png">
							</image>
							<rich-text class="item-right-text" :selectable="true" :nodes="content"></rich-text>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import API from '@/api/api.js';
	export default {
		data() {
			return {
				recive: '',
				title: '',
				data: '',
				content: '',
			}
		},
		onLoad(options) {
			this.getMessagedetails({
				id: options.id
			})
		},
		methods: {

			/**
			 * 获取消息内容
			 */
			getMessagedetails: function(data) {
				let that = this;
				API.getMessagedetail(data).then(res => {
					console.log(res);
					that.recive = res.user;
					that.title = res.title;
					that.data = res.date;
					that.content = res.content;
				})
			}
		}
	}
</script>

<style>
.user-avatar {
  position: relative;
  width: 90rpx;
  height: 90rpx;
  border-radius: 50%;
}

.chatOtherItem {
  position: relative;
  width: 690rpx;
  display: flex;
  margin-bottom: 50rpx;
  padding-left: 30rpx;
}

.otherText {
  position: relative;
  max-width: 470rpx;
  margin-top: 10rpx;
  margin-left: 20rpx;
  display: flex;
  flex-direction: column;
}

.otherElement {
  width: 0px;
  height: 0px;
  margin-top: 0rpx;
  border-top: 10rpx solid #7aaffa;
  border-right: 4rpx solid #7aaffa;
  border-bottom: 10rpx solid transparent;
  border-left: 4rpx solid transparent;
}

.otherTextCont {
  position: relative;
  background-image: linear-gradient(90deg,#7aaffa 0%,#8fc1ff 100%),linear-gradient(#fff,#fff);
  background-blend-mode: normal,normal;
  box-shadow: 0rpx 6rpx 14rpx 0rpx rgba(88,145,219,0.48);
  color: white;
  border-radius: 36rpx;
  border-top-left-radius: 0;
  font-size: 30rpx;
  padding: 20rpx;
  box-sizing: border-box;
}

.otherContImgIcon {
  width: 20px;
  height: 20px;
  display: inline-block;
  vertical-align: middle;
}

.otherImg {
  position: relative;
  background-position: 50% center;
  background-repeat: no-repeat;
  background-size: cover;
  height: 230rpx;
  width: 230rpx;
  margin-top: 10rpx;
  margin-left: 20rpx;
  border-radius: 5rpx;
}

.chatMyItem {
  width: 690rpx;
  display: flex;
  flex-direction: row-reverse;
  margin-bottom: 50rpx;
}

.myText {
  position: relative;
  max-width: 470rpx;
  margin-top: 10rpx;
  margin-right: 20rpx;
  display: flex;
  flex-direction: column;
}
.time{
padding-bottom: 20rpx;
color:#999;
}

.myElement {
  width: 0px;
  height: 0px;
  margin-top: 0rpx;
  border-top: 10rpx solid white;
  border-right: 4rpx solid transparent;
  border-bottom: 10rpx solid transparent;
  border-left: 4rpx solid white;
}

.myTextCont {
  position: relative;
  background-color: #fff;
  box-shadow: 0px 6px 14px 0px rgba(88,145,219,0.2);
  border-radius: 36rpx;
  border-top-right-radius: 0;
  font-size: 30rpx;
  padding: 20rpx;
  box-sizing: border-box;
  font-weight: normal;
  font-stretch: normal;
  letter-spacing: 0px;
  color: #323232;
}

.myImg {
  position: relative;
  background-position: 50% center;
  background-repeat: no-repeat;
  background-size: cover;
  height: 230rpx;
  width: 230rpx;
  margin-top: 10rpx;
  margin-right: 20rpx;
  border-radius: 5rpx;
}
.container {
  position: relative;
  width: 750rpx;
  min-height: 100%;
  background: #f8f8f8;
}

.main {
  position: relative;
  display: flex;
  flex-direction: column;
  width: 750rpx;
}

.goods {
  position: fixed;
  display: flex;
  width: 750rpx;
  padding: 18rpx 30rpx;
  border-top: solid 1px #f8f8f8;
  background: white;
  z-index: 99;
}

.goodsImg {
  position: relative;
  background-position: 50% center;
  background-repeat: no-repeat;
  background-size: cover;
  width: 165rpx;
  height: 165rpx;
}

.goodsInfo {
  position: relative;
  width: 495rpx;
  margin-left: 30rpx;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.goodsTitle {
  color: #323232;
  font-size: 28rpx;
  line-height: 30rpx;
  display: -webkit-box;
  word-break: break-all;
  text-overflow: ellipsis;
  overflow: hidden;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}

.goodsPrice {
  color: #f44;
  font-size: 32rpx;
  line-height: 34rpx;
}

.goodsButton {
  position: relative;
  display: flex;
  width: 495rpx;
  align-items: center;
  justify-content: flex-end;
}

.sendImg {
  color: #f44;
  font-size: 22rpx;
  border: solid 1px #f44;
  border-radius: 8rpx;
  width: 138rpx;
  height: 50rpx;
  display: flex;
  align-items: center;
  justify-content: center;
}

.pay {
  color: white;
  font-size: 22rpx;
  background-color: #f44;
  border-radius: 8rpx;
  width: 138rpx;
  height: 50rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 22rpx;
}

.chat {
  position: relative;
  width: 750rpx;
  padding: 30rpx;
  display: flex;
  flex-direction: column;
  padding-bottom: 150rpx;
}

.formTo {
  position: fixed;
  width: 750rpx;
  bottom: 0;
  left: 0;
  box-shadow: 0 -2rpx 5rpx 0rpx #32323238;
  background-color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 0 30rpx;
  box-sizing: border-box;
}

.formCont {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: space-between;
  min-height: 120rpx;
  width: 690rpx;
}

.more {
  width: 82rpx;
  height: 82rpx;
}

.reply {
  position: relative;
  width: 500rpx;
  height: 80rpx;
  box-sizing: border-box;
  background-color: #fafafa;
  padding: 0 35rpx;
  border-radius: 40rpx;
  font-size: 30rpx;
  border: solid #d3d3d3 1px;
}

.reply-area {
  position: relative;
  width: 420rpx;
  min-height: 80rpx;
  box-sizing: border-box;
  background-color: #fafafa;
  padding: 0 35rpx;
  border-radius: 40rpx;
  font-size: 30rpx;
  border: solid #d3d3d3 1px;
}

.send {
  width: 112rpx;
  height: 56rpx;
  font-size: 24rpx;
  color: #FFFFFF;
  border-radius: 28rpx;
  background: #2F78FF;
  text-align: center;
  line-height: 56rpx;
}

.moreList {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  width: 750rpx;
  padding: 0 30rpx;
  box-sizing: border-box;
  margin-bottom: 39rpx;
  border-top: solid 1px #d3d7dc;
}

.moreItem {
  margin-right: 42rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  margin-top: 46rpx;
}

.moreItemIconBox {
  width: 120rpx;
  height: 120rpx;
  background-color: #fafafa;
  border-radius: 10rpx;
  border: solid 1rpx #d3d3d3;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.moreIcon {
  width: 52rpx;
  height: 42rpx;
}

.moreItemName {
  margin-top: 20rpx;
  font-size: 26rpx;
  font-weight: normal;
  font-stretch: normal;
  letter-spacing: 0px;
  color: #888888;
}
.iphoneX72 {
  padding-bottom: 60rpx!important;
}

.private{
padding-bottom:80rpx;
}

.jz-chat-edit-view-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  box-sizing: border-box;
  background: #F2F2F2;
}

.jz-chat-edit-view-top {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  box-sizing: border-box;
  margin-top: 42rpx;
  padding: 0 48rpx;
}

.jz-chat-edit-view-tip {
  font-size: 24rpx;
  color: #999999;
}

.jz-chat-edit-view-text {
  font-size: 24rpx;
  color: #666666;
}

.jz-chat-edit-view-input {
  width: 710rpx;
  height: 80rpx;
  line-height: 80rpx;
  text-align: left;
  padding: 0 28rpx;
  font-size: 32rpx;
  color: #333333;
  margin-top: 20rpx;
  background: #FFFFFF;
  border-radius: 10rpx;
  box-sizing: border-box;
}

.jz-chat-edit-view-bottom {
  width: 100%;
  height: 126rpx;
  padding: 0 42rpx;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  box-sizing: border-box;
}

.jz-chat-edit-view-bottom-left {
  width: 50%;
  height: 100%;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  box-sizing: border-box;
}

.jz-chat-edit-view-emoji {
  width: 84rpx;
  height: 64rpx;
  padding-left: 20rpx;
  padding-right: 20rpx;
  padding-top: 10rpx;
  padding-bottom: 10rpx;
  border-radius: 10rpx;
  box-sizing: border-box;
}

.jz-chat-edit-view-emoji-show {
  background: #FFFFFF;
}

.jz-chat-edit-view-pic {
  width: 78rpx;
  height: 64rpx;
  padding-top: 10rpx;
  padding-bottom: 10rpx;
  box-sizing: border-box;
  padding-left: 14rpx;
  padding-right: 20rpx;
}

.jz-chat-edit-view-image {
  width: 106rpx;
  height: 106rpx;
  margin-left: 14rpx;
  border-radius: 10rpx;
}

.jz-chat-edit-view-confirm {
  width: 112rpx;
  height: 56rpx;
  font-size: 24rpx;
  color: #FFFFFF;
  border-radius: 28rpx;
  background: #2F78FF;
  text-align: center;
  line-height: 56rpx;
}

.jz-chat-emoji-scroll {
  width: 100%;
}

.jz-chat-emoji-content {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  flex-wrap: wrap;
  box-sizing: border-box;
}

.jz-chat-emoji-text {
  width: 125rpx;
  height: 80rpx;
  line-height: 80rpx;
  text-align: center;
  font-size: 52rpx;
}

.bottom-extra {
  position: fixed;
  width: 100%;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  background: #EFEFEF;
}

page {
  background: #F8F8F8;
}

.container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
}

.content-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  box-sizing: border-box;
  padding: 0 32rpx;
  padding-bottom: 250rpx;
}

.item-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  box-sizing: border-box;
  padding-top: 34rpx;
}

.item-top {
  font-size: 22rpx;
  color: #CCCCCC;
  margin-bottom: 32rpx;
}

.item {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
  justify-content: flex-start;
  align-items: flex-start;
  box-sizing: border-box;
}

.item-left {
  width: 80rpx;
  height: 80rpx;
  border-radius: 40rpx;
  margin-top: 6rpx;
  background: #EFEFEF;
}

.item-right {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  box-sizing: border-box;
  padding: 0 12rpx;
}

.item-right-me {
  align-items: flex-end;
}

.item-right-title {
  font-size: 32rpx;
  color: #576B95;
}

.item-right-text-container {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: flex-start;
  box-sizing: border-box;
}

.item-right-text-container-me {
  justify-content: flex-end;
}

.item-right-arrow {
  width: 12rpx;
  height: 24rpx;
  margin-top: 28rpx;
}

.item-right-arrow-me {
  width: 12rpx;
  height: 24rpx;
  margin-top: 28rpx;
}

.item-right-text {
  max-width: 482rpx;
  line-height: 44rpx;
  font-size: 32rpx;
  color: #333333;
  padding: 18rpx 20rpx;
  margin-top: 8rpx;
  box-sizing: border-box;
  background: #FFFFFF;
  border-top-left-radius: 8rpx;
  border-top-right-radius: 8rpx;
  border-bottom-left-radius: 8rpx;
  border-bottom-right-radius: 8rpx;
  word-wrap: break-word;
  word-break: break-all;
}

.item-right-text-me {
  border-top-left-radius: 8rpx;
  border-top-right-radius: 8rpx;
  border-bottom-left-radius: 8rpx;
  border-bottom-right-radius: 8rpx;
  background: #4C98FF;
  color: #FFFFFF;
}

.item-right-image {
  width: 208rpx;
  height: 332rpx;
  background: #EFEFEF;
  margin-top: 8rpx;
  border-top-left-radius: 8rpx;
  border-top-right-radius: 8rpx;
  border-bottom-left-radius: 8rpx;
  border-bottom-right-radius: 8rpx;
}

.item-right-image-me {
  border-top-left-radius: 8rpx;
  border-top-right-radius: 8rpx;
  border-bottom-left-radius: 8rpx;
  border-bottom-right-radius: 8rpx;
}

.bottom-extra {
  position: fixed;
  width: 100%;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  background: #EFEFEF;
}

.ad-container {
  position: fixed;
  left: 32rpx;
  bottom: 250rpx;
}
</style>
