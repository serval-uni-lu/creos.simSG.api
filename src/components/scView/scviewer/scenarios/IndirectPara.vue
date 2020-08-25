import {CableType} from "@/utils/SvgTypes";
import {CableType} from "@/utils/SvgTypes";
<template>
    <svg viewBox="113 7 416 373">
        <defs>
            <font-face font-family="Helvetica Neue" font-size="12" panose-1="2 0 5 3 0 0 0 9 0 4" units-per-em="1000" underline-position="-100" underline-thickness="50" slope="-1e3" x-height="517" cap-height="714" ascent="957.0007" descent="-212.99744" font-style="italic" font-weight="400">
                <font-face-src>
                    <font-face-name name="HelveticaNeue-Italic"/>
                </font-face-src>
            </font-face>
            <font-face font-family="Helvetica Neue" font-size="12" panose-1="2 0 5 3 0 0 0 2 0 4" units-per-em="1000" underline-position="-100" underline-thickness="50" slope="0" x-height="517" cap-height="714" ascent="951.9958" descent="-212.99744" font-weight="400">
                <font-face-src>
                    <font-face-name name="HelveticaNeue"/>
                </font-face-src>
            </font-face>
        </defs>
        <g fill="none" stroke-dasharray="none" stroke="none" stroke-opacity="1" fill-opacity="1">
            <CableVue :id=4 :info=cbl5 :isHidden=true />
            <CableVue :id=3 :info=cbl4 :isHidden=true />
            <CableVue :id=2 :info=cbl3 :isHidden=true />
            <CableVue :id=1 :info=cbl2 :isHidden=true />
            <CableVue :id=0 :info=cbl1 :isHidden=true />
            <Meter :id=0 :location=locMeter0 />
            <Meter :id=1 :location=locMeter1 />
            <Meter :id=2 :location=locMeter2 />
            <Meter :id=3 :location=locMeter3 />
            <Meter :id=4 :location=locMeter4 />
            <g id="Substation">
                <rect x="204.5" y="8" width="99.99999" height="47.644" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                <path d="M 204.5 55.644 L 304.5 8 L 204.5 8 Z" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="black"/>
                <text transform="translate(308.3 24.322)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="1.4260004" y="11">Substation</tspan>
                </text>
            </g>
            <g id="Cabinet 2">
                <rect x="334" y="161" width="71" height="32" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                <text transform="translate(352 193)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="2.0160011" y="11">Cabinet</tspan>
                </text>
            </g>
            <g id="Cabinet 1">
                <rect x="218" y="269.5" width="71" height="32" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                <text transform="translate(289 278.332)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="2.0160011" y="11">Cabinet</tspan>
                </text>
            </g>
            <CableVue :id=4 :info=cbl5 />
            <CableVue :id=3 :info=cbl4 />
            <CableVue :id=2 :info=cbl3 />
            <CableVue :id=1 :info=cbl2 />
            <CableVue :id=0 :info=cbl1 />
            <g id="DE">
                <circle cx="253.5" cy="363.62933" r="7.1770752434485" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                <circle cx="520.9223" cy="176.39287" r="7.17707524344844"  stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
            </g>
            <FuseVue :id=9 :location=locFuse9 />
            <FuseVue :id=8 :location=locFuse8 />
            <FuseVue :id=7 :location=locFuse7 />
            <FuseVue :id=6 :location=locFuse6 :displayLeft="'bottom'" />
            <FuseVue :id=5 :location=locFuse5 :displayLeft="'top'" />
            <FuseVue :id=4 :location=locFuse4 :displayLeft="'top'" />
            <FuseVue :id=3 :location=locFuse3 :displayLeft="'top'" />
            <FuseVue :id=2 :location=locFuse2 />
            <FuseVue :id=1 :location=locFuse1 :displayLeft="'left'" />
            <FuseVue :id=0 :location=locFuse0 :displayLeft="'left'" />
            <InfoLayerFuse :id=9 :location=locFuse9 />
            <InfoLayerFuse :id=8 :location=locFuse8 />
            <InfoLayerFuse :id=7 :location=locFuse7 />
            <InfoLayerFuse :id=6 :location=locFuse6 />
            <InfoLayerFuse :id=5 :location=locFuse5 />
            <InfoLayerFuse :id=4 :location=locFuse4 />
            <InfoLayerFuse :id=3 :location=locFuse3 />
            <InfoLayerFuse :id=2 :location=locFuse2 />
            <InfoLayerFuse :id=1 :location=locFuse1 />
            <InfoLayerFuse :id=0 :location=locFuse0 />
            <InfoLayerCable :cableId=4 :x=cbl5.line1.x1 :y=cbl5.circle.y />
            <InfoLayerCable :cableId=3 :x=cbl4.circle.onLineX :y=cbl4.circle.y />
            <InfoLayerCable :cableId=1 :x=cbl2.circle.onLineX :y=cbl2.circle.y />
            <InfoLayerCable :cableId=0 :x=cbl1.circle.onLineX :y=cbl1.circle.y />
            <InfoLayerCable :cableId=2 :x=cbl3.circleX :y=cbl3.line.y />
        </g>
    </svg>
</template>

<script lang="ts">

    import {Component, Vue} from "vue-property-decorator";
    import {namespace} from "vuex-class";
    import {CableType, ComplexCableInfo, OneLineCableInfo, Point, SimpleCableInfo} from "@/utils/SvgTypes";
    import CableVue from "@/components/scView/scviewer/sg-elements/CableVue.vue";
    import Meter from "@/components/scView/scviewer/sg-elements/Meter.vue";
    import FuseVue from "@/components/scView/scviewer/sg-elements/FuseVue.vue";
    import InfoLayerFuse from "@/components/scView/scviewer/infoLayer/InfoLayerFuse.vue";
    import InfoLayerCable from "@/components/scView/scviewer/infoLayer/InfoLayerCable.vue";

    const gridState = namespace('GridSCState');
    @Component({
        components: {InfoLayerCable, InfoLayerFuse, FuseVue, Meter, CableVue}
    })
    export default class IndirectPara extends Vue {
        @gridState.Mutation
        public init!: (nbFuses: number) => void;

        private locFuse9: Point = { x:248.5, y:343 };
        private locFuse8: Point = { x:248.5, y:306 };
        private locFuse7: Point = { x:277, y:255 };
        private locFuse6: Point = { x:320.5, y:183.135 };
        private locFuse5: Point = { x:500, y:171.69337 };
        private locFuse4: Point = { x:408.5, y:171.69337 };
        private locFuse3: Point = { x:320.5, y:161 };
        private locFuse2: Point = { x:277, y:58.61098 };
        private locFuse1: Point = { x:220.27, y:255 };
        private locFuse0: Point = { x:220, y:60 };

        private locMeter4: Point = { x:187, y:336 };
        private locMeter3: Point = { x:334.6, y:230 };
        private locMeter2: Point = { x:445.5, y:187.3 };
        private locMeter1: Point = { x:348.5, y:111 };
        private locMeter0: Point = { x:155.5, y:181 };

        private cbl1: ComplexCableInfo = {
            path: "M 225.43436 55.644 L 225 56 L 225 285.5 L 253.5 285.5",
            line: {x1:127.85 , y1:170.01, x2: 220.27, y2:170.01},
            circle: {onLineX: 225.27, endX: 120.67706, y: 170.01},
            type: CableType.Complex,
        };

        private cbl2: ComplexCableInfo = {
            path: "M 369.5 177 L 369.5 165 L 282 165 L 282 56 L 281.5951 55.644",
            line: {x1:287 , y1:100.17, x2: 400.5, y2:100.17},
            circle: {onLineX: 282, endX: 407.68, y: 100.16},
            type: CableType.Complex,
        };

        private cbl3: OneLineCableInfo = {
            line: {x1: 369.5, x2: 522.45, y:177 },
            circleX: 464,
            type: CableType.OneLine
        };

        private cbl4: ComplexCableInfo = {
            path: "M 253.5 285.5 L 282 285.5 L 282 189 L 369.5 189 L 369.5 177",
            line: {x1:287 , y1:218.97, x2: 377.5, y2:218.97},
            circle: {onLineX: 282, endX: 384.68, y: 218.97},
            type: CableType.Complex,
        };

        private cbl5: SimpleCableInfo = {
            line1: {x1:253.5, y1:285.5, x2:253.5, y2:364.42},
            line2: {x1:169.85, y1:324.96, x2:253, y2:325},
            circle: {x: 162.68, y: 325},
            type: CableType.Simple,
        };












        public created() {
            this.init(10);
        }

    }
</script>

<style scoped>

</style>