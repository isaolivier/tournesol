<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app',
  components: {
  }
}
</script>

<style>
#app {
  font-family: 'FontAwesome', Helvetica, Arial, sans-serif;
  color: #2c3e50;
}

body{
  margin: 0;
  overflow: hidden;
}

.el-tabs, .el-tab-pane {
  position: relative;
  height: calc(100% - 44px);
}

.el-tabs__content {
  height: calc(100% - 74px);
  overflow-y: scroll;
}
</style>
