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
    <HiddenSimpleCable :id=0 :line1=cbl1.line1 :line2=cbl1.line2 />
    <HiddenComplexCable :id=1 :line=cbl2.line :path=cbl2.path />
    <HiddenComplexCable :id=2 :line=cbl3.line :path=cbl3.path />
    <HiddenSimpleCable :id=3 :line1=cbl4.line1 :line2=cbl4.line2 />
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
    <SimpleCable :id=0 :line1=cbl1.line1 :line2=cbl1.line2 :circle=cbl1.circle />
    <ComplexCable :id=1 :line=cbl2.line :path=cbl2.path :circle=cbl2.circle />
    <ComplexCable :id=2 :line=cbl3.line :path=cbl3.path :circle=cbl3.circle />
    <SimpleCable :id=3 :line1=cbl4.line1 :line2=cbl4.line2 :circle=cbl4.circle />
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
    <Meter :id=0 :xRect="825.5" :yRect="541" />
    <Meter :id=1 :xRect="791" :yRect="673" />
    <Meter :id=2 :xRect="1009" :yRect="670" />
    <Meter :id=3 :xRect="956.4251" :yRect="793" />
    <Fuse :id=0 :xRect="906.9337" :yRect="495.3649" />
    <Fuse :id=1 :xRect="907.3112" :yRect="567.6995" />
    <Fuse :id=2 :xRect="884.5" :yRect="619.33994" :displayLeft="'left'" />
    <Fuse :id=3 :xRect="884.5" :yRect="693.7666" :displayLeft="'left'" />
    <Fuse :id=4 :xRect="932.5" :yRect="619.33994" />
    <Fuse :id=5 :xRect="932.5" :yRect="693.7666" />
    <Fuse :id=6 :xRect="909" :yRect="745.2334" :displayLeft="'left'" />
    <Fuse :id=7 :xRect="909" :yRect="807" :displayLeft="'left'" />
    <InfoBoxSimpleCable :id=0 :line1=cbl1.line1 :line2=cbl1.line2 :circle=cbl1.circle />
    <InfoBoxComplexCable :id=1 :line=cbl2.line :path=cbl2.path :circle=cbl2.circle />
    <InfoBoxComplexCable :id=2 :line=cbl3.line :path=cbl3.path :circle=cbl3.circle />
    <InfoBoxSimpleCable :id=3 :line1=cbl4.line1 :line2=cbl4.line2 :circle=cbl4.circle />
    <InfoBoxFuse :id=0 :xRect="906.9337" :yRect="495.3649" />
    <InfoBoxFuse :id=1 :xRect="907.3112" :yRect="567.6995" />
    <InfoBoxFuse :id=2 :xRect="884.5" :yRect="619.33994" />
    <InfoBoxFuse :id=3 :xRect="884.5" :yRect="693.7666" />
    <InfoBoxFuse :id=4 :xRect="932.5" :yRect="619.33994" />
    <InfoBoxFuse :id=5 :xRect="932.5" :yRect="693.7666" />
    <InfoBoxFuse :id=6 :xRect="909" :yRect="745.2334" />
    <InfoBoxFuse :id=7 :xRect="909" :yRect="807" />
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
import InfoBoxFuse from "@/components/infoBox/InfoBoxFuse.vue"
import InfoBoxSimpleCable from "@/components/infoBox/InfoBoxSimpleCable.vue"
import InfoBoxComplexCable from "@/components/infoBox/InfoBoxComplexCable.vue"
import { mapState } from 'vuex'

export default {
    data: function() {
        return {
            cbl1: {
              line1: {x1: 912.45, y1: 598.52, x2: 911.86, y2: 485.60},
              line2: {x1: 915.79, y1: 530, x2: 809.85, y2: 529.85},
              circle: {x: 802.68, y: 529.84}
            },
            cbl2: {
              line: {x1: 884.16, y1: 661.9, x2: 765, y2: 662.44},
              path: "M 912.446 598.5197 L 889.3459 598.5197 L 888.9699 727.439 L 913.446 727.439",
              circle: {onLineX: 889.16, endX: 758, y: 661.88}
            },
            cbl3: {
              line: {x1: 1068.5, y1: 659.16, x2: 941.98, y2: 659.16},
              path: "M 912.446 598.5197 L 936.9761 598.5197 L 936.9761 727.439 L 912.5 727.439",
              circle: {onLineX: 936.98, endX: 1075.5, y: 659.16}
            },
            cbl4: {
              line1: {x1: 913.45, y1: 727.44, x2: 914.24, y2: 822.68},
              line2: {x1: 919.3, y1: 781.59, x2: 1011.73, y2: 781.59},
              circle: {x: 1018.7323, y: 781.59}
            }
        }
        
    },
    components: {
      Fuse, Meter, SimpleCable, ComplexCable,
      HiddenSimpleCable, HiddenComplexCable,
      InfoBoxFuse, InfoBoxSimpleCable, InfoBoxComplexCable
    },
    created() {
        this.$store.commit('init', 8)
    }
}
</script>
