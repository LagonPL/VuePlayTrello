<template>
  <div id="login-component">
    <div id="blue-bg-div">
      <h1>Rejestracja</h1>
      <form>
        <br />
        <input
          type="text"
          name="email"
          v-model="email"
          maxlength="100"
          size="25"
          placeholder="Email"
          pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
          class="form-control col-md-3"
        />
        <br />
        <input
          type="text"
          name="login"
          v-model="login"
          maxlength="20"
          size="25"
          placeholder="Login"
          class="form-control col-md-3"
        />
        <br />
        <input
          type="password"
          name="password"
          v-model="password"
          maxlength="15"
          size="20"
          placeholder="Hasło"
          class="form-control col-md-3"
        />
        <br />
        <!-- <input type="submit" value="Zarejestruj się" @click="Register()" disabled> -->
      </form>
      <button @click="Register()" class="btn btn-light">Rejestruj</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Vue from 'vue';

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
    show(group, type = "", text) {
      this.$notify({
        group,
        text,
        type
      });
    },
    clean(group) {
      this.$notify({ group, clean: true });
    },
    notify(email, login, password) {
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      if (!re.test(email) && login.length == 0 && password.length == 0) {
        this.show("foo-css", "error", "Błędne dane");
      } else if (!re.test(email)) {
        this.show("foo-css", "error", "Błędny email");
      } else if (login.length == 0) {
        this.show("foo-css", "error", "Błędny login");
      } else if (password.length == 0) {
        this.show("foo-css", "error", "Błędne hasło");
      }
    },
    Register() {
      const vm = this;
      var log = this.login;
      var pass = this.password;
      var email = this.email;
      var child = this.$refs.profile;
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

      if (
        pass.length != 0 &&
        log.length != 0 &&
        email.length != 0 &&
        re.test(email)
      ) {
        axios
          .post("http://localhost:9000/api/user/register", {
            emailAddress: email,
            password: pass,
            fullName: log
          })
          .then(response => {
            this.show(
              "foo-css",
              "success",
              "Utworzono użytkownika dla ".concat(email)
            );
            this.$router.push('/login');
          })
          .catch(e => {
            this.show("foo-css", "error", "Taki użytkownik już istnieje");
          });
      }
      this.notify(email, log, pass);
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