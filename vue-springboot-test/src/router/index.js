import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
    },
    {
        path: '/blog',
        name: 'Blog',
        component: () => import('@/views/Blog.vue'),
    },
    {
        path: '*',
        name: 'Error',
        component: () => import('@/views/Error404.vue'),
    }
];
console.log(process.env.VUE_APP_DEPLOY_PATH);
const router = new VueRouter({
    base: process.env.VUE_APP_DEPLOY_PATH,
    mode: 'history',
    routes,
});

export default router;
