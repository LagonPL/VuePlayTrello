<template>
  <div id="login-component">
    <div id="blue-bg-div">
      <h1>Logowanie</h1>
      <form 
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
          type="password"
          name="password"
          v-model="password"
          maxlength="15"
          size="20"
          placeholder="Hasło"
          class="form-control col-md-3"
        />
        <br />
        <input type="submit" value="Zaloguj się" @click="Login()" class="btn btn-light"/>
      </form>
    </div>
    <br />
    <button @click="getList()" class="btn btn-light">Lista userów</button>
    <div class="collection-item" v-for="user in this.users">
      <ul>{{ user.fullName }}
      {{ user.emailAddress }}</ul>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Bootstrap from 'bootstrap';
export default {
  data: function() {
    return {
      users: "",
      email: "",
      password: ""
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
    getList() {
      const vm = this;
      axios
        .get("http://localhost:9000/api/user/getAll")
        .then(response => {
          console.log(response.data);
          if (response.data.body != 0) {
            this.users = response.data.body;
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
        .then(response => {
          this.$router.push('/boardList');
          location.reload(true);
          this.show("foo-css", "success", "Witaj ".concat(mail));
			  })
        .catch(e => {
          this.show("foo-css", "error", "Błędne dane");
        });        
	}
}
}
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