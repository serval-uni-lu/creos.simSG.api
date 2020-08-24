<template>
    <g v-bind:class="[{selected: isSelected}, getClass]" v-on:click="eventHandler()">
        <title>Load: {{uLoads()}}</title>
        <path :d=path stroke-linecap="round" stroke-linejoin="round"/>
        <circle :cx=circle.endX :cy=circle.y r="7" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" v-if="!isHidden"/>
        <circle :cx=circle.onLineX :cy=circle.y r="5" v-if="!isHidden"/>
        <line :x1=line.x1 :y1=line.y1 :x2=line.x2 :y2=line.y2 stroke-linecap="round" stroke-linejoin="round"/>
    </g>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import {CirclesComplexLine, Line} from "@/utils/SvgTypes";
    import {namespace} from "vuex-class";
    import {ElmtType, Selection} from "@/utils/selection";
    import {Cable} from "@/utils/grid";
    import {prettyStr} from "@/utils/uLoadsUtils";

    const inspState = namespace('InspectorState');
    const gridState = namespace('GridSCState');

    @Component
    export default class ComplexCable extends Vue{
        @Prop() id!: number;
        @Prop() path!: string;
        @Prop() circle!: CirclesComplexLine;
        @Prop() line!: Line;
        @Prop({default(){return false}}) isHidden!: boolean;

        @inspState.Mutation
        public select!: (elmt: Selection) => void;

        @inspState.State
        public  selectedElement!: Selection;

        @gridState.State
        public allCables!: Array<Cable>;

        public selection: Selection = new Selection(this.id, ElmtType.Cable);

        get isSelected(): boolean {
            return this.selection.equals(this.selectedElement);
        }

        get getClass(): string {
            return this.isHidden? "cableHidden" : "cable";
        }

        public eventHandler(): void {
            this.select(this.selection);
        }

        public uLoads(): string {
            return prettyStr(this.allCables[this.id].uLoads);
        }

    }
</script>

<style scoped lang="scss">
    @import "@/scss/cable.scss";
</style>