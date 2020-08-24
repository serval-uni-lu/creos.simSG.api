<template>
    <g class="cable" v-bind:class="{selected: isSelected}" v-on:click="eventHandler()">
        <title>Load: {{uLoads()}}</title>
        <line :x1=line1.x1 :y1=line1.y1 :x2=line1.x2 :y2=line1.y2 stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <line :x1=line2.x1 :y1=line2.y1 :x2=line2.x2 :y2=line2.y2 stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <circle :cx=line1.x1 :cy=circle.y r="5"/>
        <circle :cx=circle.x :cy=circle.y r="7" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
    </g>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import {Circle, Line} from "@/utils/SvgTypes"
    import {namespace} from "vuex-class";
    import {Selection, ElmtType} from "@/utils/selection";
    import {Cable} from "@/utils/grid";
    import {prettyStr} from "@/utils/uLoadsUtils";

    const inspState = namespace('InspectorState');
    const gridState = namespace('GridSCState');

    @Component
    export default class SimpleCable extends Vue{
        @Prop() id!: number;
        @Prop() line1!: Line;
        @Prop() line2!: Line;
        @Prop() circle!: Circle;

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