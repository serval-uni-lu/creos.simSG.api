<template>
    <g class="cableHidden" v-bind:class="{selected: isSelected}" v-on:click="eventHandler()">
        <title>Load: {{uLoads()}}</title>
        <title>Load</title>
        <path :d=path stroke-linecap="round" stroke-linejoin="round"/>
        <line :x1=line.x1 :y1=line.y1 :x2=line.x2 :y2=line.y2 stroke-linecap="round" stroke-linejoin="round"/>
    </g>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from "vue-property-decorator";
    import {Line} from "@/utils/SvgTypes";
    import {namespace} from "vuex-class";
    import {ElmtType, Selection} from "@/utils/selection";
    import {Cable} from "@/utils/grid";
    import {prettyStr} from "@/utils/uLoadsUtils";

    const inspState = namespace('InspectorState');
    const gridState = namespace('GridSCState');

    @Component
    export default class HiddenComplexCable extends Vue {
        @Prop() id!: number;
        @Prop() path!: string;
        @Prop() line!: Line;

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