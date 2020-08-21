import {ElmtType} from "@/utils/selection";
<template>
    <g v-on:click="eventHandler()" v-bind:class="{selected: isSelected}">
        <rect :x="location.x" :y="location.y" width="37" height="27.04319" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
        <circle :cx="cX" :cy="cY" r="7.37542706758215" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
        <text :transform="translate" fill="black">
            <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="5.206" y="11">{{consumption}} A</tspan>
        </text>
        <line :x1="x" :y1="location.y" :x2="x" :y2="y2" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
    </g>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import {Point} from "@/utils/SvgTypes";
    import {Selection, ElmtType} from "@/utils/selection";
    import {namespace} from "vuex-class";
    import {Cable} from "@/utils/grid";

    const inspState = namespace('InspectorState');
    const gridState = namespace('GridSCState');

    @Component
    export default class Meter extends Vue {
        @Prop() id!: number;
        @Prop() location!: Point;

        @inspState.State
        public selectedElement!: Selection;

        @gridState.State
        public allCables!: Array<Cable>;

        @inspState.Mutation
        public select!: (elmt: Selection) => void;

        public selection: Selection = new Selection(this.id, ElmtType.Meter);

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

        get isSelected(): boolean {
            return this.selection.equals(this.selectedElement);
        }

        get consumption(): number {
            return this.allCables[this.id].consumption;
        }

        public eventHandler(): void {
            this.select(this.selection);
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