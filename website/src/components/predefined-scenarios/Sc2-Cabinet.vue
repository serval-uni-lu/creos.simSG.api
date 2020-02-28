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
            <HiddenComplexCable :id=2 :path=cbl2_path :line=cbl2_line />
            <HiddenComplexCable :id=1 :path=cbl1_path :line=cbl1_line />
            <HiddenSimpleCable :id=0 :line1=c1_line1 :line2=c1_line2 />
            <ComplexCable :id=2 :path=cbl2_path :line=cbl2_line :circle=cbl2_circle  />
            <ComplexCable :id=1 :path=cbl1_path :line=cbl1_line :circle=cbl1_circle  />
            <SimpleCable :id=0 :line1=c1_line1 :line2=c1_line2 :circle=c1_circle />        
            <g id="DE">
                <circle cx="316.7441" cy="385" r="7.00001118531326" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white" stroke="black"/>
                <circle cx="217.82796" cy="385" r="7.00001118531327" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
            </g>
            <Meter :id=0 :xRect=208.2 :yRect=194 />
            <Meter :id=1 :xRect=149 :yRect=341.3 />
            <Meter :id=2 :xRect=345.8 :yRect=341.7 />
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
            <Fuse :id=0 :xRect=268.7441 :yRect=139.77368 />
            <Fuse :id=1 :xRect=268.7441 :yRect=241.65787 />
            <Fuse :id=2 :xRect=243.8201 :yRect=293.38053 :displayLeft="'left'" />
            <Fuse :id=3 :xRect=293.67107 :yRect=293.2013 />
            <Fuse :id=4 :xRect=212.78744 :yRect=360.4025 />
            <Fuse :id=5 :xRect=311.8802 :yRect=360.3309 :displayLeft="'left'"/>
        </g>
    </svg>
</template>



<script>
import Fuse from "@/components/sg-elmts/Fuse.vue"
import Meter from "@/components/sg-elmts/Meter.vue"
import SimpleCable from "@/components/sg-elmts/SimpleCable.vue"
import HiddenSimpleCable from "@/components/sg-elmts/HiddenSimpleCable.vue"
import ComplexCable from "@/components/sg-elmts/ComplexCable.vue"
import HiddenComplexCable from "@/components/sg-elmts/HiddenComplexCable.vue"
import { mapState } from 'vuex'

export default {
     data: function() {
        return {
            c1_line1: {x1: 273.74, y1: 273.42, x2:273.74, y2: 137},
            c1_line2: {x1: 268.74, y1: 182.5, x2:199, y2: 182.5},
            c1_circle: {x: 192, y: 182.5},
            cbl2_path: "M 273.7441 273.4163 L 298.6681 273.4163 L 298.6726 311 L 317.2561 311 L 316.79254 378.00016",
            cbl2_line: {x1: 322.12, y1:331.06, x2:397.8, y2:331},
            cbl2_circle: {onLineX: 317.1173, endX: 404.8, y: 331},
            cbl1_path: "M 273.7441 273.4163 L 248.8201 273.4163 L 248.8201 311 L 217.67497 311 L 217.81348 378",
            cbl1_line: {x1: 212.71, y1:330.134, x2:130.4, y2:331},
            cbl1_circle: {onLineX: 217.71, endX: 123.4, y: 331}
        }
    },
    components: {Fuse, Meter, SimpleCable, ComplexCable, HiddenComplexCable, HiddenSimpleCable},
    created() {
        this.$store.commit('init', 6)
    }
}
</script>

