<template lang="pug">
    div
        p.title Actions

        button(v-if="!isApproximating" type="button" class="btn btn-primary" v-on:click="startApproximation()")
            <i class="fa fa-spinner fa-spin" v-bind:class="{show: isApproximating}"></i> Approximate load
        button(v-else type="button" class="btn btn-danger" v-on:click="stopApproximation()")
            <i class="fa fa-spinner fa-spin"></i> Cancel load approximation
</template>

<script>
import { mapState, mapMutations } from 'vuex'

export default {
   computed: {
       ...mapState({
            isApproximating: state => state.isApproximating,
        })
   },
    methods: {
        ...mapMutations(['startApproximation', 'stopApproximation'])
    }
}
</script>

<style lang="scss" scoped>
i:not(.show) {
    display: none;
}

i.show {
    display: inline-block;
}
</style>