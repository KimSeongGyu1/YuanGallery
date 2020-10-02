<template>
    <div id="post_list">
        <div
            v-if="fetchedPosts.length === 0"
            style="text-align: center"
        >
            검색 결과가 존재하지 않습니다.
        </div>
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
            if (this.$route.path.startsWith("/search")) {
                await this.updateBySearch();
            } else {
                await this.updateByPagination();
            }
        },
        async updateByPagination() {
            const page = this.$route.params.page ? this.$route.params.page : 0;
            const url = `/api/blog/posts?page=${page}&size=10&sort=publishedDate,desc`;
            const { data } = await getAction(url);
            this.fetchedPosts = data.postResponses;
            this.currentPage = parseInt(page) + 1;
            this.pageCount = data.pageCount;
        },
        async updateBySearch() {
            const searchTitle = this.$route.query.title;
            const page = this.$route.query.page ? this.$route.query.page : 0;
            const url = `/api/blog/search?searchTitle=${searchTitle}&page=${page}&size=10&sort=publishedDate,desc`;
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