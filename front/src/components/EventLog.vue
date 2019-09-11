<template>
  <div>
    <scrolly>
  	  <scrolly-viewport>
        <p>{{eventLogText}}</p>        
      </scrolly-viewport>
		  	<scrolly-bar axis="y"></scrolly-bar>
		</scrolly>
  </div>
</template>

<script>
import axios from "axios";
import Bootstrap from "bootstrap";

export default {
  destroyed() {},
  mounted() {},
  updated() {},
  data() {
    return { };
  },
  methods: {},
  props: {
    eventLogText: String,
    default: "Default event log text"
  },
  name: "EventLog"
};
</script>


<style lang="scss" scoped>
div {
  border-left-style: solid;
  word-wrap: break-word;
  white-space: pre-wrap;
  color: white;
}
p{
  margin-left: 10px;
}
</style>