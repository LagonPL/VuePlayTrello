<template>
	<div id="login-component">
	<div id="blue-bg-div">
		<h1>Login</h1>
		<form>
		  Email:<br>
		  <input type="text" name="email" ref="email"><br>
		  Login:<br>
		  <input type="text" name="login" v-model="login"><br>
          Password:<br>
		  <input type="password" name="password" ref="password"><br>
		  <input type="submit" value="Zarejestruj się" @click="Register()">
		</form>
	</div>
	</div>
</template>

<script>
import axios from 'axios';

export default {
	data: function () {
		return {
			login: ""
		}
	},
	methods: {
		Register() {
			const vm = this;
			alert(this.login)
		}
	}
}
</script>

<style>
#blue-bg-div {
	background-color: #0079BF;
}
</style>