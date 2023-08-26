<template>
	<view style="display: flex;flex-direction: row;">
		<!-- 管理功能选项栏 -->
		<view style="display: flex;flex-direction: column;width: 220upx;background-color: #001529;">
			<image src="../../static/admin.png" mode="aspectFill" class="admin_pic"></image>
			<view v-for="(item,index) in functionList" :key=index>
				<view style="margin-top: 20upx;padding: 20upx;width: 180upx;display: flex;flex-direction: row;color: #FFFFFF;font-size: 15px;align-items: center;">
					<image :src="item.icon" style="width: 15upx;height: 15upx;margin-right: 5upx;"></image>
					{{item.name}}
					<image src="../../static/arrow.png" class="arrow"></image>
				</view>
				<view v-for="(items,indexs) in item.page" @click="menu_change(items)" :key=indexs :class="menu_id==items.id?'choose_detail':'detail'">
					{{items.name}}
				</view>
			</view>
		</view>
		<!-- 管理功能选项栏end -->
		<!-- 管理具体界面 -->
		<scroll-view scroll-y='true' style="background-color: #F6F8F9;width: 100%;height: 100vh;">
			<view style="box-shadow: -1px -1px 5px #888888;width: 100%;background-color: #FFFFFF;height: 50upx;display: flex;flex-direction: row;align-items: center;font-size: 17px;">
				<image src="../../static/admin1.png" style="width: 20upx;height: 20upx;margin-left: 25upx;"></image>
				<view style="margin-left: 5upx;">
					后台管理
				</view>
				<view style="margin-left: 5upx;display: flex;justify-content: center;align-items: center;">
					<image src="../../static/arrow1.png" class="arrow1"></image>
				</view>
				<view style="margin-left: 5upx;">
					{{name}}
				</view>
				<image src="http://t7.baidu.com/it/u=1880899253,2364657333&fm=193" style="width: 40upx;height: 40upx;border-radius: 50%;margin-left: auto;"></image>
				<!-- 后面可以改成用户名 -->
				<view style="color: #6C6E72;font-weight: bold;padding: 0 10upx 0 10upx;">
					admin {{adminName}}
				</view>
				<image src="../../static/arrow2.png" class="arrow1" style="margin-right: 10upx;"></image>
			</view>
			
			
			<swiper :current='menu_id' disable-touch=true style="width: 100%;height: 100%;display: flex;justify-content: center;align-items: center;">
				<swiper-item>
					<scroll-view scroll-y="true" style="height: 98vh;">
						<!-- 人员管理id=0 -->
						<person_admin :PageNum='PageNum' :Num='Num' :PersonList='PersonList' style="margin-top: -30upx;width: 100%;height: 90%;display: flex;justify-content: center;align-items: center;"></person_admin>
						<!-- 人员管理end-->
					</scroll-view>
					
				</swiper-item>

				<swiper-item>
					<scroll-view scroll-y="true" style="height: 150vh;">
						<!-- 文章管理id=2 -->
						<shopping_admin :PageNum='PageNum_shop' :Num='Num_shop' :PersonList='ShopList' style="margin-top: -30upx;width: 100%;height: 90%;display: flex;justify-content: center;align-items: center;"></shopping_admin>
						<!-- 文章管理end-->
					</scroll-view>
					
				</swiper-item>
				
				<swiper-item style="display: flex;justify-content: center;align-items: center;flex-direction: column;">
					<scroll-view scroll-y="true" style="height: 98vh;">
						<!-- 投诉管理id=3 -->
						<feedbackAdmin :PageNum='PageNum_feedback' :Num='Num_feedback' :PersonList='feedbackList' style="width: 100%;"></feedbackAdmin>
						<!-- 投诉管理end-->
						
					</scroll-view>
					
				</swiper-item>
				
				<swiper-item style="display: flex;justify-content: center;align-items: center;flex-direction: column;">
					<scroll-view scroll-y="true" style="height: 98vh;">
						<!-- 登录日志管理id=3 -->
						<loginLogAdmin :PageNum='PageNum_loginLog' :Num='Num_loginLog' :PersonList='loginLogList' style="width: 100%;"></loginLogAdmin>
						<!-- 登录日志管理end-->
						
					</scroll-view>
					
				</swiper-item>
				
				<swiper-item style="display: flex;justify-content: center;align-items: center;flex-direction: column;">
					<scroll-view scroll-y="true" style="height: 98vh;">
						<!-- 操作日志管理id=3 -->
						<editLogAdmin :PageNum='PageNum_editLog' :Num='Num_editLog' :PersonList='editLogList' style="width: 100%;"></editLogAdmin>
						<!-- 操作日志管理end-->
						
					</scroll-view>
					
				</swiper-item>
				
			</swiper>
			
		</scroll-view>
		<!-- 管理具体界面end -->
		
	</view>
</template>

<script>
	import uniPopup from "@/components/uni-popup/uni-popup.vue"
	import person_admin from "@/components/admin-components/person_adminRe.vue"
	import shopping_admin from "@/components/admin-components/post_adminRe.vue"
	import feedbackAdmin from "@/components/admin-components/feedback_admin.vue"
	import loginLogAdmin from "@/components/admin-components/loginLog_admin.vue"
	import editLogAdmin from "@/components/admin-components/editLog_admin.vue"
	
	export default{
		components: {
			uniPopup,
			person_admin,
			shopping_admin,
			feedbackAdmin,
			loginLogAdmin,
			editLogAdmin
		},
		data(){
			return{
				menu_id:0,
				search_word:'',
				
				adminName: '',
				
				add_accout:'',
				add_password:'',
				choose_all_:false,
				checkbox_status:[],
				checkbox:false,
				accout:'',
				password:'',
				person_id:1,
				add_time:'',
				input_confirm:-1,
				NowPage:1,
				name:'人员管理',
				functionList:[
					{
						name:'人员管理',
						icon:'/static/personAdmin.png',
						// id:1,
						open:false,
						page:[
							{
								name:'用户管理',
								id:0
							},
							// {
							// 	name:'admin管理',
							// 	id:1
							// }
						]
					},
					{
						name:'博客管理',
						icon:'/static/shopAdmin.png',
						// id:4,
						open:false,
						page:[
							{
								name:'文章列表',
								id:1
							},
							{
								name:'投诉列表',
								id:2
							},
							{
								name:'登录日志列表',
								id:3
							},
							{
								name:'编辑日志列表',
								id:4
							}
						]
					}
				],
				// 人员管理数据
				PersonList:[],
				Num:1,
				PageNum:1,
				// 人员管理数据end
				
				// 商品管理数据
				ShopList:[],
				Num_shop:1,
				PageNum_shop:1,
				// 商品管理数据end
				
				feedbackList:[],
				Num_feedback:1,
				PageNum_feedback:1,
				
				loginLogList:[],
				Num_loginLog:1,
				PageNum_loginLog:1,
				
				editLogList:[],
				Num_editLog:1,
				PageNum_editLog:1,
			}
		},
		onLoad() {
			var that = this;
			
			var value = uni.getStorageSync("user");
			that.adminName = value.name;
			// 获取第一页
			// 打印
			console.log("正在获取第一页人员数据");
			// 网络提交数据
			uni.request({
				// 地址
				url: "http://localhost:8080/Perpage",
				// 请求方式
				method: "POST",
				data:{
					key: that.search_word,
					perpage: 10, //每一页10个数据
					page: 1
				},
				
				// 查看返回的数据
				success: (res) => {
					// console.log(this.user);
					console.log("res:");
					console.log(res);
					// 登录成功，code = 200 -> 保存用户信息，返回个人中心
					if (res.data.code * 1 == 200) {
						that.PersonList	= res.data.result;
						console.log(that.PersonList);
					}
				}
			}),
			// 获取页数
			console.log("正在获取页数");
			uni.request({
				// 地址
				url: "http://localhost:8080/Perpage",
				// 请求方式
				method: "POST",
				data:{
					key: that.search_word,
					perpage: 1000, 
					page: 1
				},
				
				// 查看返回的数据
				success: (res) => {
					// console.log(this.user);
					console.log("res:");
					console.log(res);
					// 登录成功，code = 200 -> 保存用户信息，返回个人中心
					if (res.data.code * 1 == 200) {
						that.Num = res.data.result.length
						that.PageNum = Math.ceil(that.Num / 10); //向上整除
						console.log(that.PageNum)
					}
				}
			})
			
			// 获取文章数据
			// 获取第一页
			// 打印
			console.log("正在获取第一页文章数据");
			// 网络提交数据
			uni.request({
				// 地址
				url: "http://localhost:8080/PerpagePost",
				// 请求方式
				method: "POST",
				data:{
					key: that.search_word,
					perpage: 10, //每一页10个数据
					page: 1
				},
				
				// 查看返回的数据
				success: (res) => {
					// console.log(this.user);
					console.log("res:");
					console.log(res);
					// 登录成功，code = 200 -> 保存用户信息，返回个人中心
					if (res.data.code * 1 == 200) {
						that.ShopList	= res.data.result;
						console.log(that.ShopList);
					}
				}
			}),
			// 获取页数
			console.log("正在获取文章页数");
			uni.request({
				// 地址
				url: "http://localhost:8080/PerpagePost",
				// 请求方式
				method: "POST",
				data:{
					key: that.search_word,
					perpage: 1000, 
					page: 1
				},
				
				// 查看返回的数据
				success: (res) => {
					// console.log(this.user);
					console.log("res:");
					console.log(res);
					// 登录成功，code = 200 -> 保存用户信息，返回个人中心
					if (res.data.code * 1 == 200) {
						that.Num_shop = res.data.result.length
						that.PageNum_shop = Math.ceil(that.Num_shop / 10); //向上整除
						console.log(that.PageNum_shop)
					}
				}
			}),
			
			// 获取第一页
			// 打印
			console.log("正在获取第一页投诉数据");
			// 网络提交数据
			uni.request({
				// 地址
				url: "http://localhost:8080/showcomplain",
				// 请求方式
				method: "POST",
				data:{
					key: that.search_word,
					perpage: 10, //每一页10个数据
					page: 1
				},
				
				// 查看返回的数据
				success: (res) => {
					// console.log(this.user);
					console.log("res:");
					console.log(res);
					// 登录成功，code = 200 -> 保存用户信息，返回个人中心
					if (res.data.code * 1 == 200) {
						that.feedbackList = res.data.result;
						console.log(that.feedbackList);
					}
				}
			}),
			// 获取页数
			console.log("正在获取投诉页数");
			uni.request({
				// 地址
				url: "http://localhost:8080/showcomplain",
				// 请求方式
				method: "POST",
				data:{
					key: that.search_word,
					perpage: 1000, 
					page: 1
				},
				
				// 查看返回的数据
				success: (res) => {
					// console.log(this.user);
					console.log("res:");
					console.log(res);
					// 登录成功，code = 200 -> 保存用户信息，返回个人中心
					if (res.data.code * 1 == 200) {
						that.Num_feedback = res.data.result.length
						that.PageNum_feedback = Math.ceil(that.Num_feedback / 10); //向上整除
						console.log(that.PageNum_feedback)
					}
				}
			}),
			
			// 获取第一页
			// 打印
			console.log("正在获取第一页登录日志数据");
			// 网络提交数据
			uni.request({
				// 地址
				url: "http://localhost:8080/showloginlog",
				// 请求方式
				method: "POST",
				data:{
					key: that.search_word,
					perpage: 10, //每一页10个数据
					page: 1
				},
				
				// 查看返回的数据
				success: (res) => {
					// console.log(this.user);
					console.log("res:");
					console.log(res);
					// 登录成功，code = 200 -> 保存用户信息，返回个人中心
					if (res.data.code * 1 == 200) {
						that.loginLogList = res.data.result;
						console.log(that.loginLogList);
					}
				}
			}),
			// 获取页数
			console.log("正在获取登录日志页数");
			uni.request({
				// 地址
				url: "http://localhost:8080/showloginlog",
				// 请求方式
				method: "POST",
				data:{
					key: that.search_word,
					perpage: 1000, 
					page: 1
				},
				
				// 查看返回的数据
				success: (res) => {
					// console.log(this.user);
					console.log("res:");
					console.log(res);
					// 登录成功，code = 200 -> 保存用户信息，返回个人中心
					if (res.data.code * 1 == 200) {
						that.Num_loginLog = res.data.result.length
						that.PageNum_loginLog = Math.ceil(that.Num_loginLog / 10); //向上整除
						console.log(that.PageNum_loginLog)
					}
				}
			})
			
			// 获取第一页
			// 打印
			console.log("正在获取第一页操作日志数据");
			// 网络提交数据
			uni.request({
				// 地址
				url: "http://localhost:8080/showeditlog",
				// 请求方式
				method: "POST",
				data:{
					key: that.search_word,
					perpage: 10, //每一页10个数据
					page: 1
				},
				
				// 查看返回的数据
				success: (res) => {
					// console.log(this.user);
					console.log("res:");
					console.log(res);
					// 登录成功，code = 200 -> 保存用户信息，返回个人中心
					if (res.data.code * 1 == 200) {
						that.editLogList = res.data.result;
						console.log(that.editLogList);
					}
				}
			}),
			// 获取页数
			console.log("正在获取操作日志页数");
			uni.request({
				// 地址
				url: "http://localhost:8080/showeditlog",
				// 请求方式
				method: "POST",
				data:{
					key: that.search_word,
					perpage: 1000, 
					page: 1
				},
				
				// 查看返回的数据
				success: (res) => {
					// console.log(this.user);
					console.log("res:");
					console.log(res);
					// 登录成功，code = 200 -> 保存用户信息，返回个人中心
					if (res.data.code * 1 == 200) {
						that.Num_editLog = res.data.result.length
						that.PageNum_editLog = Math.ceil(that.Num_editLog / 10); //向上整除
						console.log(that.PageNum_editLog)
					}
				}
			})
			
		},
		
		methods:{
			menu_change:function(e){
				var that = this
				that.menu_id = e.id
				that.name = e.name
			},
			search:function(){
				var that = this
				uni.request({
					url:'https://hn216.api.yesapi.cn/',
						method: "POST",
						header: {
						},
						data:{
							model_name:'car_person',
							where:'[["user_num", "LIKE","'+that.search_word+'"]]',
							app_key:'0B4163C86181759DD5DE6C761AF719A5',
							service:'App.Table.FreeQuery',
							perpage:10, //每一页12个数据
							page:1 //第几页
						},
						
						success: function (res) {
							console.log(res)
							that.PersonList = res.data.data.list
							console.log(that.PersonList)
						}
					})					
				uni.request({
					url:'https://hn216.api.yesapi.cn/',
					method: "POST",
					data:{
						model_name:'car_person',
						where:'[["user_num", "LIKE","'+that.search_word+'"]]',
						app_key:'0B4163C86181759DD5DE6C761AF719A5',
						service:'App.Table.FreeQuery',
						perpage:10000, //每一页12个数据
						
					},					
					success: function (res) {
							that.Num = res.data.data.list.length
							that.PageNum = Math.ceil(that.Num / 10); //向上整除
							console.log(that.PageNum)
						}
					})
			},
			add_sure:function(){
				var that = this
				console.log(that.accout)
				// 手机号正则
				if (!(/^1(3|4|5|6|7|8|9)\d{9}$/.test(that.add_accout))) {
				     uni.showToast({
				        icon: 'none',
				        title: '手机号不正确'
				    });
				    return;
				}else{
					// 密码正则
					if (!(/^\d{6}$/.test(that.add_password))) {
					    uni.showToast({
					        icon: 'none',
					        title: '密码不是六位数字！'
					    });
					    return;
					}else{
						uni.request({
							url:'https://hn216.api.yesapi.cn/?s=App.Table.CheckCreate',
								data:{
									model_name:'car_person',
									app_key:'0B4163C86181759DD5DE6C761AF719A5',
									service:'App.Table.CheckCreate',
									data:{
										'user_num':that.add_accout,
										'user_pass':that.add_password,
									},
									check_field:"user_num",
								},
								
								success: function (res) {
									console.log('成功')
									console.log(res)
									that.add_accout = ''
									that.add_password = ''
									that.$refs.popup1.close()
									uni.showToast({
										title:'添加成功！',
										icon:'none'
									})
									that.refresh()
								}
							})
					} 
				}
			},
			add_cancel:function(){
				this.$refs.popup1.close()
			},
			add_data:function(){
				this.$refs.popup1.open()
			},
			// cancel_choose:function(){
			// 	var that = this
				
			// 	that.choose_all_ = false
			// 	that.checkbox = false
			// 	console.log(that.choose_all_)
			// },
			delete_all:function(){
				var that = this
				that.choose_all_ = false
				that.checkbox = false
				
				if(that.checkbox_status.length==0&&that.checkbox==false){
					uni.showToast({
						title:'未选中任何行',
						icon:'none'
					})
				}else{
					uni.showModal({
					    content: '确定要删除吗？',
					    success: function (res) {
					        if (res.confirm) {
					            console.log('用户点击确定');
								console.log(that.checkbox_status)
								if(that.checkbox_status.length == 0){
									console.log(1)
									console.log(that.PersonList)
									console.log(that.checkbox)
									if(that.checkbox == true){
										console.log(2)
										
										for(var i = 0;i<that.PersonList.length;i++){
											console.log(that.PersonList[i].id)
											uni.request({
												url:'https://hn216.api.yesapi.cn/?s=App.Table.FreeDelete',
													method: "POST",
													data:{
														model_name:'car_person',
														app_key:'0B4163C86181759DD5DE6C761AF719A5',
														service:'App.Table.FreeDelete',
														where:'[["id", "=","'+that.PersonList[i].id+'"]]',
													},
													success: function (res) {
														console.log(res)
														that.refresh()
													}
												})
										}
										uni.showToast({
										    icon: 'none',
										    title: '删除成功'
										});
										that.refresh()
										that.checkbox = false
									}
								}else{
									for(var i = 0;i<that.checkbox_status.length;i++){
										uni.request({
											url:'https://hn216.api.yesapi.cn/?s=App.Table.FreeDelete',
												method: "POST",
												data:{
													model_name:'car_person',
													app_key:'0B4163C86181759DD5DE6C761AF719A5',
													service:'App.Table.FreeDelete',
													where:'[["id", "=","'+that.checkbox_status[i]+'"]]',
												},
												success: function (res) {
													that.refresh()
												}
											})
									}
									that.checkbox = false
									uni.showToast({
									    icon: 'none',
									    title: '删除成功'
									});
									
								}
					        } else if (res.cancel) {
					            console.log('用户点击取消');
					        }
					    }
					});
				}
				
				
			},
			checkbox_change:function(e){
				this.checkbox_status=e.detail.value
				console.log(this.checkbox_status)
				
				
			},
			choose_all:function(){
				if(this.checkbox == false){
					this.checkbox = true
					this.choose_all_ = true
					this.checkbox_status =[]
					console.log(this.checkbox)
				}else{
					this.checkbox = false
					this.choose_all_ = false
					this.checkbox_status =[]
					console.log(this.checkbox)
				}
			},
			delete_detail:function(e){
				var that = this
				uni.showModal({
				    content: '确定要删除吗？',
				    success: function (res) {
				        if (res.confirm) {
				            console.log('用户点击确定');
							
							uni.request({
								url:'https://hn216.api.yesapi.cn/?s=App.Table.FreeDelete',
									method: "POST",
									data:{
										model_name:'car_person',
										app_key:'0B4163C86181759DD5DE6C761AF719A5',
										service:'App.Table.FreeDelete',
										where:'[["id", "=","'+e.id+'"]]',
									},
									success: function (res) {
										console.log(res)
										
										that.checkbox_status =[]
										uni.showToast({
										    icon: 'none',
										    title: '删除成功'
										});
										
										that.refresh()
									}
								})								

				        } else if (res.cancel) {
				            console.log('用户点击取消');
							
							that.checkbox_status =[]
				        }
				    }
				});
			},
			refresh(){
				
				console.log(this.checkbox)
			
				var that = this
				
				uni.request({
					url:'https://hn216.api.yesapi.cn/',
					method: "POST",
					data:{
						model_name:'car_person',
						where:'[["user_num", "LIKE","'+that.search_word+'"]]',
						app_key:'0B4163C86181759DD5DE6C761AF719A5',
						service:'App.Table.FreeQuery',
						perpage:10, //每一页12个数据
						page:that.NowPage, //第几页
						// order: ["id DESC"],
					},					
					success: function (res) {
							// console.log(res.data.data.list)
							that.PersonList = res.data.data.list
						}
					})
				uni.request({
					url:'https://hn216.api.yesapi.cn/',
					method: "POST",
					data:{
						model_name:'car_person',
						where:'[["user_num", "LIKE","'+that.search_word+'"]]',
						app_key:'0B4163C86181759DD5DE6C761AF719A5',
						service:'App.Table.FreeQuery',
						perpage:10000, //每一页12个数据
						
					},					
					success: function (res) {
							that.Num = res.data.data.list.length
							that.PageNum = Math.ceil(that.Num / 10); //向上整除
							console.log(that.PageNum)
						}
					})
			},
			alter_cancel(){
				this.$refs.popup.close()
			},
			alter_sure(){
				var that = this
				console.log(that.accout)
				// 手机号正则
				if (!(/^1(3|4|5|6|7|8|9)\d{9}$/.test(that.accout))) {
				     uni.showToast({
				        icon: 'none',
				        title: '手机号不正确'
				    });
				    return;
				}else{
					// 密码正则
					if (!(/^\d{6}$/.test(that.password))) {
					    uni.showToast({
					        icon: 'none',
					        title: '密码不是六位数字！'
					    });
					    return;
					}else{
						uni.request({
							url:'https://hn216.api.yesapi.cn/?s=App.Table.CheckCreateOrUpdate',
								data:{
									model_name:'car_person',
									app_key:'0B4163C86181759DD5DE6C761AF719A5',
									service:'App.Table.CheckCreateOrUpdate',
									data:{
										'user_num':that.accout,
										'user_pass':that.password,
										'id':that.person_id
									},
									check_field:"id",
									
								},
								
								success: function (res) {
									console.log('成功')
									console.log(res)
									that.$refs.popup.close()
									uni.showToast({
										title:'修改成功！',
										icon:'none'
									})
									that.refresh()
								}
							})
					} 
				}
				
								

			},
			close(){
				this.$refs.popup.close()
			},
			confirm0(){
				this.input_confirm = 0
				console.log(this.input_confirm)
			},
			confirm1(){
				this.input_confirm = 1
				console.log(this.input_confirm)
			},
			alter:function(e){
				this.$refs.popup.open()
				console.log(e)
				this.password = e.user_pass
				this.accout = e.user_num
				this.add_time = e.add_time
				this.person_id = e.id
			},
			
			change_page:function(e){
				console.log(e)
				var that = this
				that.NowPage = e
				that.checkbox = false
				that.checkbox_status =[]
				uni.request({
					url:'https://hn216.api.yesapi.cn/',
					method: "POST",
					data:{
						model_name:'car_person',
						where:'[["user_num", "LIKE","'+that.search_word+'"]]',
						app_key:'0B4163C86181759DD5DE6C761AF719A5',
						service:'App.Table.FreeQuery',
						perpage:10, //每一页12个数据
						page:e, //第几页
						// order: ["id DESC"],
					},					
					success: function (res) {
							// console.log(res.data.data.list)
							that.PersonList = res.data.data.list
							console.log(that.PersonList)
						}
					})
			},
			page_delete:function(){
				var that = this
				if(that.NowPage<=1){
					console.log('不合格')
					alert('已经是首页了！')
				}else{
					that.NowPage = that.NowPage-1
					uni.request({
						url:'https://hn216.api.yesapi.cn/',
						method: "POST",
						data:{
							model_name:'car_person',
							where:'[["user_num", "LIKE","'+that.search_word+'"]]',
							app_key:'0B4163C86181759DD5DE6C761AF719A5',
							service:'App.Table.FreeQuery',
							perpage:10, //每一页12个数据
							page:that.NowPage, //第几页
							// order: ["id DESC"],
						},					
						success: function (res) {
								// console.log(res.data.data.list)
								that.PersonList = res.data.data.list
								console.log(that.PersonList)
							}
						})
				}
			},
			page_add:function(){
				var that = this
				if(that.NowPage>=that.PageNum){
					console.log('不合格')
					alert('已经是尾页了！')
				}else{
					that.NowPage = that.NowPage+1
					uni.request({
						url:'https://hn216.api.yesapi.cn/',
						method: "POST",
						data:{
							model_name:'car_person',
							where:'[["user_num", "LIKE","'+that.search_word+'"]]',
							app_key:'0B4163C86181759DD5DE6C761AF719A5',
							service:'App.Table.FreeQuery',
							perpage:10, //每一页12个数据
							page:that.NowPage, //第几页
							// order: ["id DESC"],
						},					
						success: function (res) {
								// console.log(res.data.data.list)
								that.PersonList = res.data.data.list
								console.log(that.PersonList)
							}
						})
				}
			}
				
		}
	}
</script>

<style>
	.add_color{
		background-color: #1890FF;
		
	}
	.add_color:hover{
		background-color: #46A6FF;
	}
	.admin_pic{
		width: 180upx;
		height: 50upx;
		padding: 20upx;
	}
	.admin_pic:hover{
		background-color: #2C405A;
	}
	.arrow{
		height: 20upx;
		width: 20upx;
		
		margin-left: auto;
	}
	.arrow1{
		height: 18upx;
		width: 18upx;
		
	}
	.choose_detail{
		padding: 20upx 20upx 20upx 40upx;
		width: 160upx;
		display: flex;
		flex-direction: row;
		color: #FFFFFF;
		font-size: 15px;
		align-items: center;
		background-color: #1890FF;
	}
	.detail{
		padding: 20upx 20upx 20upx 40upx;
		width: 160upx;
		display: flex;
		flex-direction: row;
		color: #FFFFFF;
		font-size: 15px;
		align-items: center;
		
	}
	.detail:hover{
		background-color: #1890FF;
	}
	.list_detail{
		border-bottom: 1px solid #EBEEF5;display: flex;flex-direction: row;width: 100%;padding: 10upx;
	}
	.list_detail:hover{
		background-color: #F5F7FA;
	}
	.alter{
		color: #1890FF;
	}
	.alter:hover{
		text-decoration: underline;
		color: #DD524D;
	}
	.delete{
		color: #1890FF;margin-left: 10upx;
	}
	.delete:hover{
		text-decoration: underline;
		color: #DD524D;
	}
	.left{
		background-color: #F4F4F5;
		width: 15upx;
		height: 15upx;
		padding: 10upx;
	}
	.page{
		color: #FFFFFF;
		background-color: #1890FF;
		width: 32upx;
		height: 32upx;
		font-size: 15px;
		border-radius: 3upx;
		text-align: center;
		justify-content: center;
		display: flex;
		align-items: center;
		margin-left: 10upx;
	}
	.nopage{
		color: #FFFFFF;
		background-color: #F4F4F5;
		width: 32upx;
		height: 32upx;
		font-size: 15px;
		border-radius: 3upx;
		text-align: center;
		justify-content: center;
		display: flex;
		align-items: center;
		margin-left: 10upx;
		color: #606270;
	}
	.right{
		margin-left: 10upx;
		background-color: #F4F4F5;
		width: 15upx;
		height: 15upx;
		padding: 10upx;
	}
	.input_account{
		padding: 5upx 15upx 5upx 15upx;border: 1px solid #DCDFE6;width: 250upx;font-size: 13px;margin-left: 20upx;
	}
	.input_account:hover{
		border: 1px solid #C8C7CC;
	}
	.input_account1{
		padding: 5upx 15upx 5upx 15upx;border: 1px solid #DCDFE6;width: 227upx;font-size: 13px;margin-left: 20upx;
	}
	.input_confirm{
		border: 1px solid #1890FF;
		padding: 5upx 15upx 5upx 15upx;width: 250upx;font-size: 13px;margin-left: 20upx;
	}
	.back_color{
		background-color: #FF4D4F;
	}
	.back_color:hover{
		background-color: #FF7172;
	}
</style>
