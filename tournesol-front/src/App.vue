<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>
  export default {
    name: 'app',
    components: {}
  }
</script>

<style>

    #app {
        font-family: Arial;
        color: #2c3e50;
    }

    body {
        margin: 0;
        overflow: hidden;
    }

    .el-tabs, .el-tab-pane {
        position: relative;
        height: calc(100% - 44px);
    }

    div.el-tabs__content {
        height: calc(100% - 74px);
        overflow-y: scroll !important;
    }

    .clickable {
        display: inline-block;
        border: solid 1px grey;
        border-radius: 5px;
        padding: 2px 6px;
        margin: 4px 0;
    }

    .input-icon:after {
        font-family: 'FontAwesome';
        position: absolute;
        right: 10px;
        top: 0px;
        font-size: 1.8em;
        color: #CCC
    }

    .greybox {
        border: solid 1px #D1DBE5;
        background-color: #EEF1F6;
    }
</style>
