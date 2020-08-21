<template>
    <g :transform=gPosition :visibility=showCableLayer class="infoBox">
        <rect x="0" y="0" rx="8" ry="8" width="75" :height="heightLayer()" fill="white"  stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
        <text transform="translate(5 5)" fill="black">
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="700" x="26" y="5">Cable {{id + 1}}</tspan>
            <tspan v-for="ul in uLoads()" :key="ul.id" font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" :y="ul.y">- {{ul.value}} A [{{ul.confidence}}%]</tspan>
        </text>
    </g>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import {CirclesComplexLine} from "@/utils/SvgTypes";
    import {namespace} from "vuex-class";
    import {Cable} from "@/utils/grid";

    const toolbarState = namespace('ToolBarState');
    const gridSCState = namespace('GridSCState');

    @Component
    export default class InfoLayerComplexCable extends Vue {
        @Prop() id!: number;
        @Prop() circle!: CirclesComplexLine;

        @toolbarState.State
        public cableLayerVisible!: boolean;

        @gridSCState.State
        public allCables!: Array<Cable>;

        get xLoadInfo(): number {
            return this.circle.onLineX - 25;
        }

        get yLoadInfo(): number {
            return this.circle.y - 22.5;
        }

        get gPosition(): string {
            return "translate(" + (this.xLoadInfo) + " " + this.yLoadInfo + ")";
        }

        get showCableLayer(): string {
            return this.cableLayerVisible? "visible" : "hidden";
        }

        get cable(): Cable {
            return this.allCables[this.id];
        }

        public uLoads(): Array<object> {
            const uloads = this.cable.uLoads;
            if(uloads.length === 0) {
                return [{id: 0, value: "TBD", confidence: "TBD", y: 15}];
            }

            const result = [];
            for(let ul=0; ul<uloads.length; ul++) {
                result.push({
                    id: ul,
                    value: uloads[ul].prettyLoad(),
                    confidence: uloads[ul].prettyConf(),
                    y: 5 + 10*(ul+1)
                });
            }

            return result;
        }

        public heightLayer(): number {
            return 15 + this.uLoads().length * 10;
        }

    }
</script>

<style scoped lang="scss">
    @import "@/scss/infoLayer.scss";
</style>