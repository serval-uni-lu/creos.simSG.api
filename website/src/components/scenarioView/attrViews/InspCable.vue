<template lang="pug">
     div
        | Load: {{load}}
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
            return (load === -1)? "To be computed..." : load.toFixed(2) + " A";
        },
        ...mapState({
            loads: state => state.loads
        })
    }

}
</script>
