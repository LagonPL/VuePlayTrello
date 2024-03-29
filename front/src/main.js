// noinspection JSAnnotator
import Vue from 'vue';
import VueRouter from 'vue-router';
import Notifications from 'vue-notification'
import Home from './components/Home.vue';
import App from './components/App.vue';
import BoardList from './components/BoardList.vue';
import Board from './components/Board.vue';
import Login from './components/Login.vue';
import TestBoard from './components/TestBoard.vue';
import Register from './components/Register.vue';
import CreateBoard from './components/CreateBoard.vue';
import Teams from './components/Teams.vue';
import CreateTeam from './components/CreateTeam.vue';
import Team from './components/Team.vue';
import 'bootstrap';
import 'jquery';
import 'popper.js';
import VueSplit from 'vue-split-panel'

Vue.use(VueSplit);
Vue.use(VueRouter);
Vue.use(Notifications);

const routes = [
	{
		path: '/',
		name: 'home',
		component: Home
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
	},
	{
		path: '/createboard',
		name: 'createboard',
		component: CreateBoard
	},	
	{
		path: '/team/:id',
		name: 'team',
		component: Team
	},
	{
		path: '/teams',
		name: 'teams',
		component: Teams
	},
	{
		path: '/createTeam',
		name: 'createTeam',
		component: CreateTeam
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