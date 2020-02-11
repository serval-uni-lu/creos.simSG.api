<template>
    <g v-on:click="showInspector(id + 1)">
        <rect :x="xRect" :y="yRect" width="37" height="27.04319" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
        <circle :cx="cX" :cy="cY" r="7.37542706758215" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
        <text :transform="translate" fill="black">
            <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="5.206" y="11">{{consumptions[id]}} A</tspan>
        </text>
        <line :x1="xLine" :y1="yRect" :x2="xLine" :y2="y2Line" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
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
        xLine: function() {
            return this.xRect + 18.5
        },
        y2Line: function() {
            return this.yRect - 10.7;
        },
        cX: function() {
            return this.xRect + 11.8;
        },
        cY: function() {
            return this.yRect + 13.5;
        },
        translate: function() {
            return "translate(" +  (this.xRect) + " " + (this.yRect + 27) + ")";
        },
        ...mapState({
            consumptions: state => state.consumptions,
        })
    },
    methods: {
        ...mapMutations(['showInspector'])
    }
}
</script>