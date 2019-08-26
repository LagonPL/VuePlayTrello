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
        <!-- <br /> -->
        <!-- <input
          type="text"
          name="boardlink"
          v-model="boardlink"
          maxlength="20"
          size="25"
          placeholder="URL boardu"
          class="form-control col-md-3"
        /> -->
        <!-- <br />
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
        </div> -->
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
          name: "Tab1"          
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
      if (newTitle.length != 0) {
        axios
          .post("http://localhost:9000/api/boards/add", {
            name: newTitle,
            listts: vm.boards["0"].listts
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
