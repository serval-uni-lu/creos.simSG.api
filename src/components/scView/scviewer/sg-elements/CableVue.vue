import {CableType} from "@/utils/SvgTypes";
import {CableType} from "@/utils/SvgTypes";
<template>
    <g v-bind:class="[{selected: isSelected}, getClass]" v-on:click="eventHandler()">
        <title>Load: {{uLoads()}}</title>
        <g v-if="isSimple">
            <line :x1=infoSimple.line1.x1 :y1=infoSimple.line1.y1 :x2=infoSimple.line1.x2 :y2=infoSimple.line1.y2 stroke-linecap="round" stroke-linejoin="round"/>
            <line :x1=infoSimple.line2.x1 :y1=infoSimple.line2.y1 :x2=infoSimple.line2.x2 :y2=infoSimple.line2.y2 stroke-linecap="round" stroke-linejoin="round"/>
            <g v-if="!isHidden">
                <circle :cx=infoSimple.line1.x1 :cy=infoSimple.circle.y r="5"/>
                <circle :cx=infoSimple.circle.x :cy=infoSimple.circle.y r="7" stroke-linecap="round" stroke-linejoin="round"/>
            </g>
        </g>
        <g v-else-if="isComplex">
            <path :d=infoComplex.path stroke-linecap="round" stroke-linejoin="round"/>
            <line :x1=infoComplex.line.x1 :y1=infoComplex.line.y1 :x2=infoComplex.line.x2 :y2=infoComplex.line.y2 stroke-linecap="round" stroke-linejoin="round"/>
            <g v-if="!isHidden">
                <circle :cx=infoComplex.circle.endX :cy=infoComplex.circle.y r="7" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" v-if="!isHidden"/>
                <circle :cx=infoComplex.circle.onLineX :cy=infoComplex.circle.y r="5" v-if="!isHidden"/>
            </g>
        </g>
        <g v-else>
            <line :x1=infoOneLine.line.x1 :y1=infoOneLine.line.y :x2=infoOneLine.line.x2 :y2=infoOneLine.line.y stroke-linecap="round" stroke-linejoin="round"/>
            <circle :cx=infoOneLine.circleX :cy=infoOneLine.line.y r="5" />
        </g>
    </g>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import {CableInfo, CableType, ComplexCableInfo, OneLineCableInfo, SimpleCableInfo} from "@/utils/SvgTypes"
    import {namespace} from "vuex-class";
    import {ElmtType, Selection} from "@/utils/selection";
    import {Cable} from "@/utils/grid";
    import {prettyStr} from "@/utils/uLoadsUtils";

    const inspState = namespace('InspectorState');
    const gridState = namespace('GridSCState');

    @Component
    export default class SimpleCable extends Vue{
        @Prop() id!: number;
        @Prop() info!: CableInfo;
        @Prop({default() {return  false}}) isHidden!: boolean;

        @inspState.Mutation
        public select!: (elmt: Selection) => void;

        @inspState.State
        public  selectedElement!: Selection;

        @gridState.State
        public allCables!: Array<Cable>;

        public selection: Selection = new Selection(this.id, ElmtType.Cable);

        get isSimple(): boolean {
            return this.info.type === CableType.Simple;
        }

        get isComplex(): boolean {
            return this.info.type === CableType.Complex;
        }

        get isSelected(): boolean {
            return this.selection.equals(this.selectedElement);
        }

        get infoSimple(): SimpleCableInfo {
            return this.info as SimpleCableInfo;
        }

        get infoComplex(): ComplexCableInfo {
            return this.info as ComplexCableInfo;
        }

        get infoOneLine(): OneLineCableInfo {
            return this.info as OneLineCableInfo;
        }

        get getClass(): string {
            return (this.isHidden)? "cableHidden" : "cable";
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