import Vue from "vue"
import VueRouter from "vue-router"
import PostList from "../components/PostList";

Vue.use(VueRouter);

export const router = new VueRouter({
    mode: "history",
    routes: [
        {
            path: "/",
            name: "main",
            component: PostList
        },
        {
            path: "/:page",
            name: "postPage",
            component: PostList
        }
    ]
})