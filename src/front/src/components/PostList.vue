<template>
    <div id="post_list">
        <Post
            v-for="post in fetchedPosts"
            :key="post.id"
            :post="post"
        />
        <div>
            <PageButton
                :currentPage="currentPage"
                :pageCount="pageCount"
            />
        </div>
    </div>
</template>

<script>
import Post from "./Post";
import PageButton from "./PageButton";
import { getAction } from "../api"

export default {
    data() {
        return {
            fetchedPosts: [],
            currentPage: 0,
            pageCount: 0
        }
    },
    components: {
        Post,
        PageButton
    },
    methods: {
        async updatePosts() {
            const page = this.$route.params.page ? this.$route.params.page : 0;
            const url = `/api/blog/posts?page=${page}&size=10&sort=publishedDate,desc`;
            const { data } = await getAction(url);
            this.fetchedPosts = data.postResponses;
            this.currentPage = parseInt(page) + 1;
            this.pageCount = data.pageCount;
        }
    },
    async created() {
        await this.updatePosts();
    },
    watch: {
        async $route() {
            await this.updatePosts();
        }
    }
}
</script>

<style scoped>
#post_list {
    width: 750px;
    margin: 20px auto;
}
</style>