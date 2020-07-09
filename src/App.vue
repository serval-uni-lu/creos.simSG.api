<template>
    <section class="simsg">
        <Header />
       <NavigationBar />
    </section>
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

<style lang="scss" >
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

        a {
            text-decoration-line: none;
            color: #2B92D1;
        }
    }

    .simsg > header {
        height: 10%;
    }

    .simsg > nav {
        height: 5%;
    }

</style>
