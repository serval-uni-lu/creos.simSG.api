<template>
    <div class="svg-viewer">
        <svg id="sc-viewer" viewBox="587 193 222 199">
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
                <SimpleCable :id=0 :line1=line1 :line2=line2 :circle=circle />
                <g id="Substation">
                    <rect x="644" y="194.4" width="99.99999" height="47.644" fill="white"/>
                    <rect x="644" y="194.4" width="99.99999" height="47.644" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <path d="M 644 242.04035 L 744 194.39635 L 644 194.39635 Z" fill="black"/>
                    <path d="M 644 242.04035 L 744 194.39635 L 644 194.39635 Z" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <text transform="translate(747.8 210.722)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="1.4260004" y="11">Substation</tspan>
                    </text>
                </g>
                <Meter :id=0 :xRect=612 :yRect=303 />
                <g id="DeadEnds">
                    <circle cx="694" cy="384" r="7.00001118531325" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                </g>
                <Fuse :id=0 :xRect=689 :yRect=244.79135 />
                <Fuse :id=1 :xRect=689 :yRect=360.3567 />
            </g>
        </svg>
        <CableInfo :id=0 :position=cableInfoBox />
    </div>
</template>

<script>
import Fuse from "@/components/sg-elmts/Fuse.vue"
import Meter from "@/components/sg-elmts/Meter.vue"
import SimpleCable from "@/components/sg-elmts/SimpleCable.vue"
import CableInfo from "@/components/overlayerInfo/CableInfo.vue"

export default {
    components: {Fuse, Meter, SimpleCable, CableInfo},
    data: function() {
        let line1= {x1: 694, y1: 377, x2:694, y2: 242.04};
        let line2= {x1: 694, y1: 291.70832, x2:601.5, y2: 292};
        let circle= {x: 594.5, y: 292};
        let cableInfoBox = {x: line1.x1, y: circle.y};

        return {
            line1: line1,
            line2: line2,
            circle: circle,
            cableInfoBox: cableInfoBox
        }
    },
    created() {
        this.$store.commit('init', 2)
    }
}
</script>

<style lang="scss">

.svg-viewer, .svg-viewer svg {
    height: inherit;
    position: relative;
}

</style>

