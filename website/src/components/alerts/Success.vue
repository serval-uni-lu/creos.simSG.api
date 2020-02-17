<template lang="pug">
    #alert
        i(class="far fa-check-circle symbol")
        | {{text}}
        i(class="far fa-times-circle close" v-on:click="removeSuccessMessage")
</template>


<script>
import { mapMutations } from 'vuex'


export default {
    props: {
        text: String
    },
    methods: {
        ...mapMutations(['removeSuccessMessage'])
    },
}
</script>

<style lang="scss" scoped>
#alert {
    position: absolute;
    z-index: 30;
    bottom: 10px;
    right: 10px;
    background-color: #E9F5EA;
    color: #4CAF50;
    min-width: 20%;
    text-align: left;

    i.symbol {
        margin: 10px
    }

    i.close {
        position: relative;
        top: 2px;
        right: 2px;
        color: green;
        font-size: 0.9em;
    }
}
</style>