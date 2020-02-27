<template>
    <g>
        <g class="cable" v-bind:class="{selected: isSelected}" v-on:click="showInspector();">
            <title>Load: {{load()}}</title>
            <line :x1=line.x1 :y1=line.y :x2=line.x2 :y2=line.y stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
            <circle :cx=circle.x :cy=line.y r="5" />
        </g>
        <g :transform=gPosition :visibility=showOLInfo class="infoBox">
            <rect x="0" y="0" rx="8" ry="8" width="75" :height="heightOLBox()" fill="white"  stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
            <text transform="translate(5 5)" fill="black">
                <tspan font-family="Helvetica Neue" font-size="8" font-weight="700" x="26" y="5">Load</tspan>
                <tspan v-for="ul in uloads()" :key="ul.id" font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" :y="ul.y">- {{ul.value}} A [{{ul.confidence}}%]</tspan>
            </text>
        </g>    
    </g>
</template>


<script>
import { mapState } from 'vuex'

export default {
    props: {
        id: Number,
        line: Object,
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
            return this.circle.x - 25;
        },
        yLoadInfo: function() {
            return this.line.y - 22.5;
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