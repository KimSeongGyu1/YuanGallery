<template>
    <div>
        <v-pagination
            v-model = currentPage
            :length = pageCount
            :total-visible="10"
            @input="onPageClick"
        />
    </div>
</template>

<script>
export default {
    props: ["currentPage", "pageCount"],
    methods: {
        onPageClick(pageNumber) {
            const page = pageNumber - 1;
            if (this.$route.path.startsWith("/search")) {
                const searchTitle = this.$route.query.title;
                this.$router.push(`/search?title=${searchTitle}&page=${page}`)
            } else {
                this.$router.push(`/${page}`);
            }
        }
    }
}
</script>

<style scoped>

</style>