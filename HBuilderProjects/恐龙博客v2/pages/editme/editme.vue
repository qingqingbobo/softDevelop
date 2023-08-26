<template>
	<view class="content">
		<uni-forms ref="form" :model="user">
		    <!-- <view class="header">
			    <view class="photo" @click="chooseImage()">
				    <image :src="user.avatart"
					    mode="aspectFill" class="avatar"></image>
				    <view class="upload">
					<image class="img" src="@/static/images/camera.png"></image>
				</view>
			</view>
		</view> -->
		<view>
			<u-upload ref="uUpload" :action="action" :auto-upload="false" :form-data="upFileData" ></u-upload>
			<!-- <u-button @click="submit">提交</u-button> -->
		</view> 
		
		<view class="forms">
			<view class="form-item">
				<view class="label" prop="name">昵称<label class="count void "></label>
				</view>
				<view class="box">
					<input class="text" maxlength="20" placeholder="请输入昵称" v-if="!popupShow"  v-model="user.name"></input>
				</view>
			</view>
			
			<view class="form-item">
				<view class="label" prop="signature">个性签名<label class="count"></label>
				</view>
				<view class="box">
					<textarea :autoHeight="true" class="area high" :holdKeyboard="false" maxlength="60" placeholder="请输入..."  v-if="!popupShow"  v-model="user.signature"></textarea>
					<view :class="!signature?'gray':''" v-else>
						<rich-text :nodes="descText"></rich-text>
					</view>
				</view>
			</view>
			
			<view class="form-item">
				<view class="label" prop="province">省份<label class="count void "></label>
				</view>
				<view class="box">
					<input class="text" maxlength="10" placeholder="请输入省份" v-if="!popupShow"  v-model="user.province"></input>
				</view>
			</view>
			
			<view class="form-item">
				<view class="label" prop="city">城市<label class="count void "></label>
				</view>
				<view class="box">
					<input class="text" maxlength="10" placeholder="请输入城市" v-if="!popupShow"  v-model="user.city"></input>
				</view>
			</view>
			
			<view class="cu-form-group">
			        <view class="title">性别</view>
			        <radio-group @change="radioChange">
			            <label class="uni-list-cell uni-list-cell-pd" v-for="(item, index) in gender" :key="item.value">
			                <radio :value="item.value" :checked="item.checked" /><span>{{item.label}}</span>
			            </label>
			        </radio-group>
			        <!-- <view class="padding">
			            <button class="cu-btn block bg-blue margin-tb-sm lg" @click="confirm">保存</button>
			        </view> -->
			    </view>
		</view>
		    
		</uni-forms>
		<view class="submit">
			<u-button @click="saveInfo" ripple="true" class="btn">保存</u-button>
		</view>
	</view>
</template>

<script>
import { user } from '../../api/auth';
	// import API from '@/api/api.js'
	var app = getApp();
	export default {
		data() {
			return {
				
				action: 'http://localhost:8080/upload',
				upFileData: {
					id: uni.getStorageSync("user").userid,
					type: 'number'
				},
				popupShow: false,
				userImage:'',
				user:{
					avatart:null,
					name: null,
					province: null,
					city:null,
					signature: null,
					gender:null,
					userid:uni.getStorageSync("user").userid
				},
				gender: [{
				                    label: "男",
				                    value: "男",
				                    checked: true
				                }, {
				                    label: "女",
				                    value: "女"
				                }]
				                // addressData: {
				                //     gender: "男",
				                // }
			}
		},
		onShow() {
			// let user = app.globalData.user
			//  //    if (!user) {
			//  //      uni.showToast({
			//  //        title: '未登录,请登录',
			//  //      })
			//  //      uni.navigateTo({
			//  //        url: '/pages/login/login',
			//  //      })
			//  //    }
			// 	// else{
			// 		this.userImage=user.avatarUrl;
			// 		this.signature=user.signature;
			// 		this.name=user.name;
			// 		this.province=user.province;
			// 		this.city=user.city;
			// 		this.userid=uni.getStorageSync("user").userid;
				// }
				
		},
		methods: {
			chooseImage(){
				uni.navigateTo({
					url:"/pages/uploadFile/uploadFile"
				})
							// uni.chooseImage({
							// 	count:1,
							// 	sizeType: ['original', 'compressed'],
							// 	success: (res) => {
							// 		const tempFilePaths =res.tempFilePaths//图片临时数组
							// 		console.log(tempFilePaths)
							// 		uni.uploadFile({
							// 		//请求的url接口地址
							// 			url:"http://localhost:8080/Editdata",
							// 			fileType:'image',//图片类型
							// 			filePath:tempFilePaths[0],//哪张图片
							// 			name:'avatart',//对应接口的文件名称
							// 			header:{//请求头
							// 				"Content-Type": "multipart/form-data"
							// 			},
							// 			success:(res)=>{
							// 			//成功的回调
							// 				//一般用于重新获取数据渲染页面
							// 				console.log(res)
							// 			},
							// 			fail:(err)=>{
							// 			//失败的回调
							// 				console.log(err)
							// 			}
							// 		})
							// 	}
							// })
						},

			radioChange(e) {
			                this.addressData.gender = this.radio = e.detail.value
			            },
			saveInfo:function(){
				 console.log(this.user);
				 
				 this.$refs.uUpload.upload();
				 console.log("头像已上传");
				 
				 console.log(this.user);
				 
				 setTimeout(() => {
				   // 在2秒后执行
				   console.log('Hello'); 
				 }, 2000);
				 
				 uni.request({
				 	url:"http://localhost:8080/Editdata",
					method:"POST",
					// 请求发送的数据
					data:this.user,
					// 接收响应
					
					success:(res)=>{
						// console.log(args);
						console.log(res);
						if(res.data.code *1==200){
								// console.log(res.data.result);
							uni.setStorageSync('user',res.data.result);
								//
							uni.navigateBack()
								
						}else{
							console.log(res);
							this.$u.toast('提交失败')  //提示框
						}
					}
				 })
				
			},	
		}
	}
</script>

<style lang="scss" scoped>
	.header {
		padding: 60rpx 0;
		text-align: center;
		margin-bottom: 20rpx;

		.photo {
			width: 200rpx;
			height: 200rpx;
			margin: auto;
			position: relative;

			.avatar {
				width: 100%;
				height: 100%;
				border-radius: 50%;
				margin-bottom: 10rpx;
				background-size: cover;
			}

			.upload {
				width: 48rpx;
				height: 48rpx;
				padding: 10rpx;
				background: #3e3e3f;
				border-radius: 50%;
				position: absolute;
				right: 0;
				bottom: 0;

				.img {
					width: 100%;
					height: 100%;

				}
			}
		}

	}

	.forms {
		padding: 0 30rpx;

		.form-item {
			width: 100%;

			.label {
				margin: 28rpx 0 10rpx;
				color: #4a4a4a;

				.count {
					font-size: 24rpx;
					color: #999;
				}
			}

			.box {
				width: 100%;
				padding: 4rpx 0;
				position: relative;
				z-index: 1;

				.text {
					display: block;
				}

				.area {
					width: calc(100% - 60rpx);
					height: 80rpx;
					line-height: 40rpx;
					display: block;
					background: #f8f8f9;
					border-radius: 12rpx;
					padding: 20rpx 30rpx;
					border: 0;
					font-size: 32rpx;
					vertical-align: middle;
				}

				.high {
					min-height: 300rpx;
					max-height: 560rpx;
					margin-bottom: 40rpx;
					overflow: hidden;
				}

				.text {

					width: calc(100% - 60rpx);
					height: 100rpx;
					line-height: 100rpx;
					background: #f8f8f9;
					border-radius: 12rpx;
					padding: 0 30rpx;
					font-weight: 400;
					font-size: 32rpx;

				}

				.gray {
					color: #888;
				}

			}

		}
	}

	.submit {
		width: 100%;
		background-color: #fff;
		padding: 20rpx 50rpx;
		box-sizing: border-box;
		z-index: 9;

		.btn {
			width: 80%;
			height: 100rpx;
			line-height: 100rpx;
			border-radius: 50rpx;
			background: #3e3e3f;
			color: #fff;
			font-size: 32rpx;
		}
	}
</style>
