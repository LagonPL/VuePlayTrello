<template>
  <div>
    {{eventLogText}}
  </div>
</template>

<script>
import axios from "axios";
import Bootstrap from "bootstrap";
export default {
  destroyed() {},
  mounted() {},
  data() {
    return {};
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
  
  word-wrap: break-word;
  white-space: normal;
  margin-left: 10px;
}
</style>