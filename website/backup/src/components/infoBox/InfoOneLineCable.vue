<template>
    <g :transform=gPosition :visibility=showOLInfo class="infoBox">
        <rect x="0" y="0" rx="8" ry="8" width="75" :height="heightOLBox()" fill="white"  stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
        <text transform="translate(5 5)" fill="black">
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="700" x="26" y="5">Cable {{id + 1}}</tspan>
            <tspan v-for="ul in uloads()" :key="ul.id" font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" :y="ul.y">- {{ul.value}} A [{{ul.confidence}}%]</tspan>
        </text>
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
            return (this.infoCblVis)? "visible": "hidden";
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
        ...mapState({
            uCableLoads: state => state.uCableLoads,
            infoCblVis: state => state.infoCblVis
        })
    },
    methods: {
        heightOLBox: function() {
            return 15 + this.uloads().length * 10;
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
        }
    }
}
</script>

<style lang="scss" scoped>
@import "@/scss/infoBox.scss";
</style>