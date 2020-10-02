import Vue from "vue"
import VueRouter from "vue-router"
import PostList from "../components/PostList";
import Admin from "../components/Admin";

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
            path: "/admin",
            name: "admin",
            component: Admin
        },
        {
            path: "/:page",
            name: "postPage",
            component: PostList
        },
        {
            path: "/search/:page",
            name: "searchPage",
            component: PostList
        }
    ]
})