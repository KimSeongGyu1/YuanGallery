<template>
    <div class="post_item">
        <div class="post_title">
            <a :href="post.link" target="_blank"> {{post.title}} </a>
        </div>
        <div class="post_info">
            by {{ post.blogName }} ({{parseDate(post.publishedDate)}})
        </div>
        <hr class="separator">
    </div>
</template>

<script>
export default {
    props: ["post"],
    methods: {
        parseDate(dateFromPost) {
            const dateAndTime = dateFromPost.split('T');
            const date = dateAndTime[0];
            const time = dateAndTime[1].split(':').slice(0, -1).join(':');
            return date + " " + time;
        }
    }
}
</script>

<style scoped>
.post_item {
    display: flex;
    flex-direction: column;
}
.post_title {
    font-size: 16px;
}
.post_title a {
    text-decoration: none;
}
.post_info {
    font-size: 12px;
}
.separator {
    margin: 15px 0;
    border-top: thin solid #9FD0D4;
}
</style>