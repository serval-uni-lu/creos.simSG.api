<template>
<!--    <g v-on:click="showInspector(id)" v-bind:class="{selected: isSelected}">-->
    <g>
        <rect :x="location.x" :y="location.y" width="37" height="27.04319" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
        <circle :cx="cX" :cy="cY" r="7.37542706758215" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
        <text :transform="translate" fill="black">
<!--            <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="5.206" y="11">{{consumptions[id]}} A</tspan>-->
            <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="5.206" y="11">XX A</tspan>
        </text>
        <line :x1="x" :y1="location.y" :x2="x" :y2="y2" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
    </g>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from "vue-property-decorator";
    import {Point} from "@/utils/SvgTypes";

    @Component
    export default class Meter extends Vue {
        @Prop() id!: number;
        @Prop() location!: Point;

        get x(): number {
            return this.location.x + 18.5;
        }

        get y2(): number {
            return this.location.y - 10.7;
        }

        get cX(): number {
            return this.location.x + 11.8;
        }

        get cY(): number {
            return this.location.y + 13.5;
        }

        get translate(): string {
            return "translate(" +  (this.location.x) + " " + (this.location.y + 27) + ")";
        }


    }
</script>

<style lang="scss" scoped>
    @import "@/scss/global-var.scss";

    g:not(selected) {
        stroke: $color-schema;
    }

    g.selected {
        stroke: $color-selection;
    }
</style>