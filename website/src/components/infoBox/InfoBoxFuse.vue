<template>
    <g :transform=gPosition :visibility=showOLInfo class="infoBox">
        <rect x="0" y="0" rx="8" ry="8" width="90" :height="heightOLBox()" fill="white"  stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
        <text transform="translate(5 5)" fill="black">
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="700" x="26" y="5">Fuse {{id + 1}}</tspan>
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" y="15">Status: {{status}} [{{fuses[id].confidenceLevel}}%]</tspan>
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" y="25">Load:</tspan>
            <tspan v-for="ul in uloads()" :key="ul.id" font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" :y="ul.y">- {{ul.value}} A [{{ul.confidence}}%]</tspan>
        </text>
    </g>
</template>

<script>
import { mapState, mapMutations } from 'vuex'

export default {
    props: {
        id: Number,
        xRect: Number,
        yRect: Number,
    },
    computed: {
        showOLInfo: function() {
            return (this.infoOverLayerVis)? "visible": "hidden";
        },
        gPosition: function() {
            return "translate(" + (this.xRect - 37.5) + " " + (this.yRect - this.heightOLBox()/2) + ")";
        },
        status: function() {
            return (this.fuses[this.id].isClosed)? "Closed" : "Open";
        },
        ...mapState({
            fuses: state => state.fuses,
            infoOverLayerVis: state => state.infoOverLayerVis
        })
    },
    methods: {
        heightOLBox: function() {
            return 15 + (this.uloads().length + 2) * 10;
        },
        uloads: function() {
            var uloads = this.fuses[this.id].uLoad;
            if(uloads === undefined || uloads.length === 0) {
                return [{id: 0, value: "??", confidence: "??", y: 35}]
            }

            var result = []
            for(var ul=0; ul<uloads.length; ul++) {
                var curr = {
                    id: ul,
                    value: uloads[ul].value.toFixed(2),
                    confidence: (uloads[ul].confidence * 100).toFixed(2),
                    y: 25 + 10*(ul+1)
                };
                result.push(curr);
            }

            return result;
        }
    }
}
</script>



<style lang="scss" scoped>
@import "@/scss/infoBox.scss";
</style>