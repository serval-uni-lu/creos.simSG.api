<template lang="pug">
    #alert
        i(class="far fa-check-circle symbol")
        span {{text}}
        <svg v-on:click="removeErrorMessage">
            <use xlink:href="#close-button"></use>
        </svg>
</template>


<script>
import { mapMutations } from 'vuex'


export default {
    props: {
        text: String
    },
    methods: {
        ...mapMutations(['removeErrorMessage'])
    },
}
</script>

<style lang="scss" scoped>
#alert {
    position: absolute;
    z-index: 30;
    bottom: 10px;
    right: 10px;
    background-color: #FEE8E6;
    color: #F44335;
    text-align: left;

    i.symbol {
        margin: 15px
    }

    span {
        margin-right: 30px;
    }

    svg {
        position: absolute;
        display: block;
        top: 5px;
        right: 5px;
        width: 10px;
        height: 10px;

        stroke-width: 2;
        stroke: red;
    }

}
</style>