<template>
    <div id="post_list">
        <Post
            v-for="post in fetchedPosts"
            :key="post.id"
            :post="post"
        />
    </div>
</template>

<script>
import Post from "./Post";
import { getAction } from "../api"

export default {
    data() {
        return {
            fetchedPosts: []
        }
    },
    components: {
        Post
    },
    async created() {
        const page = this.$route.query.page ? this.$route.query.page : 0;
        const url = `/api/blog/postsInPage?page=${page}&size=10&sort=publishedDate,desc`;
        const { data } = await getAction(url);
        this.fetchedPosts = data.postResponses;
    }
}
</script>

<style scoped>
#post_list {
    width: 750px;
    margin: 20px auto;
}
</style>