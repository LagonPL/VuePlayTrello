<template>
  <div id="login-component">
    <div id="blue-bg-div">
      <h1>Login</h1>
      <form>
        Email:
        <br />
        <input type="text" name="email" v-model="email" />
        <br />Login:
        <br />
        <input type="text" name="login" v-model="login" />
        <br />Password:
        <br />
        <input type="password" name="password" v-model="password" />
        <br />
        <!-- <input type="submit" value="Zarejestruj się" @click="Register()" disabled> -->
      </form>
      <button @click="Register()">Rejestruj</button>
      <h1>{{ loginerror }}</h1>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data: function() {
    return {
      login: "",
      password: "",
      email: "",
      loginerror: ""
    };
  },
  methods: {
    show (group, type = '', text) {
      this.$notify({
        group,
        title: `${type} notification`,
        text,
        type,
      })
    },
    clean (group) {
      this.$notify({ group, clean: true })
    },
    Register() {
      const vm = this;
      var log = this.login;
      var pass = this.password;
	  var mail = this.email;
	  var child = this.$refs.profile;
      if (pass.length != 0 && log.length != 0 && mail.length != 0) {
        axios
          .post("http://localhost:9000/api/user/register", {
            emailAddress: mail,
            password: pass,
            fullName: log
          })
          .catch(e => {
            this.show('foo-css', 'error',"Użytkownik istnieje");
          });
      } else {
        this.show('foo-css', 'error',"Błędne dane")
      }
      console.log(log);
    }
  }
};
</script>

<style>
#blue-bg-div {
  background-color: #0079bf;
}
</style>