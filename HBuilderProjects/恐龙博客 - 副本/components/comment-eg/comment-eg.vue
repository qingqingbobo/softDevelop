<template>
	<view class="comment-eg">
		<hb-comment ref="hbComment" @add="add" @del="del" @like="like" @focusOn="focusOn" :deleteTip="'确认删除？'"
			:cmData="commentData" v-if="commentData"></hb-comment>
	</view>
</template>

<script>
	export default {
		name: 'comment-eg',
		props: {
			articleId: {
				type: Number,
				default: () => {
					return null;
				}
			}
		},
		watch: {
			articleId: {
				handler: function(newVal, oldVal) {
					if (newVal) {
						this.getComment(newVal);
					}
				},
				immediate: true
			}
		},
		data() {
			return {
				"commentData": null,
				"reqFlag": false // 请求标志，防止重复操作，true表示请求中

			}
		},
		methods: {
			// 登录校验
			checkLogin() {
				// TODO 此处填写登录校验逻辑
				if (true) {
					return true;
				} else {
					uni.showModal({
						title: '提示',
						content: '请先登录',
						confirmText: '前往登录',
						success: function(res) {
							if (res.confirm) {
								uni.redirectTo({
									url: '/pages/login/login'
								});
							}
						}
					});
					return false;
				}
			},
			// 输入框聚焦
			focusOn() {
				this.checkLogin();
			},
			// 获取评论
			getComment(articleId) {
				// TODO 接入真实接口
				// this.$u.api.commentList(articleId).then(res => {
				// this.commentData = {
				// 	"readNumer": res.readNumer,
				// 	"commentSize": res.commentList.length,
				// 	"comment": this.getTree(res.commentList)
				// };
				// }).catch(res => {})
				
				console.log("getComment");
				uni.request({
					url: "http://localhost:8080/comment",
					method: "POST",
					data: {
						postid: articleId,
					},
					
					success: (res) => {
						console.log("getComment res: ");
						console.log(res);
						this.commentData = {
							"commentSize": res.data.result.length,
							"comment": this.getTree(res.data.result)
						};
					}
				})
				
				// 下边假装请求成功
				// let res = {
				// 	"readNumer": 193,
				// 	"commentList": [{
				// 			"id": 1,
				// 			"avatarUrl": "https://inews.gtimg.com/newsapp_ls/0/13797755537/0",
				// 			"nickName": "超长昵称超长...",
				// 			"content": "啦啦啦啦",
				// 			"parentId": null,
				// 			"createTime": "2021-07-02 16:32:07"
				// 		},
				// 		{
				// 			"id": 2,
				// 			"avatarUrl": "https://inews.gtimg.com/newsapp_ls/0/13797761970/0",
				// 			"nickName": "寂寞无敌",
				// 			"content": "我是评论的评论",
				// 			"parentId": 1,
				// 			"createTime": "2021-07-02 17:05:50"
				// 		},
				// 		{
				// 			"id": 4,
				// 			"avatarUrl": "https://inews.gtimg.com/newsapp_ls/0/13797763270/0",
				// 			"nickName": "name111",
				// 			"content": "评论啦啦啦啦啦啦啦啦啦啦",
				// 			"parentId": null,
				// 			"createTime": "2021-07-13 09:37:50"
				// 		},
				// 		{
				// 			"id": 5,
				// 			"avatarUrl": "https://inews.gtimg.com/newsapp_ls/0/13797755537/0",
				// 			"nickName": "超长昵称超长...",
				// 			"content": "超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论超长评论",
				// 			"parentId": null,
				// 			"createTime": "2021-07-13 16:04:35"
				// 		},
				// 		{
				// 			"id": 13,
				// 			"avatarUrl": "https://inews.gtimg.com/newsapp_ls/0/13797755537/0",
				// 			"nickName": "超长昵称超长...",
				// 			"content": "@寂寞无敌 你怕不是个大聪明",
				// 			"parentId": 1,
				// 			"createTime": "2021-07-14 11:01:23"
				// 		}
				// 	]
				// };
				// this.commentData = {
				// 	"commentSize": res.data.result.length,
				// 	"comment": this.getTree(res.data.result)
				// };
			},
			// 新增评论
			add(req) {
				if (!this.checkLogin()) {
					return
				}
				if (this.reqFlag) {
					uni.showToast({
						title: '操作频繁',
						duration: 1000
					});
					return
				}
				this.reqFlag = true;
				
				console.log("add comment");
				
				uni.request({
					url: "http://localhost:8080/docomment",
					method: "POST",
					data: {
						postid: this.articleId,
						preid: req.pId,
						content: req.content,
						author: 140
					},
					
					success: (res) => {
						console.log("add res: ");
						console.log(res);
						
						this.reqFlag = false;
						this.$refs.hbComment.addComplete();
						this.getComment(this.articleId);
					}
				})
				
				// TODO 接入真实接口
				// let params = {
				// 	"articleId": this.articleId,
				// 	"pId": req.pId,
				// 	"content": req.content
				//  "author": 
				// }
				// this.$u.api.commentAdd(params).then(res => {
				// 	uni.showToast({
				// 		title: '操作成功！',
				// 		duration: 3000
				// 	});
				// 	this.$refs.hbComment.addComplete();
				// 	this.getComment();
				// 	this.reqFlag = false;
				// }).catch(res => {
				// 	this.reqFlag = false;
				// })
				// 下边假装请求成功
				
				// this.reqFlag = false;
				// this.$refs.hbComment.addComplete();
				// this.getComment(this.articleId);
				// 延时 200 ms
				// setTimeout(this.getComment(this.articleId), 10000);
			},
			// 点赞评论
			like(commentId) {
				if (!this.checkLogin()) {
					return
				}
				if (this.reqFlag) {
					uni.showToast({
						title: '操作频繁',
						duration: 1000
					});
					return
				}
				this.reqFlag = true;
				// TODO 接入真实接口
				// this.$u.api.commentLike(commentId).then(res => {
				// 	this.$refs.hbComment.likeComplete(commentId);
				// 	this.reqFlag = false;
				// }).catch(res => {
				// 	this.reqFlag = false;
				// })
				// 下边假装请求成功
				this.reqFlag = false;
				this.$refs.hbComment.likeComplete(commentId);
			},
			// 删除评论
			del(commentId) {
				if (!this.checkLogin()) {
					return
				}
				if (this.reqFlag) {
					uni.showToast({
						title: '操作频繁',
						duration: 1000
					});
					return
				}
				this.reqFlag = true;
				// TODO 接入真实接口
				// this.$u.api.commentDelete(commentId).then(res => {
				// 	this.reqFlag = false;
				// 	this.$refs.hbComment.deleteComplete(commentId);
				// }).catch(res => {
				// 	this.reqFlag = false;
				// })
				// 下边假装请求成功
				this.reqFlag = false;
				this.$refs.hbComment.deleteComplete(commentId);
			},
			// 列表按照父子转换成树
			getTree(data) {
				let result = [];
				let map = {};
				data.forEach(item => {
					map[item.id] = item;
				});
				data.forEach(item => {
					let parent = map[item.parentId];
					if (parent) {
						(parent.children || (parent.children = [])).push(item);
					} else {
						result.push(item);
					}
				});
				return result;
			}
		}
	};
</script>

<style>
</style>
