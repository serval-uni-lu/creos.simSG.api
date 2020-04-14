<template lang="pug">
     div
        | Load:
        div
            span(v-for="poss in uLoad" v-bind:value="poss") - {{poss.value}} A [{{poss.confidence}} %] <br/>
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
            var uloads = this.uLoads[this.cableId];
            if(uloads === undefined || uloads.length === 0) {
                return [{id: 0, value: "??", confidence: "??", y: 15}]
            }

            var result = []
            for(var ul=0; ul<uloads.length; ul++) {
                var curr = {
                    id: ul,
                    value: uloads[ul].value.toFixed(2),
                    confidence: (uloads[ul].confidence * 100).toFixed(2),
                    y: 5 + 10*(ul+1)
                };
                result.push(curr);
            }
            
            return result;
        },
        ...mapState({
            loads: state => state.cableLoads,
            uLoads: state => state.uCableLoads,
        })
    }

}
</script>
