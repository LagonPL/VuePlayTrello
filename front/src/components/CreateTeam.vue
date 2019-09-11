<template>
  <div id="login-component">
    <div id="blue-bg-div">
      <h1>Stwórz Zespół</h1>
      <form>
        <br />
        <input
          type="text"
          name="teamName"
          v-model="teamName"
          ref="teamName"
          maxlength="100"
          size="25"
          placeholder="Nazwa Zespołu"
          pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
          class="form-control col-md-3"
        />        
        <br />
      </form>
      <button @click="createNewTeam()" class="btn btn-light">Utwórz</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Vue from 'vue';

export default {
  data: function() {
    return {
      teamName: ""
    };
  },
  methods: {
    createNewTeam() {
      const vm = this;
      var newTitle = this.$refs.teamName.value;

      if (newTitle.length != 0) {
        axios       
          .post("http://localhost:9000/api/team/create", {
            name: newTitle
          })
          .then(response => {
            console.log(response);
            this.$router.push("/teams");
            vm.getAllTeams();
          })
          .catch(e => {
            console.log(e);
          });
      } else {
        alert("Nazwa nie może być pusta!");
      }
    }
  }
};
</script>
