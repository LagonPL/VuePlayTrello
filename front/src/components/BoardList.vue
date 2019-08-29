<template>
  <div id="board-list-component" class="white-bg-div">
    <div class="collection">
      <h3 class="collection-title">Tablice</h3>
      <div class="collection-item" v-for="(board, leng) in this.$root.boards">
        <router-link
          :to="{ name: 'board', params: { id: leng + 1 }}"
          tag="button"
          class="collection-item-btn"
        >
          <span class="board-title">{{ board.name }}</span>
		  <!-- <span class="board-title">{{ board.name }} - {{board.ownerUser}}</span> -->
          <!-- <button class="star-btn" >&#9760</button> -->
        </router-link>
      </div>
      <div class="collection-item" v-if="this.status.IsLogged">
        <router-link
          to="/createboard"
          id="create-board-btn"
          class="collection-item"
        >Utwórz nową tablicę...</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data: function() {
    return {
      status: "",
      boards: [
        {
          name: "Tab1",
          listts: [
            {
              name: "list1.1",
              cards: [
                {
                  name: "card1.1.1",
                  description: "desc1.1.1",
                  comments: [
                    { text: "koment1.1.1" },
                    { text: "koment1.1.2" },
                    { text: "koment1.1.3" },
                    { text: "koment1.1.4" },
                    { text: "koment1.1.5" }
                  ]
                },
                {
                  name: "card1.1.2",
                  description: "desc1.1.2",
                  comments: [
                    { text: "koment1.2.1" },
                    { text: "koment1.2.2" },
                    { text: "koment1.2.3" },
                    { text: "koment1.2.4" },
                    { text: "koment1.2.5" }
                  ]
                },
                {
                  name: "card1.1.3",
                  description: "desc1.1.3",
                  comments: [
                    { text: "koment1.3.1" },
                    { text: "koment1.3.2" },
                    { text: "koment1.3.3" },
                    { text: "koment1.3.4" },
                    { text: "koment1.3.5" }
                  ]
                }
              ]
            },
            {
              name: "list1.2",
              cards: [
                { name: "card1.2.1", description: "desc1.2.1" },
                { name: "card1.2.2", description: "desc1.2.2" },
                { name: "card1.2.3", description: "desc1.2.3" }
              ]
            },
            {
              name: "list1.3",
              cards: [
                { name: "card1.3.1", description: "desc1.3.1" },
                { name: "card1.3.2", description: "desc1.3.2" },
                { name: "card1.3.3", description: "desc1.3.3" }
              ]
            }
          ]
        },
        {
          name: "Tab2",
          listts: [
            {
              name: "list2.1",
              cards: [
                { name: "card2.1.1", description: "desc2.1.1" },
                { name: "card2.1.2", description: "desc2.1.2" },
                { name: "card2.1.3", description: "desc2.1.3" }
              ]
            },
            {
              name: "list2.2",
              cards: [
                { name: "card2.2.1", description: "desc2.2.1" },
                { name: "card2.2.2", description: "desc2.2.2" },
                { name: "card2.2.3", description: "desc2.2.3" }
              ]
            },
            {
              name: "list2.3",
              cards: [
                { name: "card2.3.1", description: "desc2.3.1" },
                { name: "card2.3.2", description: "desc2.3.2" },
                { name: "card2.3.3", description: "desc2.3.3" }
              ]
            }
          ]
        },
        {
          name: "Tab3",
          listts: [
            {
              name: "list3.1",
              cards: [
                { name: "card3.1.1", description: "desc3.1.1" },
                { name: "card3.1.2", description: "desc3.1.3" },
                { name: "card3.1.3", description: "desc3.1.3" }
              ]
            },
            {
              name: "list3.2",
              cards: [
                { name: "card3.2.1", description: "desc3.2.1" },
                { name: "card3.2.2", description: "desc3.2.2" },
                { name: "card3.2.3", description: "desc3.2.3" }
              ]
            },
            {
              name: "list3.3",
              cards: [
                { name: "card3.3.1", description: "desc3.3.1" },
                { name: "card3.3.2", description: "desc3.3.2" },
                { name: "card3.3.3", description: "desc3.3.3" }
              ]
            }
          ]
        }
      ]
    };
  },
  mounted: function() {
    this.getAllBoards();
    this.getUsername();
  },
  methods: {
    getAllBoards() {
      const vm = this;
      var dlugosc;
      axios
        .get("http://localhost:9000/api/boards/getAll")
        .then(function(response) {
          console.log(response.data);
          if (response.data.body != 0) {
            vm.$root.boards = response.data.body;
            console.log(vm.$root.boards);
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
          console.log(response.data.body);
          this.status = response.data.body;
          console.log(this.body.CurrentUser);
        })
        .catch(function(error) {});
    },
    createNewBoard() {
      const vm = this;
      var newTitle = prompt("Podaj nazwę nowej tablicy");
      var srcBoardNr = prompt("Podaj numer źródła");
      if (newTitle.length != 0 && srcBoardNr.length != 0) {
        axios
          .post("http://localhost:9000/api/boards/add", {
            name: newTitle,
            listts: vm.boards[srcBoardNr].listts
          })
          .then(response => {
            console.log(response);
            vm.getAllBoards();
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

<style>
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
  
  .board-title {
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