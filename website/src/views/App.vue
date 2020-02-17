<template>
    <div id="app">
        <Header />
        <router-view/>

        <Alert v-if="successMessage !== ''" :text="successMessage"/>
    </div>
</template>


<script>

import Header from '@/components/Header.vue'
import Alert from "@/components/alerts/Success.vue"
import { mapState } from 'vuex'

export default {
    name: 'app',
    components: {
        Header,
        Alert
    }, 
    computed: {
        ...mapState({
            successMessage: state => state.successMessage,
        })
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

#nav {
    a {
        font-size: $menu-text-size;

        &.router-link-exact-active {
            color: $menu-selected-color;
            font-weight: bold;
        }
    }
}
</style>