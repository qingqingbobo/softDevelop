<template>
	<view>
		<comment-eg :articleId="postid"></comment-eg>
	</view>
</template>

<script>

	
	export default {
		data() {
			return {
				postid: uni.getStorageSync("zzw")
			}
		},
		onLoad() {
			console.log("comment_contentcomment postid: ")
			console.log(this.postid);
		},
		methods: {

		}
	}
</script>

<style>
</style>
