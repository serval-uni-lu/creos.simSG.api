<template>
    <g>
        <g class="hidden" v-on:click="showInspector();">
            <title>Load: {{load(0)}}</title>
            <line :x1=line1.x1 :y1=line1.y1 :x2=line1.x2 :y2=line1.y2 stroke-linecap="round" stroke-linejoin="round"/>
            <line :x1=line2.x1 :y1=line2.y1 :x2=line2.x2 :y2=line2.y2 stroke-linecap="round" stroke-linejoin="round"/>
        </g>
        <g class="cable" v-bind:class="{selected: isSelected}" v-on:click="showInspector();">
            <title>Load: {{load()}}</title>
            <line :x1=line1.x1 :y1=line1.y1 :x2=line1.x2 :y2=line1.y2 stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
            <line :x1=line2.x1 :y1=line2.y1 :x2=line2.x2 :y2=line2.y2 stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
            <circle :cx=line1.x1 :cy=circle.y r="5"/>
            <circle :cx=circle.x :cy=circle.y r="7" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        </g>
        <g :transform=gPosition>
            <rect x="0" y="0" rx="8" ry="8" width="54" height="45" fill="white" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
            <text transform="translate(5 5)" fill="black">
                <tspan id="AHAHAHAHAHA" font-family="Helvetica Neue" font-size="8" font-weight="700" fill="black" x="12" y="5">Load</tspan>
                <tspan font-family="Helvetica Neue" font-size="8" font-weight="400" fill="black" x="0" y="15">- 10A [10%]</tspan>
                <tspan font-family="Helvetica Neue" font-size="8" font-weight="400" fill="black" x="0" y="25">- 20 A [80%]</tspan>
                <tspan font-family="Helvetica Neue" font-size="8" font-weight="400" fill="black" x="0" y="35">- 0 A [10%]</tspan>
            </text>
        </g>    
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
        })
    },
    methods: {
        load: function() {
            var p_load = this.loads[this.id];
            return (p_load === undefined || p_load === -1)? "To be computed..." : p_load.toFixed(2) + " A";
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

.draggable {
    cursor: move;
    user-select: none;
}

</style>