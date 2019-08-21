<template>
  <div id="login-component">
    <div id="blue-bg-div">
      <h1>Login</h1>
      <form>
        Email:
        <br />
        <input type="text" name="email" v-model="email" />
        <br />Password:
        <br />
        <input type="password" name="password" v-model="password" />
        <br />
        <input type="submit" value="Zaloguj się" @click="Login()" />
      </form>
    </div>
    <br />
    <button @click="getList()">Lista userów</button>
    <div class="collection-item" v-for="user in this.$root.users" v-bind:key="user">
      {{ user.fullName }}
      {{ user.emailAddress }}
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data: function() {
    return {
	  user: [{ emailAddress: "", fullName: "" }],
	  email: "",
	  password: ""
    };
  },
  methods: {
    getList() {
      const vm = this;
      axios
        .get("http://localhost:9000/api/user/getList")
        .then(function(response) {
          console.log(response.data);
          if (response.data.body != 0) {
            vm.$root.user = response.data.body;
            console.log(vm.$root.user);
          }
        });
    },
    Login() {
      const vm = this;
      var pass = this.password;
      var mail = this.email;
      axios
        .post("http://localhost:9000/api/user/login", {
          emailAddress: mail,
          password: pass
        })
        .catch(e => {
          this.loginerror = "Źle wypełnione pola!";
        });
      console.log(log);
    }
  }
};
</script>


<style>
#blue-bg-div {
  background-color: #0079bf;
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