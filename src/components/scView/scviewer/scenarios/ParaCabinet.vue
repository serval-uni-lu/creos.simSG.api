<template>
    <svg viewBox="750 437 333 401">
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
            <CableVue :id=0 :info=cbl1 :isSimple=true :isHidden=true />
            <CableVue :id=1 :info=cbl2 :isSimple=false :isHidden=true />
            <CableVue :id=2 :info=cbl3 :isSimple=false :isHidden=true />
            <CableVue :id=3 :info=cbl4 :isSimple=true :isHidden=true />
            <g id="Cabinet 2">
                <rect x="876.946" y="708.5" width="71" height="32" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                <text transform="translate(947.8251 717.332)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="2.0160011" y="11">Cabinet</tspan>
                </text>
            </g>
            <g id="Cabinet 1">
                <rect x="876.946" y="582.5197" width="71" height="32" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                <text transform="translate(947.825 591.3517)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="2.0160011" y="11">Cabinet</tspan>
                </text>
            </g>
            <CableVue :id=0 :info=cbl1 :isSimple=true />
            <CableVue :id=1 :info=cbl2 :isSimple=false />
            <CableVue :id=2 :info=cbl3 :isSimple=false />
            <CableVue :id=3 :info=cbl4 :isSimple=true />
            <g id="DE">
                <circle cx="914.2961" cy="829.6859" r="7.00001118531319" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
            </g>
            <g id="Substation">
                <rect x="861.7323" y="437.9564" width="99.99999" height="47.644" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                <path d="M 861.7323 485.6004 L 961.7323 437.9564 L 861.7323 437.9564 Z" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="black"/>
                <text transform="translate(965.5323 454.2784)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="1.4260004" y="11">Substation</tspan>
                </text>
            </g>
            <Meter :id=0 :location=locMeter0 />
            <Meter :id=1 :location=locMeter1 />
            <Meter :id=2 :location=locMeter2 />
            <Meter :id=3 :location=locMeter3 />
            <FuseVue :id=0 :location=locFuse0 />
            <FuseVue :id=1 :location=locFuse1 />
            <FuseVue :id=2 :location=locFuse2 :displayLeft="'left'" />
            <FuseVue :id=3 :location=locFuse3 :displayLeft="'left'" />
            <FuseVue :id=4 :location=locFuse4 />
            <FuseVue :id=5 :location=locFuse5 />
            <FuseVue :id=6 :location=locFuse6 :displayLeft="'left'" />
            <FuseVue :id=7 :location=locFuse7 :displayLeft="'left'" />
            <InfoLayerCable :cableId=0 :x=cbl1.line1.x1 :y=cbl1.circle.y />
            <InfoLayerCable :cableId=1 :x=cbl2.circle.onLineX :y=cbl2.circle.y />
            <InfoLayerCable :cableId=2 :x=cbl3.circle.onLineX :y=cbl3.circle.y />
            <InfoLayerCable :cableId=3 :x=cbl4.line1.x1 :y=cbl4.circle.y />
            <InfoLayerFuse :id=0 :location=locFuse0 />
            <InfoLayerFuse :id=1 :location=locFuse1 />
            <InfoLayerFuse :id=2 :location=locFuse2 />
            <InfoLayerFuse :id=3 :location=locFuse3 />
            <InfoLayerFuse :id=4 :location=locFuse4 />
            <InfoLayerFuse :id=5 :location=locFuse5 />
            <InfoLayerFuse :id=6 :location=locFuse6 />
            <InfoLayerFuse :id=7 :location=locFuse7 />
        </g>
    </svg>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import {namespace} from "vuex-class";
    import {ComplexCableInfo, Point, SimpleCableInfo} from "@/utils/SvgTypes";
    import CableVue from "@/components/scView/scviewer/sg-elements/CableVue.vue";
    import FuseVue from "@/components/scView/scviewer/sg-elements/FuseVue.vue";
    import Meter from "@/components/scView/scviewer/sg-elements/Meter.vue";
    import InfoLayerFuse from "@/components/scView/scviewer/infoLayer/InfoLayerFuse.vue";
    import InfoLayerCable from "@/components/scView/scviewer/infoLayer/InfoLayerCable.vue";

    const gridState = namespace('GridSCState');
    @Component({
        components: {InfoLayerCable, InfoLayerFuse, FuseVue, CableVue, Meter}
    })
    export default class ParaCabinet extends Vue{
        @gridState.Mutation
        public init!: (nbFuses: number) => void;

        private locFuse0: Point = {x:906.9337, y:495.3649};
        private locFuse1: Point = {x:907.3112, y:567.6995};
        private locFuse2: Point = {x:884.5, y:619.33994  };
        private locFuse3: Point = {x:884.5, y:693.7666  };
        private locFuse4: Point = {x:932.5, y:619.33994 };
        private locFuse5: Point = {x:932.5, y:693.7666 };
        private locFuse6: Point = {x:909, y:745.2334  };
        private locFuse7: Point = {x:909, y:807 };

        private locMeter0: Point = {x: 825.5, y: 541 };
        private locMeter1: Point = {x: 791, y: 673 };
        private locMeter2: Point = {x: 1009, y: 670 };
        private locMeter3: Point = {x: 956.4251, y: 793 };

        private cbl1: SimpleCableInfo = {
            line1: {x1: 912.45, y1: 598.52, x2: 911.86, y2: 485.60},
            line2: {x1: 915.79, y1: 530, x2: 809.85, y2: 529.85},
            circle: {x: 802.68, y: 529.84}
        };

        private cbl2: ComplexCableInfo = {
            line: {x1: 884.16, y1: 661.9, x2: 765, y2: 662.44},
            path: "M 912.446 598.5197 L 889.3459 598.5197 L 888.9699 727.439 L 913.446 727.439",
            circle: {onLineX: 889.16, endX: 758, y: 661.88}
        };

        private cbl3: ComplexCableInfo = {
            line: {x1: 1068.5, y1: 659.16, x2: 941.98, y2: 659.16},
            path: "M 912.446 598.5197 L 936.9761 598.5197 L 936.9761 727.439 L 912.5 727.439",
            circle: {onLineX: 936.98, endX: 1075.5, y: 659.16}
        };

        private cbl4: SimpleCableInfo = {
            line1: {x1: 913.45, y1: 727.44, x2: 914.24, y2: 822.68},
            line2: {x1: 919.3, y1: 781.59, x2: 1011.73, y2: 781.59},
            circle: {x: 1018.7323, y: 781.59}
        };


        public created() {
            this.init(8);
        }

    }
</script>

<style scoped>

</style>