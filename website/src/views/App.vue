<template>
    <div id="app">
        <Header />
        <div id="nav">
            <router-link to="/">Home</router-link> | 
            <router-link to="/scenario">Scenarios</router-link> | 
            <router-link to="/scenario-builder">Scenario Builder</router-link> | 
            <router-link to="/lux-sg">Luxembourg Smart Grid</router-link> 
        </div>
        <router-view/>
    </div>
</template>






<script lang="ts">
// @ is an alias to /src
import Header from '@/components/Header.vue'

export default {
    name: 'app',
    components: {
        Header
    }
}
</script>

<style lang="scss">
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        text-align: center;
        color: #2c3e50;
    }

    #nav {
        padding: 30px;

        a {
            font-weight: bold;
            color: #2c3e50;

            &.router-link-exact-active {
                color: #42b983;
            }
        }
    }

</style>