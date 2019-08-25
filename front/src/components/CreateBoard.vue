<template>
  <div id="login-component">
    <div id="blue-bg-div">
      <h1>Stwórz Board</h1>
      <form>
        <br />
        <input
          type="text"
          name="boardname"
          v-model="boardname"
          maxlength="100"
          size="25"
          placeholder="Nazwa Boardu"
          pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
          class="form-control col-md-3"
        />
        <br />
        <input
          type="text"
          name="boardlink"
          v-model="boardlink"
          maxlength="20"
          size="25"
          placeholder="URL boardu"
          class="form-control col-md-3"
        />
        <br />
        <div class="form-row">
          <input
            type="text"
            name="useradd"
            v-model="useradd"
            maxlength="20"
            size="25"
            placeholder="Dodaj użytkownika (mail)"
            class="form-control col-md-3"
          />
          <button class="btn btn-light">Dodaj</button>
        </div>
        <br />
        <div class="custom-control custom-checkbox col-md-3">
          <input type="checkbox" class="custom-control-input" id="customCheck1" v-model="privateboard" />
          <label class="custom-control-label">Prywatny?</label>
        </div>
        <br />
        <!-- <input type="submit" value="Zarejestruj się" @click="Register()" disabled> -->
      </form>
      <button @click="createNewBoard()" class="btn btn-light">Rejestruj</button>
    </div>
  </div>
</template>


<script>
import axios from "axios";

export default {
  data: function() {
    return {
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
      ],
      boardname: "",
      boardlink: "",
      useradd: "",
      privateboard: ""
    };
  },
  methods: {
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
