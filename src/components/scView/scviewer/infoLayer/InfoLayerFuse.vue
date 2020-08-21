<template>
    <g :transform=gPosition :visibility=showFuseLayer class="infoBox">
        <rect x="0" y="0" rx="8" ry="8" width="100" :height="heightLayer()" fill="white"  stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
        <text transform="translate(5 5)" fill="black">
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="700" x="26" y="5">Fuse {{id + 1}}</tspan>
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" y="15">Status: {{status}} [{{confLevel}}%]</tspan>
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" y="25">Load:</tspan>
            <tspan v-for="ul in uLoads()" :key="ul.id" font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" :y="ul.y">- {{ul.value}} A [{{ul.confidence}}%]</tspan>
        </text>
    </g>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from "vue-property-decorator";
    import {Point} from "@/utils/SvgTypes";
    import {namespace} from "vuex-class";
    import {Fuse} from "@/utils/grid";


    const toolbarState = namespace('ToolBarState');
    const gridState = namespace('GridSCState');

    @Component
    export default class InfoLayerFuse extends Vue{
        @Prop() id!: number;
        @Prop() location!: Point;

        @toolbarState.State
        public fuseLayerVisible!: boolean;

        @gridState.State
        public allFuses!: Array<Fuse>;

        get gPosition(): string {
            return "translate(" + (this.location.x - 37.5) + " " + (this.location.y - this.heightLayer()/2) + ")";
        }

        get showFuseLayer(): string {
            return (this.fuseLayerVisible)? "visible": "hidden";
        }

        get fuse(): Fuse {
            return this.allFuses[this.id];
        }

        get status(): string {
            return this.fuse.status.state;
        }

        get confLevel(): string {
            return this.fuse.status.prettyConf;
        }

        public uLoads(): Array<object> {
            const uloads = this.fuse.uloads;
            if(uloads.length === 0) {
                return [{id:0, value: "TBD", confidence: "TBD", y: 35}];
            }

            const result = [];
            for(let ul=0; ul<uloads.length; ul++) {
                result.push({
                    id: ul,
                    value: uloads[ul].prettyLoad(),
                    confidence: uloads[ul].prettyConf(),
                    y: 25 + 10*(ul+1)
                })
            }

            return result;
        }

        public heightLayer(): number {
            return 15 + (this.uLoads().length + 2) * 10;
        }

    }
</script>

<style scoped lang="scss">
@import "@/scss/infoLayer.scss";
</style>