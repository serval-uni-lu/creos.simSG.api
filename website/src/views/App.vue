<template>
    <div id="app">
        <Header />
        <!-- <Menu /> -->
        <router-view/>
    </div>
</template>






<script lang="ts">
// @ is an alias to /src
import Header from '@/components/Header.vue'
// import Menu from '@/components/Menu.vue'

export default {
    name: 'app',
    components: {
        Header/*,
        Menu*/
    }
}
</script>

<style lang="scss">

body {
    margin: 0;
}

#app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    text-align: center;
    color: $text-color;

    p {
        font-size: $body-text-size;
    }

    h2 {
        font-size: $page-title-size;
    }

    a {
        text-decoration-line: none;
        color: $link-color;
    }

   
}


</style>