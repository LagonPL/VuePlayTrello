<template>
	<div id="login-component">
	<div id="blue-bg-div">
		<h1>Login</h1>
		<form>
		  Email:<br>
		  <input type="text" name="email"><br>
		  Password:<br>
		  <input type="password" name="password"><br>
		  <input type="submit" value="Zaloguj się">
		</form>
	</div>
	<div class="collection-item" v-for="user in this.$root.users">
				{{ user.fullName }}
				{{ user.emailAddress }}
			</div>
	</div>
</template>

<script>
import axios from 'axios';

export default {
	data: function () {
		return {
			user:[
				{emailAddress: "",
				fullName: ""}
			]
		}
	},
	mounted: function () {
		this.getList();
	},
	methods: {
		getList() {
			const vm = this;
			axios.get('http://localhost:9000/api/user/getList')
			.then(function (response) {
				console.log(response.data);
				if(response.data.body != 0) {
					vm.$root.user = response.data.body;
					console.log(response.data.body)
				}
			  })
		}
	}
}
</script>


<style>
#blue-bg-div {
	background-color: #0079BF;
}
.user-title {
	position: absolute;
	top: 0;
	left: 0;
	margin: 10px 5px;
	color: white;
	font-weight: 700;
    font-size: 15px;
}
</style>