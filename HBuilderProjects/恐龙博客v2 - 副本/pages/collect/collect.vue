<template>
    <view>
        <piaoyi-comment-list :apprises="apprises"></piaoyi-comment-list>
    </view>
</template>

<script>
    import piaoyiCommentList from '@/components/piaoyi-commentList/piaoyi-commentList.vue'
    export default {
        data() {
            return {
                
					// {
                //     avatarUrl: '', //头像
                //     name: '小红', //昵称
                //     // commentStar: 5, //评分
                //     commentDate: '2023-06-09 15:00:00', //时间
                //     content: '一段简介', //描述简介
                //     blogName: '认真敬业' // 标签
                // },{
                //     avatarUrl: '',
                //     name: '小明',
                //     // commentStar: 5,
                //     commentDate: '2023-06-09 15:00:00',
                //     content: '一段简介',
                //     blogName: '认真敬业'
                // }, {
                //     avatarUrl: '',
                //     name: '小黑',
                //     // commentStar: 5,
                //     commentDate: '2023-06-09 15:00:00',
                //     content: '一段简介',
                //     blogName: '最适合入门的100个深度学习实战项目'
                // },{
                //     avatarUrl: '',
                //     name: '小红',
                //     // commentStar: 5,
                //     commentDate: '2023-06-09 15:00:00',
                //     content: '一段简介',
                //     blogName: '认真敬业'
                // },{
                //     avatarUrl: '',
                //     name: '小明',
                //     // commentStar: 5,
                //     commentDate: '2023-06-09 15:00:00',
                //     content: '一段简介',
                //     blogName: '认真敬业'
                // }, {
                //     avatarUrl: '',
                //     name: '小黑',
                //     // commentStar: 5,
                //     commentDate: '2023-06-09 15:00:00',
                //     content: '一段简介',
                //     blogName: '认真敬业'
                // }
            apprises: []
			};
        },
        components: {
            piaoyiCommentList
        },
		
		onLoad() {
					// console.log("获取收藏列表");
					
					var value = uni.getStorageSync("user");
					console.log("value");
					// console.log(value.userid);
					// var userID = value.userid;
					// console.log(userID);
					
					uni.request({
						url: "http://localhost:8080/CollectMessage",
						method: "POST",
						data: {userid: value.userid},
						
						success: (res) => {
							// console.log("res: ");
							console.log(res);
							this.apprises = res.data.result;
						}
						
					})
				}

    }
	
	
</script>

<style lang="scss">

</style>
