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
          ref="boardName"
          maxlength="100"
          size="25"
          placeholder="Nazwa Boardu"
          pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
          class="form-control col-md-3"
        />
        <br />
        <div class="custom-control custom-checkbox col-md-3">
          <input
            type="checkbox"
            name="isPrivate"
            id="isPrivate"
            ref="isPrivate"
            class="custom-control-input"
            v-model="privateboard"
            true-value="true"
            false-value="false"
          />
          <label for="isPrivate" class="custom-control-label">Prywatny?</label>
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
      privateboard: "false"
    };
  },
  methods: {
    createNewBoard() {
      const vm = this;
      var newTitle = this.$refs.boardName.value;
      var privateStatus = vm.privateboard;
      console.log(privateStatus);
      if (newTitle.length != 0) {
        axios
          .post("http://localhost:9000/api/boards/add", {
            name: newTitle,
            listts: vm.boards["0"].listts,
            isPrivate: privateStatus
          })
          .then(response => {
            console.log(response);
            this.$router.push("/boardList");
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
