<template>    
  <div>
    <router-link to="/createTeam" tag="button" id="fav-btn">
      <span class="glyphicon glyphicon-plus"></span>
    </router-link>
    <div class="collection">
      <h3 class="collection-title">Zespoły</h3>
      <div class="collection-item" v-for="(team, leng) in this.$root.teams">
        <router-link
          :to="{ name: 'team', params: { id: leng + 1 }}"
          tag="button"
          class="collection-item-btn"
        >
          <span class="team-title">{{ team.name }}</span>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Vue from "vue";

export default {
  data: function() {
    return {
      status: "",
      teams: [
        {
          name: "Team1",
          members: [
            {
              name: "Member1"
            }
          ]
        }
      ]
    };
  },
  mounted: function() {
    this.getAllTeams();
    this.getUsername();
  },
  updated: function() {

  },
  methods: {
    getAllTeams() {
      const vm = this;
      var dlugosc;
      axios
        .get("http://localhost:9000/api/team/myteam")
        .then(function(response) {
          console.log(response.data);
          if (response.data.body != 0) {
            vm.$root.teams = response.data.body;
            console.log("getallteams");
            console.log(response.data.body);
            console.log(vm.$root.teams);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    async getUsername() {
      axios
        .get("http://localhost:9000/api/user/username")
        .then(response => {
         //console.log(response.data.body);
          console.log("getusername");
          this.status = response.data.body;
          console.log(this.body.CurrentUser);
        })
        .catch(function(error) {});
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
#create-board-btn {
    background-color: #e2e4e6;
    color: #838c91;
  }
  
  #create-board-btn:hover {
    background-color: #d6dadc;
  }
  
  .star-btn {
    font-size: 30px;
    color: black;
    position: absolute;
    top: 0;
    right: 0;
    display: none;
  }
  
  div.collection-item:hover .star-btn {
    display: inline;
  }
  
  .star-btn:hover {
    color: black;
  }
  
  .white-bg-div {
    background-color: white;
    height: 100%;
  }
  
  .collection {
    padding: 5px;
    margin: 5px;
    white-space: normal;
  }
  
  .collection-title {
    color: #333;
    margin-left: 5px;
  }
  
  .collection-item {
    display: inline-block;
    position: relative;
    height: 85px;
    margin: 0 20px 20px 0;
    border-radius: 3px;
    padding: 0;
    width: 250px;
    background-color: #026aa7;
  }
  
  .collection-item-btn {
    width: 100%;
    height: 100%;
    padding: 0;
    margin: 0;
    background-color: transparent;
  }
  
  .collection-item-btn:hover {
    background-color: #055a8c;
  }
  
  .team-title {
    position: absolute;
    top: 0;
    left: 0;
    margin: 10px 5px;
    color: white;
    font-weight: 700;
    font-size: 15px;
  }
/* @import '../../sass/board-list.scss'; */
</style>