<template>
    <div id="footer">
        좋은 개발 블로그들을 모아보고 싶어서 만들었습니다<br>
        혹시 좋은 블로그가 있다면 <a v-bind:href="link" target="_blank">깃허브</a>에 이슈를 통해 추천 부탁드립니다
    </div>
</template>

<script>
export default {
    data() {
        return {
            link: "https://github.com/KimSeongGyu1/YuanGallery"
        }
    }
}
</script>

<style scoped>
#footer {
    width: 750px;
    margin: 0 auto;
    text-align: center;
}
</style>