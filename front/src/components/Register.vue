<template>
	<div id="login-component">
	<div id="blue-bg-div">
		<h1>Login</h1>
		<form>
		  Email:<br>
		  <input type="text" name="email" v-model="email"><br>
		  Login:<br>
		  <input type="text" name="login" v-model="login"><br>
          Password:<br>
		  <input type="password" name="password" v-model="password"><br>
		  <input type="submit" value="Zarejestruj się" @click="Register()">
		</form>
		<button @click="Register()">Test</button>
	</div>
	</div>
</template>

<script>
import axios from 'axios';

export default {
	data: function () {
		return {
			login: "",
			password: "",
			email: "",
		}
	},
	methods: {
		Register() {
			const vm = this;
			var log = this.login;
			var pass = this.password;
			var mail = this.email;
			
			axios.post('http://localhost:9000/api/user/register', {
				emailAddress: log,
				password: pass,
				fullName: mail
			})
			console.log(log)
		}
	}
}
</script>

<style>
#blue-bg-div {
	background-color: #0079BF;
}
</style>