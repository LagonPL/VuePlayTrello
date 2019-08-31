<template>
  <div>
    <button id="teamTitle" @click="renameTeam()">{{currentTeam.name}}</button>
    <button id="fav-btn" @click="deleteTeam(currentTeam.id)">
      <span class="glyphicon glyphicon-remove"></span>
    </button>
    <button id="fav-btn" @click="addUser(currentTeam.id)">
      <span class="glyphicon glyphicon-user"></span>
    </button>
    <button id="fav-btn">
      <span class="glyphicon glyphicon-list-alt"></span>
    </button>
    <div v-for="user in this.users">
      <ul>
        {{ user }}
      </ul>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data: function() {
    return {
      users: "",
      currentTeam: this.$root.teams[this.$route.params.id - 1]
    };
  },
  mounted: function() {
    this.getUsers();
  },
  methods: {
    addUser: function(teamId) {
      const vm = this;
      var newEmail = prompt("Podaj mail użytkownika");
      if (newEmail.length != 0) {
        axios
          .post("http://localhost:9000/api/team/addUser", {
            TeamViewModel: {
              parentTeam: teamId,
              mail: newEmail,
              name: ""
            }
          })
          .then(response => {
            console.log(response);
            console.log(parentTeam);
            console.log(email);
            location.reload(true);
          })
          .catch(e => {
            console.log(e);
          });
      } else {
        alert("Nazwa nie może być pusta!");
      }
    },
    getUsers() {
      const vm = this; 
      axios.get('http://localhost:9000/api/team/getusers/' + vm.currentTeam.id).then(response => {
        console.log(response.data);
        if (response.data.body != 0) {
          this.users = response.data.body;
          console.log(this.users);
        }
      });
    },
    renameTeam: function() {
			const vm = this;
			var newName = prompt("Podaj nową nazwę", this.currentTeam.name);
			if(newName.length != 0) {
				axios.post('http://localhost:9000/api/team/edit', {
					TeamViewModel: {
                        parentTeam: vm.currentTeam.id,
                        mail: "",
						name: newName
					}
				})
				.then(response => {
					console.log(response);
					vm.currentTeam.name = newName;
					location.reload(true);
				})
				.catch(e => {
				  console.log(e);
				})
			} else {
				alert("Nazwa nie może być pusta!");
			}
    },
    deleteTeam: function(id) {
			const vm = this;
			axios.delete('http://localhost:9000/api/team/delete/' + id)
			  .then(function (response) {
				console.log(response.data);
			  })
			  .catch(function (error) {
				console.log(error);
			  });
			 this.$router.push('/teams');
		}
  }
};
</script>

<style>
#fav-btn {
  background-color: transparent;
  font-size: 22px;
  padding: 0 10px;
  margin: 0;
  color: white;
  border-radius: 3px;
}

#fav-btn:hover {
  background-color: #026aa7;
}
#teamTitle {
  font-size: 18px;
  font-weight: 700;
  line-height: 30px;
  color: #f6f6f6;
  padding: 0 10px;
  background-color: transparent;
  border-radius: 3px;
  margin-right: 10px;
}

#teamTitle:hover {
  background-color: #026aa7;
}
</style>