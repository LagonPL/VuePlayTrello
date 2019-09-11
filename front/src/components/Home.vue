<template>
  <div class="testbuttons">
      <p><h1>TrelloXollerT</h1></p>
      <p><h3>testowe przyciski</h3></p>
      <button @click="getUsers()" class="btn btn-light">Lista userów</button>
      <button @click="getBoards()" class="btn btn-light">Lista boardów</button>
      <button @click="getLists()" class="btn btn-light">Lista list</button>
      <button @click="getCards()" class="btn btn-light">Lista kart</button>
      <button @click="getLabels()" class="btn btn-light">Lista labeli</button>
      <button @click="getTasks()" class="btn btn-light">Lista tasków</button>
      <button @click="getTeams()" class="btn btn-light">Lista teamów</button>
      <button @click="getComments()" class="btn btn-light">Lista komentarzy</button>
  </div>
</template>

<script>
import axios from "axios";
import Bootstrap from "bootstrap";

export default {
  data() {
    return { };
  },
  methods: {
    getUsers() {
      const vm = this;
      axios
        .get("http://localhost:9000/api/user/getAll")
        .then(response => {
            console.log("Users");
          console.log(response.data.body);
          if (response.data.body != 0) {
            this.users = response.data.body;
          }
        });
    },
    getBoards() {
      const vm = this;
      axios
        .get("http://localhost:9000/api/boards/getAll")
        .then(response => {
            console.log("Boards");
          console.log(response.data.body);
          if (response.data.body != 0) {
            this.boards = response.data.body;
          }
        });
    },
    getCards() {
      const vm = this;
      axios
        .get("http://localhost:9000/api/card/getAll")
        .then(response => {
            console.log("Cards");
          console.log(response.data.body);
          if (response.data.body != 0) {
            this.cards = response.data.body;
          }
        });
    },
    getComments() {
      const vm = this;
      axios
        .get("http://localhost:9000/api/comment/getAll")
        .then(response => {
            console.log("Comments");
          console.log(response.data.body);
          if (response.data.body != 0) {
            this.comments = response.data.body;
          }
        });
    },
    getLabels() {
      const vm = this;
      axios
        .get("http://localhost:9000/api/label/getAll")
        .then(response => {
            console.log("Labels");
          console.log(response.data.body);
          if (response.data.body != 0) {
            this.labels = response.data.body;
          }
        });
    },
    getLists() {
      const vm = this;
      axios
        .get("http://localhost:9000/api/listt/getAll")
        .then(response => {
            console.log("Lists");
          console.log(response.data.body);
          if (response.data.body != 0) {
            this.listts = response.data.body;
          }
        });
    },
    getTasks() {
      const vm = this;
      axios
        .get("http://localhost:9000/api/task/getAll")
        .then(response => {
            console.log("Tasks");
          console.log(response.data.body);
          if (response.data.body != 0) {
            this.tasks = response.data.body;
          }
        });
    },
    getTeams() {
      const vm = this;
      axios
        .get("http://localhost:9000/api/team/getAll")
        .then(response => {
            console.log("Teams");
          console.log(response.data.body);
          if (response.data.body != 0) {
            this.teams = response.data.body;
          }
        });
    },


  },
  props: {
    eventLogText: String,
    default: "Default event log text"
  },
  name: "EventLog"
};
</script>


<style>
.testbuttons {
    margin: auto;
    width: 50%;
    padding: 10px;
}
.testbuttons h1{
   text-align: center;
   color: #ffffff;
}
.testbuttons h3{
   color: #ffffff;
}
</style>