<template>
    <div id="admin_box">
        <div v-if="!isLogin">
            <v-text-field
                    label="name"
                    hide-details
                    v-model="name"
            ></v-text-field>
            <v-text-field
                    label="password"
                    hide-details
                    v-model="password"
                    @keyup.enter="login"
            ></v-text-field>
        </div>
        <div v-else>
            <v-text-field
                    label="블로그 이름"
                    hide-details
                    v-model="blogName"
            ></v-text-field>
            <v-text-field
                    label="블로그 주소"
                    hide-details
                    v-model="password"
            ></v-text-field>
            <v-text-field
                    label="블로그 rss 주소"
                    hide-details
                    v-model="blogRssUrl"
                    @keyup.enter="registerBlog"
            ></v-text-field>
        </div>
    </div>
</template>

<script>
    import {postAction} from "../api";

    export default {
    data() {
        return {
            name: "",
            password: "",
            isLogin: this.$cookie.get("yuanToken"),
            blogName: "",
            blogUrl: "",
            blogRssUrl: ""
        }
    },
    methods: {
        async login() {
            const loginRequest = {
                name: this.name,
                password: this.password
            };
            const {data} = await postAction("/api/login", loginRequest);
            if (data.token) {
                this.name = "";
                this.password = "";
                this.$cookie.set("yuanToken", data.token, {expires: '600s'});
                this.isLogin = true;
            }
        },
        async registerBlog() {

        }
    }
}
</script>

<style scoped>
#admin_box {
    width: 300px;
}
</style>