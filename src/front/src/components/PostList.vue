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
        const { data } = await getAction('/api/blog/posts');
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