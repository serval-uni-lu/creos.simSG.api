<template lang="pug">
    div 
        | Consumption:
        input(type="number", min="0", v-model.number="consumptions[meterId]")
</template>

<script>
import { mapState } from 'vuex'

export default {
    props: {
        meterId: Number,
    },
    computed: {
        ...mapState({
            consumptions: state => state.consumptions,
        })
    },
}
</script>

<style scoped>
input {
    width: 30%;
    margin-left: 20px; 
}
</style>