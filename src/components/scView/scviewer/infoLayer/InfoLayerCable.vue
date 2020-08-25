<template>
    <g :transform=transform :visibility=visibility class="infoBox">
        <rect x="0" y="0" rx="8" ry="8" :width="width" :height="height" fill="white"  stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
        <text transform="translate(5 5)" fill="black">
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="700" x="26" :y=getYText(0)>Cable {{cableId + 1}}</tspan>
            <tspan v-for="ul in uLoads()" :key="ul.id" font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" :y="ul.y">- {{ul.value}} A [{{ul.confidence}}%]</tspan>
        </text>
    </g>
</template>

<script lang="ts">

    import {Component, Vue, Prop} from "vue-property-decorator";
    import {namespace} from "vuex-class";
    import {Cable} from "@/utils/grid";
    import {getYText, layerHeight, ULoadInfo, uLoadsData} from "@/utils/infoLayerUtils";

    const toolbarState = namespace('ToolBarState');
    const gridSCState = namespace('GridSCState');

    @Component
    export default class InfoCableLayer extends Vue {
        @Prop() cableId!: number;
        @Prop() x!: number;
        @Prop() y!: number;

        public readonly width = 75;
        public static readonly nbTextLineInTemplate = 1;

        @toolbarState.State
        public cableLayerVisible!: boolean;

        @gridSCState.State
        public allCables!: Array<Cable>;

        get visibility(): string {
            return this.cableLayerVisible ? "visible" : "hidden";
        }

        get transform(): string {
            const realX = this.x - (this.width/2);
            const realY = this.y - (this.height/2);
            return "translate(" + realX + " " + realY + ")";
        }

        get height(): number {
            const cable: Cable = this.allCables[this.cableId];
            return layerHeight(InfoCableLayer.nbTextLineInTemplate, cable.uLoads.length);
        }

        public getYText(posElmt: number): number {
            return getYText(InfoCableLayer.nbTextLineInTemplate, posElmt);
        }

        public uLoads(): Array<ULoadInfo> {
            const cable: Cable = this.allCables[this.cableId];
            return uLoadsData(cable.uLoads, InfoCableLayer.nbTextLineInTemplate);
        }



    }
</script>

<style scoped lang="scss">
    @import "@/scss/infoLayer.scss";
</style>