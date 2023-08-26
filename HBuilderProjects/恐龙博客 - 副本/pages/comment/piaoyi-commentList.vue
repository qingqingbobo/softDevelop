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
                apprises: [ 
					
				]
            };
        },
        components: {
            piaoyiCommentList
        },
		
		onLoad() {
			console.log("获取评论列表");
			
			var value = uni.getStorageSync("user");
			console.log("value");
			// console.log(value.userid);
			// var userID = value.userid;
			// console.log(userID);
			
			uni.request({
				url: "http://localhost:8080/CommentMessage",
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

