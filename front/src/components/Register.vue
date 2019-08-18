<template>
	<div id="login-component">
	<div id="blue-bg-div">
		<h1>Login</h1>
		<form>
		  Email:<br>
		  <input type="text" name="email" v-model="email"><br>
		  Login:<br>
		  <input type="text" name="login" id="login"><br>
          Password:<br>
		  <input type="password" name="password" id="password"><br>
		  <input type="submit" value="Zarejestruj się" @click="Register()">
		</form>
	</div>
	</div>
</template>

<script>
import axios from 'axios';

export default {
	methods: {
		Register() {
			const vm = this;

		}
	}
}
</script>

<style>
#blue-bg-div {
	background-color: #0079BF;
}
</style>