<template lang="pug">
    div
        | State:
        input(type="checkbox" id="fuseStatus" v-model.boolean="fuses[fuseId].isClosed")
        label(for="fuseStatus") (is closed?)
        br
        | Load:
        | {{load}}

</template>

<script>
import { mapState } from 'vuex'

export default {
    props: {
        fuseId: Number,
    },
    computed: {
        load: function() {
            var load = this.fuses[this.fuseId].load;
            return (load === -1)? "To be computed..." : load;
        },
        ...mapState({
            fuses: state => state.fuses
        })
    }

}
</script>

<style scoped>
input {
    width: 15%;
    margin-left: 20px; 
}
</style>