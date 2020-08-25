<template>
    <svg viewBox="115 88 298 305">
        <defs>
            <font-face font-family="Helvetica Neue" font-size="12" panose-1="2 0 5 3 0 0 0 2 0 4" units-per-em="1000" underline-position="-100" underline-thickness="50" slope="0" x-height="517" cap-height="714" ascent="951.9958" descent="-212.99744" font-weight="400">
                <font-face-src>
                    <font-face-name name="HelveticaNeue"/>
                </font-face-src>
            </font-face>
            <font-face font-family="Helvetica Neue" font-size="12" panose-1="2 0 5 3 0 0 0 9 0 4" units-per-em="1000" underline-position="-100" underline-thickness="50" slope="-1e3" x-height="517" cap-height="714" ascent="957.0007" descent="-212.99744" font-style="italic" font-weight="400">
                <font-face-src>
                    <font-face-name name="HelveticaNeue-Italic"/>
                </font-face-src>
            </font-face>
        </defs>
        <g fill="none" stroke-dasharray="none" stroke="none" stroke-opacity="1" fill-opacity="1">
                <CableVue :id=2 :info=c2_info :isSimple=false :isHidden=true />
                <CableVue :id=1 :info=c1_info :isSimple=false :isHidden=true />
                <CableVue :id=0 :info=c0_info :isSimple=true :isHidden=true />
                <CableVue :id=2 :info=c2_info :isSimple=false />
                <CableVue :id=1 :info=c1_info :isSimple=false />
                <CableVue :id=0 :info=c0_info :isSimple=true />
            <g id="DE">
                <circle cx="316.7441" cy="385" r="7.00001118531326" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white" stroke="black"/>
                <circle cx="217.82796" cy="385" r="7.00001118531327" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
            </g>
            <Meter :id=0 :location="locMeter0" />
            <Meter :id=1 :location="locMeter1" />
            <Meter :id=2 :location="locMeter2" />
            <g id="Cabinet">
                <rect x="238.2441" y="257.4163" width="71" height="32" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                <text transform="translate(309.2441 266.2483)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="2.0160011" y="11">Cabinet</tspan>
                </text>
            </g>
            <g id="Substation">
                <rect x="223.7441" y="89.29499" width="99.99999" height="47.644" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                <path d="M 223.7441 136.93534 L 323.7441 89.29134 L 223.7441 89.29134 Z" fill="black"/>
                <path d="M 223.7441 136.93534 L 323.7441 89.29134 L 223.7441 89.29134 Z" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                <text transform="translate(327.5441 105.617)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="1.4260004" y="11">Substation</tspan>
                </text>
            </g>
            <FuseVue :id=0 :xRect=268.7441 :yRect=139.77368 />
            <FuseVue :id=1 :xRect=268.7441 :yRect=241.65787 />
            <FuseVue :id=2 :xRect=243.8201 :yRect=293.38053 :displayLeft="'left'" />
            <FuseVue :id=3 :xRect=293.67107 :yRect=293.2013 />
            <FuseVue :id=4 :xRect=212.78744 :yRect=360.4025 />
            <FuseVue :id=5 :xRect=311.8802 :yRect=360.3309 :displayLeft="'left'"/>
            <InfoLayerFuse :id=0 :location="locFuse0" />
            <InfoLayerFuse :id=1 :location="locFuse1" />
            <InfoLayerFuse :id=2 :location="locFuse2" />
            <InfoLayerFuse :id=3 :location="locFuse3" />
            <InfoLayerFuse :id=4 :location="locFuse4" />
            <InfoLayerFuse :id=5 :location="locFuse5" />
            <InfoLayerCable :cableId=0 :x=c0_info.line1.x1 :y=c0_info.circle.y />
            <InfoLayerCable :cableId=1 :x=c1_info.circle.onLineX :y=c1_info.circle.y />
            <InfoLayerCable :cableId=2 :x=c2_info.circle.onLineX :y=c2_info.circle.y />
        </g>
    </svg>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import {namespace} from "vuex-class";
    import FuseVue from "@/components/scView/scviewer/sg-elements/FuseVue.vue";
    import InfoLayerFuse from "@/components/scView/scviewer/infoLayer/InfoLayerFuse.vue";
    import {Point, SimpleCableInfo, ComplexCableInfo} from "@/utils/SvgTypes";
    import Meter from "@/components/scView/scviewer/sg-elements/Meter.vue";
    import CableVue from "@/components/scView/scviewer/sg-elements/CableVue.vue";
    import InfoLayerCable from "@/components/scView/scviewer/infoLayer/InfoLayerCable.vue";

    const gridState = namespace('GridSCState');
    @Component({
        components: {
            InfoLayerCable, CableVue, Meter, InfoLayerFuse, FuseVue}
    })
    export default class Cabinet extends Vue {
        private locFuse0: Point = {x: 268.7441, y: 139.77368};
        private locFuse1: Point = {x: 268.7441, y: 241.65787};
        private locFuse2: Point = {x: 243.8201, y: 293.38053};
        private locFuse3: Point = {x: 293.67107, y: 293.20138};
        private locFuse4: Point = {x: 212.78744, y: 360.4025};
        private locFuse5: Point = {x: 311.8802, y: 360.3309};

        private locMeter0: Point = {x:208.2, y: 194};
        private locMeter1: Point = {x:149, y: 341.3};
        private locMeter2: Point = {x:345.8, y: 341.7};


        private c0_info: SimpleCableInfo = {
            line1: {x1: 273.74, y1: 273.42, x2:273.74, y2: 137},
            line2: {x1: 268.74, y1: 182.5, x2:199, y2: 182.5},
            circle: {x: 192, y: 182.5}
        };

        private c1_info: ComplexCableInfo = {
            path: "M 273.7441 273.4163 L 248.8201 273.4163 L 248.8201 311 L 217.67497 311 L 217.81348 378",
            circle: {onLineX: 217.71, endX: 123.4, y: 331},
            line: {x1: 212.71, y1:330.134, x2:130.4, y2:331}
        };

        private c2_info: ComplexCableInfo = {
            path: "M 273.7441 273.4163 L 298.6681 273.4163 L 298.6726 311 L 317.2561 311 L 316.79254 378.00016",
            circle: {onLineX: 317.1173, endX: 404.8, y: 331},
            line:{x1: 322.12, y1:331.06, x2:397.8, y2:331}
        };


        @gridState.Mutation
        public init!: (nbFuses: number) => void;

        public created() {
            this.init(6);
        }

    }
</script>

<style scoped>

</style>