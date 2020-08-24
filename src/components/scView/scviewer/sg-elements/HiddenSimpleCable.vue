<template>
    <g class="cableHidden" v-bind:class="{selected: isSelected}" v-on:click="eventHandler()">
        <title>Load: {{uLoads()}}</title>
        <line :x1=line1.x1 :y1=line1.y1 :x2=line1.x2 :y2=line1.y2 stroke-linecap="round" stroke-linejoin="round"/>
        <line :x1=line2.x1 :y1=line2.y1 :x2=line2.x2 :y2=line2.y2 stroke-linecap="round" stroke-linejoin="round"/>
    </g>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from "vue-property-decorator";
    import {Line} from "@/utils/SvgTypes";
    import {namespace} from "vuex-class";
    import {ElmtType, Selection} from "@/utils/selection";
    import {Cable} from "@/utils/grid";
    import {uLoadsStr} from "@/utils/cableVueUtils";

    const inspState = namespace('InspectorState');
    const gridState = namespace('GridSCState');

    @Component
    export default class HiddenSimpleCable extends Vue{
        @Prop() id!: number;
        @Prop() line1!: Line;
        @Prop() line2!: Line;

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
            return uLoadsStr(this.allCables[this.id]);
        }

    }
</script>

<style scoped lang="scss">
    @import "@/scss/cable.scss";
</style>