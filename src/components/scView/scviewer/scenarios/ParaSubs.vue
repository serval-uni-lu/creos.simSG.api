import {CableType} from "@/utils/SvgTypes";
import {CableType} from "@/utils/SvgTypes";
import {CableType} from "@/utils/SvgTypes";
<template>
    <svg viewBox="777 437 258 284" >
        <defs>
            <font-face font-family="Helvetica Neue" font-size="12" panose-1="2 0 5 3 0 0 0 2 0 4" units-per-em="1000" underline-position="-100" underline-thickness="50" slope="0" x-height="517" cap-height="714" ascent="951.9958" descent="-212.99744" font-weight="400">
                <font-face-src>
                    <font-face-name name="HelveticaNeue"/>
                </font-face-src>
            </font-face>
            <font-face font-family="Helvetica Neue" font-size="16" panose-1="2 0 8 3 0 0 0 9 0 4" units-per-em="1000" underline-position="-100" underline-thickness="50" slope="0" x-height="517" cap-height="714" ascent="975.0061" descent="-216.99524" font-weight="700">
                <font-face-src>
                    <font-face-name name="HelveticaNeue-Bold"/>
                </font-face-src>
            </font-face>
            <font-face font-family="Helvetica Neue" font-size="12" panose-1="2 0 5 3 0 0 0 9 0 4" units-per-em="1000" underline-position="-100" underline-thickness="50" slope="-1e3" x-height="517" cap-height="714" ascent="957.0007" descent="-212.99744" font-style="italic" font-weight="400">
                <font-face-src>
                    <font-face-name name="HelveticaNeue-Italic"/>
                </font-face-src>
            </font-face>
        </defs>
        <g id="Canvas_5" fill="none" stroke-dasharray="none" stroke="none" stroke-opacity="1" fill-opacity="1">
            <CableVue :id=0 :info=c0_info :isSimple=false :isHidden=true />
            <CableVue :id=1 :info=c1_info :isSimple=false :isHidden=true />
            <CableVue :id=2 :info=c2_info :isSimple=true :isHidden=true />
            <g id="Cabinet">
                <rect x="876.8251" y="582.5197" width="71" height="32" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                <text transform="translate(947.8251 591.3517)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="2.0160011" y="11">Cabinet</tspan>
                </text>
            </g>
            <g id="DE">
                <circle cx="911.7323" cy="713.5" r="7.00001118531318" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
            </g>
            <g id="Substation">
                <rect x="861.7323" y="437.9564" width="99.99999" height="47.644" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                <path d="M 861.7323 485.6004 L 961.7323 437.9564 L 861.7323 437.9564 Z" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="black"/>
                <text transform="translate(965.5323 454.2784)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="1.4260004" y="11">Substation</tspan>
                </text>
            </g>
            <CableVue :id=0 :info=c0_info :isSimple=false />
            <CableVue :id=1 :info=c1_info :isSimple=false />
            <CableVue :id=2 :info=c2_info :isSimple=true />
            <Meter :id=0 :location=locMeter0 />
            <Meter :id=1 :location=locMeter1 />
            <Meter :id=2 :location=locMeter2 />
            <FuseVue :id=0 :location=locFuse0 :displayLeft="'left'" />
            <FuseVue :id=1 :location=locFuse1 :displayLeft="'left'" />
            <FuseVue :id=2 :location=locFuse2 />
            <FuseVue :id=3 :location=locFuse3 />
            <FuseVue :id=4 :location=locFuse4 />
            <FuseVue :id=5 :location=locFuse5 :displayLeft="'left'" />
            <InfoLayerFuse :id=0 :location=locFuse0 />
            <InfoLayerFuse :id=1 :location=locFuse1 />
            <InfoLayerFuse :id=2 :location=locFuse2 />
            <InfoLayerFuse :id=3 :location=locFuse3 />
            <InfoLayerFuse :id=4 :location=locFuse4 />
            <InfoLayerFuse :id=5 :location=locFuse5 />
            <InfoLayerCable :cableId=0 :x=c0_info.circle.onLineX :y=c0_info.circle.y />
            <InfoLayerCable :cableId=1 :x=c1_info.circle.onLineX :y=c1_info.circle.y />
            <InfoLayerCable :cableId=2 :x=c2_info.line1.x1 :y=c2_info.circle.y />
        </g>
    </svg>
</template>

<script lang="ts">

    import {Component, Vue} from "vue-property-decorator";
    import InfoLayerCable from "../infoLayer/InfoLayerCable.vue";
    import CableVue from "../sg-elements/CableVue.vue";
    import Meter from "../sg-elements/Meter.vue";
    import InfoLayerFuse from "../infoLayer/InfoLayerFuse.vue";
    import FuseVue from "../sg-elements/FuseVue.vue";
    import {namespace} from "vuex-class";
    import {CableType, ComplexCableInfo, Point, SimpleCableInfo} from "@/utils/SvgTypes";

    const gridState = namespace('GridSCState');

    @Component({
        components: {InfoLayerCable, CableVue, Meter, InfoLayerFuse, FuseVue}
    })
    export default class ParaSubs extends Vue {
        @gridState.Mutation
        public init!: (nbFuses: number) => void;

        private locFuse0: Point = {x: 886.5825, y: 490.45385};
        private locFuse1: Point = {x: 886.5825, y: 567.05556};
        private locFuse2: Point = {x: 926.8821, y: 490.34056};
        private locFuse3: Point = {x: 926.8821, y: 567.2795};
        private locFuse4: Point = {x: 906.7323, y: 619.0939};
        private locFuse5: Point = {x: 906.7323, y: 691.7418};

        private locMeter0: Point = {x: 806, y: 543};
        private locMeter1: Point = {x: 985, y: 543};
        private locMeter2: Point = {x: 945, y: 672};

        private c0_info: ComplexCableInfo = {
            path: "M 891.5825 485.6004 L 891.5825 598.5197 L 911.7323 598.5197",
            line: {x1: 891.58, y1: 532, x2: 792, y2: 531.53},
            circle: {y: 531.53, onLineX: 891.58, endX: 785},
            type: CableType.Complex
        };

        private c1_info: ComplexCableInfo = {
            path: "M 931.8821 485.6004 L 931.8821 598.5197 L 911.7323 598.5197",
            line: {x1: 1020.5, y1: 531.5, x2: 932, y2: 531.5},
            circle: {y: 531.87, onLineX: 931.88, endX: 1027.5},
            type: CableType.Complex
        };

        private c2_info: SimpleCableInfo = {
            line1: {x1: 911.73, y1: 706.5, x2: 911.73, y2: 598.52},
            line2: {x1: 1001.12, y1: 660.51, x2: 912.62, y2: 660.51},
            circle: {x: 1008.12, y: 660.87},
            type: CableType.Simple
        };

        public created() {
            this.init(6);
        }
    }

</script>