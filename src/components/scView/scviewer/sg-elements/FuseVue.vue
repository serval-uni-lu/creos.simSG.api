<template>
    <g class="fuse" v-bind:class="{fClosed: isClosed, selected: isSelected}" v-on:click="eventHandler($event)">
        <title>Status: {{status}}; Load: {{uLoads()}}</title>
        <rect :x=location.x :y=location.y width="10" height="10" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <text :transform="translate">
            <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" x="0" y="11">Fuse {{id + 1}}</tspan>
        </text>
    </g>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import {Fuse} from "@/utils/grid";
    import {namespace} from "vuex-class";
    import {ElmtType, Selection} from "@/utils/selection";
    import {prettyStr} from "@/utils/uLoadsUtils";
    import {Point} from "@/utils/SvgTypes";

    const gridState = namespace('GridSCState');
    const inspState = namespace('InspectorState');

    @Component
    export default class FuseVue extends Vue {
        @Prop() id!: number;
        @Prop() location!: Point;

        @Prop({
            default: "right",
            validator: function(value) {
                return ["left", "right", "top", "bottom"].indexOf(value) !== -1;
            }
        })
        displayLeft!: string;

        @Prop({default: 0}) shiftTextX!: number;
        @Prop({default:0}) shiftTextY!: number;

        public selection: Selection = new Selection(this.id, ElmtType.Fuse)

        @gridState.State
        public allFuses!: Array<Fuse>;

        @gridState.Mutation
        public switchFuse!: (id: number) => void;

        @inspState.Mutation
        public select!: (elmt: Selection) => void;

        @inspState.State
        public  selectedElement!: Selection;

        get isSelected(): boolean {
            return this.selection.equals(this.selectedElement);
        }


        get translate(): string {
            let x = this.location.x;
            let y = this.location.y - 2;
            if(this.displayLeft === "left") {
                x = this.location.x - 38;
            } else if(this.displayLeft === "right"){
                x = this.location.x + 11;
            } else if(this.displayLeft === "top") {
                y = this.location.y - 14;
            } else {
                y = this.location.y + 12;
            }
            return "translate(" +  (x + this.shiftTextX) + " " + (y + this.shiftTextY) + ")";
        }

        get isClosed(): boolean {
            return this.fuse.isClosed();
        }

        get fuse(): Fuse {
            return this.allFuses[this.id];
        }

        get status(): string {
            return this.fuse.status.state;
        }

        public uLoads(): string {
            return prettyStr(this.fuse.uloads);
        }

        public eventHandler(event: MouseEvent): void {
            if(event.altKey) {
                this.switchFuse(this.id);
            } else {
                this.select(this.selection);
            }
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