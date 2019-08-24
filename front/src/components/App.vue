<template>
    <div id="app">
		<notifications group="foo-css"
                   position="bottom left"
                   :speed="500" />
		<div id="header">
			<router-link to="/boardList" tag="button">Tablice</router-link>
			<router-link to="/testBoard" tag="img" id="nav-logo" src="http://localhost:8080/src/images/header-logo.png"></router-link>
			<router-link to="/login" tag="button" id="login-btn">Zaloguj się</router-link>
			<router-link to="/register" tag="button" id="register-btn">Zarejestruj się</router-link>
			<button @click="Logout()" tag="button" id="logout-btn">Wyloguj się</button>
			<!-- <router-link to="/login" @click="Logout()" tag="button" id="logout-btn">Wyloguj się2</router-link>			 -->
		</div>
		<router-view id="test"></router-view>
    </div>
</template>

<script>
import axios from "axios";

export default {
	data: function() {
    return {
      mail: ""
    };
  },
  mounted: function () {
		this.getUsername();
	},
	methods: {
		getUsername() {
			const vm = this;
			axios.get('http://localhost:9000/api/user/username')
			  .then(function (response) {
				console.log(response.data);
				if(response.data.body != 0) {
					vm.$root.boards = response.data.body;
				}
			  })
			  .catch(function (error) {
				console.log(error);
			  });
		},
		Logout() {
			const vm = this;
			axios.get('http://localhost:9000/api/user/logout')
			  .catch(function (error) {
				console.log(error);
			  })

		},
	}
}
</script>
<style>
body {
	margin: 0;
	padding: 0;
	margin-top: 40px;
	font-family: "Helvetica Neue", Arial, Helvetica, sans-serif;
	font-size: 14px;
	font-style: normal;
	letter-spacing: 0px;
	text-transform: none;
	word-spacing: 0px;
	text-indent: 0px;
	white-space: nowrap;
	background-color: #0079BF;
}

#header {
	position: fixed;
	top: 0;
	background-color: #026AA7;
	width: 100%;
	height: 40px;
}

button {
	cursor: pointer;
	border-style: none;
}

#header button {
	height: 30px;
	background-color: #4E97C2;
	color: #f3f3f3;
	border-radius: 3px;
	line-height: 30px;
	font-weight: bold;
	margin: 5px 8px;
}

#header button:hover {
	background-color: #3588B9;
}

#nav-logo {
	vertical-align: middle;
	position: absolute;
	left: 45%;
	opacity: 0.5;
	margin: 5px 8px;
}

#nav-logo:hover {
	opacity: 0.7;
}

#login-btn {
	position: absolute;
	right: 120px;
}
#logout-btn {
	position: absolute;
	right: 220px;
}
#register-btn {
	position: absolute;
	right: 0px;
}
</style>