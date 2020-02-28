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
            <HiddenComplexCable :id=0 :path=cbl1.path :line=cbl1.line />
            <HiddenComplexCable :id=1 :path=cbl2.path :line=cbl2.line />
            <HiddenSimpleCable :id=0 :line1=cbl3.line1 :line2=cbl3.line2 />
            <g id="Cabinet">
                <rect x="876.8251" y="582.5197" width="71" height="32" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                <text transform="translate(947.8251 591.3517)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="2.0160011" y="11">Cabinet</tspan>
                </text>
            </g>
            <ComplexCable :id=0 :path=cbl1.path :line=cbl1.line :circle=cbl1.circle />
            <ComplexCable :id=1 :path=cbl2.path :line=cbl2.line :circle=cbl2.circle />
            <SimpleCable :id=0 :line1=cbl3.line1 :line2=cbl3.line2 :circle=cbl3.circle />
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
            <Meter :id=0 :xRect=806 :yRect=543 />
            <Meter :id=1 :xRect=985 :yRect=543 />
            <Meter :id=2 :xRect=945 :yRect=672 />
            <Fuse :id=0 :xRect=886.5825 :yRect=490.45385 :displayLeft="'left'" />
            <Fuse :id=1 :xRect=886.5825 :yRect=567.05556 :displayLeft="'left'" />
            <Fuse :id=2 :xRect=926.8821 :yRect=490.34056 />
            <Fuse :id=3 :xRect=926.8821 :yRect=567.2795 />
            <Fuse :id=4 :xRect=906.7323 :yRect=619.0939 />
            <Fuse :id=5 :xRect=906.7323 :yRect=691.7418 :displayLeft="'left'" />
        </g>
    </svg>
</template>

<script>
import Fuse from "@/components/sg-elmts/Fuse.vue"
import Meter from "@/components/sg-elmts/Meter.vue"
import ComplexCable from "@/components/sg-elmts/ComplexCable.vue"
import HiddenComplexCable from "@/components/sg-elmts/HiddenComplexCable.vue"
import SimpleCable from "@/components/sg-elmts/SimpleCable.vue"
import HiddenSimpleCable from "@/components/sg-elmts/HiddenSimpleCable.vue"
import { mapState } from 'vuex'

export default {
    data: function() {
        return {
            cbl1: {
                path: "M 891.5825 485.6004 L 891.5825 598.5197 L 911.7323 598.5197",
                line: {x1: 891.58, y1: 532, x2: 792, y2: 531.53},
                circle: {y: 531.53, onLineX: 891.58, endX: 785}
            },
            cbl2: {
                path: "M 931.8821 485.6004 L 931.8821 598.5197 L 911.7323 598.5197",
                line: {x1: 1020.5, y1: 531.5, x2: 932, y2: 531.5},
                circle: {y: 531.87, onLineX: 931.88, endX: 1027.5}
            },
            cbl3: {
                line1: {x1: 911.73, y1: 706.5, x2: 911.73, y2: 598.52},
                line2: {x1: 1001.12, y1: 660.51, x2: 912.62, y2: 660.51},
                circle: {x: 1008.12, y: 660.87}
            }
        }
        
    },
    components: {Fuse, Meter, ComplexCable, HiddenComplexCable, SimpleCable, HiddenSimpleCable},
    created() {
        this.$store.commit('init', 6)
    }
}
</script>
