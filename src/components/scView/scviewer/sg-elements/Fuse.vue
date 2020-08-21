<template>
<!--    <g class="fuse" v-bind:class="{fClosed: fuses[id].isClosed, selected: isSelected}" v-on:click="showInspector($event, id)">-->
    <g class="fuse">
<!--        <title>Status: {{status}}; Load: {{load}}</title>-->
        <title>Status: ; Load: ;</title>
        <rect :x="xRect" :y="yRect" width="10" height="10" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <text :transform="translate">
            <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" x="0" y="11">Fuse {{id + 1}}</tspan>
        </text>
    </g>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from "vue-property-decorator";

    @Component
    export default class Fuse extends Vue {
        @Prop() id!: number;
        @Prop() xRect!: number;
        @Prop() yRect!: number;

        @Prop({
            default: "right",
            validator: function(value) {
                return ["left", "right", "top", "bottom"].indexOf(value) !== -1;
            }
        })
        displayLeft!: string;

        @Prop({default: 0}) shiftTextX!: number;
        @Prop({default:0}) shiftTextY!: number;


        get translate(): string {
            let x = this.xRect;
            let y = this.yRect - 2;
            if(this.displayLeft === "left") {
                x = this.xRect - 38;
            } else if(this.displayLeft === "right"){
                x = this.xRect + 11;
            } else if(this.displayLeft === "top") {
                y = this.yRect - 14;
            } else {
                y = this.yRect + 12;
            }
            return "translate(" +  (x + this.shiftTextX) + " " + (y + this.shiftTextY) + ")";
        }

    }
</script>

<style scoped lang="scss">
    @import "@/scss/global-var.scss";

    g.fuse:not(selected) {

        rect {
            fill: white;
            stroke:$color-schema;
        }

        text {
            stroke: none;
            fill: $color-schema;
        }

        &.fClosed {
            rect {
                fill: $color-schema;
            }
        }
    }

    g.fuse.selected {

        rect {
            fill: white;
            stroke: $color-selection;
        }

        text {
            stroke: none;
            fill: $color-selection;
        }

        &.fClosed {
            rect {
                fill: $color-selection;
            }
        }
    }
</style>