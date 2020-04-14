<template>
    <g class="cable" v-bind:class="{selected: isSelected}" v-on:click="showInspector();">
        <title>Load: {{load()}}</title>
        <line :x1=line1.x1 :y1=line1.y1 :x2=line1.x2 :y2=line1.y2 stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <line :x1=line2.x1 :y1=line2.y1 :x2=line2.x2 :y2=line2.y2 stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <circle :cx=line1.x1 :cy=circle.y r="5"/>
        <circle :cx=circle.x :cy=circle.y r="7" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
    </g> 
</template>

<script>
import { mapState } from 'vuex'

export default {
    props: {
        id: Number,
        line1: Object,
        line2: Object,
        circle: Object
    },
    computed: {
        showOLInfo: function() {
            return (this.infoOverLayerVis)? "visible": "hidden";
        },
        gPosition: function() {
            return "translate(" + (this.xLoadInfo) + " " + this.yLoadInfo + ")";
        },
        xLoadInfo: function() {
            return this.line1.x1 - 25;
        },
        yLoadInfo: function() {
            return this.circle.y - 22.5;
        },
        isSelected: function() {
            return this.selectedElmt.isSameAs(this.id, 'cable')
        },
        ...mapState({
            selectedElmt: state => state.selectedElmt,
            loads: state => state.cableLoads,
            uCableLoads: state => state.uCableLoads,
            infoOverLayerVis: state => state.infoOverLayerVis
        })
    },
    methods: {
        heightOLBox: function() {
            return 15 + this.uloads().length * 10;
        },
        load: function() {
            var p_load = this.loads[this.id];
            return (p_load === undefined || p_load === -1)? "To be computed..." : p_load.toFixed(2) + " A";
        },
        uloads: function() {
            var uloads = this.uCableLoads[this.id];
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
        showInspector: function() {
            let info = {
                elemtId: this.id,
                elemtType: "cable"
            }
            this.$store.commit('showInspector', info)
        },
    }
}
</script>

<style lang="scss" scoped>
@import "@/scss/cable.scss";
</style>