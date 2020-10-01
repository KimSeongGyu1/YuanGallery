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
            this.$router.push(`/${pageNumber - 1}`);
        }
    }
}
</script>

<style scoped>

</style>