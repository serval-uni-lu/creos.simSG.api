<template lang="pug">
     div
        | Load:
        | {{load}}
</template>


<script>
import { mapState } from 'vuex'

export default {
    props: {
        cableId: Number,
    },
    computed: {
        load: function() {
            var load = this.loads[this.cableId];
            return (load === -1)? "To be computed..." : load;
        },
        ...mapState({
            loads: state => state.loads
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