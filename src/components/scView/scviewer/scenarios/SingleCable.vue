<template>
    <svg viewBox="587 193 222 199">
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
        <g id="SC1-SingleCable" fill="none" stroke-dasharray="none" stroke="none" stroke-opacity="1" fill-opacity="1">
            <CableVue :id=0 :info=cbl_info :isHidden=true :isSimple=true />
            <CableVue :id=0 :info=cbl_info :isSimple=true />
            <g id="Substation">
                <rect x="644" y="194.4" width="99.99999" height="47.644" fill="white"/>
                <rect x="644" y="194.4" width="99.99999" height="47.644" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                <path d="M 644 242.04035 L 744 194.39635 L 644 194.39635 Z" fill="black"/>
                <path d="M 644 242.04035 L 744 194.39635 L 644 194.39635 Z" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                <text transform="translate(747.8 210.722)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="1.4260004" y="11">Substation</tspan>
                </text>
            </g>
            <Meter :id=0 :location=meterLoc />
            <g id="DeadEnds">
                <circle cx="694" cy="384" r="7.00001118531325" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
            </g>
            <FuseVue :id=0 :xRect=689 :yRect=244.79135 />
            <FuseVue :id=1 :xRect=689 :yRect=360.3567 />
            <InfoLayerCable :cableId=0 :x=cbl_info.line1.x1 :y=cbl_info.circle.y />
            <InfoLayerFuse :id=0 :location="infoFuse1" />
            <InfoLayerFuse :id=1 :location="infoFuse2" />
        </g>
    </svg>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import FuseVue from "@/components/scView/scviewer/sg-elements/FuseVue.vue";
    import CableVue from "@/components/scView/scviewer/sg-elements/CableVue.vue";
    import {Point, SimpleCableInfo} from "@/utils/SvgTypes"
    import Meter from "@/components/scView/scviewer/sg-elements/Meter.vue";
    import InfoLayerCable from "@/components/scView/scviewer/infoLayer/InfoLayerCable.vue";
    import InfoLayerFuse from "@/components/scView/scviewer/infoLayer/InfoLayerFuse.vue";
    import {namespace} from "vuex-class";

    const gridState = namespace('GridSCState');

    @Component({
        components: {InfoLayerFuse, InfoLayerCable, Meter, FuseVue, CableVue}
    })
    export default class SingleCable extends Vue{


        private cbl_info: SimpleCableInfo = {
            line1: {x1: 694, y1: 377, x2:694, y2: 242.04},
            line2: {x1: 694, y1: 292, x2:601.5, y2: 292},
            circle: {x: 594.5, y: 292}
        };

        private meterLoc: Point = {x: 612, y: 303};

        private infoFuse1: Point = {x: 689, y: 244.79135};
        private infoFuse2: Point = {x: 689, y: 360.3567};

        @gridState.Mutation
        public init!: (nbFuses: number) => void;

        public created() {
            this.init(2);
        }

    }
</script>

<style scoped>

</style>