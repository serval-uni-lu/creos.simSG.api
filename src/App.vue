<template lang="pug">
    section.simsg
        Header
        NavigationBar
        router-view
</template>

<script lang="ts">
    import { Component, Vue } from 'vue-property-decorator';
    import Header from "@/components/Header.vue";
    import NavigationBar from "@/components/NavigationBar.vue";

    @Component({
        components: {NavigationBar, Header}
    })
    export default class App extends Vue {
    }
</script>

<style lang="scss">
    body, html {
        margin: 0;
        height: 100%;
    }

    * {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }

    .simsg {
        height: 100%;
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        color: #034164;
        display: flex;
        flex-direction: column;
        align-items: center;

        a {
            text-decoration-line: none;
            color: #2B92D1;
        }

        a.router-link-exact-active, a.router-link-active:not(.home) {
            color: #6F2683;;
            font-weight: bold;
        }
    }

    .simsg > header {
        height: 80px;
        width: 100%;
    }

    .simsg > nav {
        height: 40px;
        width: 100%;
    }

    .simsg > section {
        flex: 1;
        width: 100%;
    }

</style>
