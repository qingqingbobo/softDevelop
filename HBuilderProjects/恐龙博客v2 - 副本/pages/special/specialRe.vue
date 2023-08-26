<template>
	<view class="content">
			<view class="msg_16">
				<view class="msg_1">
					<image v-on:click="msg_17_17_click()"  src="/static/images/msg_17_17.jpg" mode="scaleToFill" border="0"    class="msg_17"></image>
					<text decode="true" class="title">消息中心</text>
				</view>
				<view class="msg_2">
					<view class="msg_12">
						<image v-on:click="icon_4_click()"  src="/static/images/mail.png" mode="scaleToFill" border="0"    class="icon"></image>
						<text decode="true" class="word">评论</text>
					</view>
					<view class="msg_13">
						<image v-on:click="icon_5_click()"  src="/static/images/shouca.png" mode="scaleToFill" border="0"    class="icon"></image>
						<text decode="true" class="word">收藏</text>
					</view>
					<view class="msg_14">
						<image v-on:click="icon_6_click()"  src="/static/images/zan.png" mode="scaleToFill" border="0"    class="icon"></image>
						<text decode="true" class="word">点赞</text>
					</view>
				</view>
				<view class="Messages">
				<view v-for="(item_Messages, index) in list_Messages" :key="index" >
		
					<view class="row">
						<image v-on:click="icon_40_click(item_Messages)"  :src="item_Messages.icon" mode="scaleToFill" border="0"    class="icon"></image>
						<view class="msg_41">
							<view class="msg_42">
								<text decode="true" class="subject">{{item_Messages.subject}}</text>
								<text decode="true" class="time">{{item_Messages.time}}</text>
							</view>
							<view class="msg_43">
								<text decode="true" class="smallTitle">{{item_Messages.smallTitle}}</text>
								<text decode="true" class="vote">{{item_Messages.vote}}</text>
							</view>
						</view>
					</view>
				</view>
				</view>
			</view>
		

		<view class="loading">{{loadingText}}</view>
		<view class="ymBbottom"></view>
	</view>
</template>

<script>
import msg from "./msg.js";
export default msg;
</script>

<style lang="scss" scoped>
   @import './msg.scss'
</style>