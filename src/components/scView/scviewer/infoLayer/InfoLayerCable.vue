<template>
    <g :transform=transform :visibility=visibility class="infoBox">
        <rect x="0" y="0" rx="8" ry="8" :width="width" :height="height" fill="white"  stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
        <text transform="translate(5 5)" fill="black">
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="700" x="26" y="5">Cable {{cableId + 1}}</tspan>
            <tspan v-for="ul in uLoads()" :key="ul.id" font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" :y="ul.y">- {{ul.value}} A [{{ul.confidence}}%]</tspan>
        </text>
    </g>
</template>

<script lang="ts">

    import {Component, Vue, Prop} from "vue-property-decorator";
    import {namespace} from "vuex-class";
    import {Cable} from "../../../../utils/grid";

    const toolbarState = namespace('ToolBarState');
    const gridSCState = namespace('GridSCState');

    @Component
    export default class InfoCableLayer extends Vue {
        @Prop() cableId!: number;
        @Prop() x!: number;
        @Prop() y!: number;

        public width = 75;

        @toolbarState.State
        public cableLayerVisible!: boolean;

        @gridSCState.State
        public allCables!: Array<Cable>;

        get visibility(): string {
            return this.cableLayerVisible ? "visible" : "hidden";
        }

        get realX(): number {
            return this.x - (this.width/2);
        }

        get realY(): number {
            return this.y - (this.height/2);
        }

        get transform(): string {
            return "translate(" + (this.realX) + " " + this.realY + ")";
        }

        get cable(): Cable {
            return this.allCables[this.cableId];
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

        get height(): number {
            return 15 + this.uLoads().length * 10;
        }

    }
</script>

<style scoped lang="scss">
    @import "@/scss/infoLayer.scss";
</style>