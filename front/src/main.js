// noinspection JSAnnotator
import Vue from 'vue';
import VueRouter from 'vue-router';
import Notifications from 'vue-notification'
import App from './components/App.vue';
import BoardList from './components/BoardList.vue';
import Board from './components/Board.vue';
import Login from './components/Login.vue';
import TestBoard from './components/TestBoard.vue';
import Register from './components/Register.vue';



Vue.use(VueRouter);
Vue.use(Notifications);

const routes = [
	{
		path: '/',
		name: 'home',
		component: Login
	},
	{
		path: '/boards/:id',
		name: 'board',
		component: Board
	},
	{
		path: '/boardList',
		name: 'board-list',
		component: BoardList
	},
	{
		path: '/login',
		name: 'login',
		component: Login
	},
	{
		path: '/testBoard',
		name: 'test-board',
		component: TestBoard
	},
	{
		path: '/register',
		name: 'register',
		component: Register
	}
]

const router = new VueRouter({
	routes
})

const app = new Vue({
	data: function () {
		return { boards: [] };
	},
	router,
	render: h => h(App)
}).$mount('#app-container');