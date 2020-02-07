<template>
    <div id="app">
        <Header />
        <Menu />
    </div>
</template>






<script lang="ts">
// @ is an alias to /src
import Header from '@/components/Header.vue'
import Menu from '@/components/Menu.vue'

export default {
    name: 'app',
    components: {
        Header,
        Menu
    }
}
</script>

<style lang="scss">

#app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    text-align: center;
    color: $text-color;
}


</style>