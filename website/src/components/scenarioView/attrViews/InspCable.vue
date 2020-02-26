<template lang="pug">
     div
        | Load: {{load}}
        div
            span(v-for="poss in uLoad" v-bind:value="poss") {{poss.value.toFixed(2)}} A [{{(poss.confidence * 100).toFixed(2)}} %] <br/>
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
            return (load === undefined || load === -1)? "To be computed..." : load.toFixed(2) + " A";
        },
        uLoad: function() {
            var uload = this.uLoads[this.cableId];
            return (uload === undefined || uload.length === 0)? "To be computed..." : uload;
        },
        ...mapState({
            loads: state => state.cableLoads,
            uLoads: state => state.uCableLoads,
        })
    }

}
</script>
