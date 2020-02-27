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
      <HiddenSimpleCable :id=4 :line1=cbl5.line1 :line2=cbl5.line2 />
      <HiddenComplexCable :id=3 :path=cbl4.path :line=cbl4.line />
      <HiddenComplexCable :id=1 :path=cbl2.path :line=cbl2.line />
      <HiddenComplexCable :id=0 :path=cbl1.path :line=cbl1.line />
      <Meter :id=4 :xRect="187" :yRect="336" />
      <Meter :id=3 :xRect="334.6" :yRect="230" />
      <Meter :id=2 :xRect="445.5" :yRect="187.3" />
      <Meter :id=1 :xRect="348.5" :yRect="111" />
      <Meter :id=0 :xRect="155.5" :yRect="181" />
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
      <SimpleCable :id=4 :line1=cbl5.line1 :line2=cbl5.line2 :circle=cbl5.circle />
      <ComplexCable :id=3 :path=cbl4.path :line=cbl4.line :circle=cbl4.circle />
      <OneLineCable :id=2 :line=cbl3.line :circle=cbl3.circle />
      <ComplexCable :id=1 :path=cbl2.path :line=cbl2.line :circle=cbl2.circle />
      <ComplexCable :id=0 :path=cbl1.path :line=cbl1.line :circle=cbl1.circle />

      <!-- <g class="cable" v-bind:class="{selected: isSelected && id===2}" v-on:click="id=2; showInspector();">
          <title>Load: {{load(2)}}</title>
          <line x1="369.5" y1="177" x2="522.45184" y2="176.38674" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
          <circle cx="464" cy="176.39287" r="5.00000798950948" />
      </g> -->
      <g id="DE">
        <circle cx="253.5" cy="363.62933" r="7.1770752434485" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
        <circle cx="520.9223" cy="176.39287" r="7.17707524344844"  stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
      </g>
      <Fuse :id=9 :xRect="248.5" :yRect="343" />
      <Fuse :id=8 :xRect="248.5" :yRect="306" />
      <Fuse :id=7 :xRect="277" :yRect="255" />
      <Fuse :id=6 :xRect="320.5" :yRect="183.135" :displayLeft="'bottom'" :shiftTextX="-10" />
      <Fuse :id=5 :xRect="500" :yRect="171.69337" :displayLeft="'top'" :shiftTextX="-10" :shiftTextY="-2"/>
      <Fuse :id=4 :xRect="408.5" :yRect="171.69337" :displayLeft="'top'"/>
      <Fuse :id=3 :xRect="320.5" :yRect="161" :displayLeft="'top'" :shiftTextX="-10" />
      <Fuse :id=2 :xRect="277" :yRect="58.61098" />
      <Fuse :id=1 :xRect="220.27" :yRect="255" :displayLeft="'left'" />
      <Fuse :id=0 :xRect="220" :yRect="60" :displayLeft="'left'" />
      
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
import OneLineCable from "@/components/sg-elmts/OneLineCable.vue"

import { mapState } from 'vuex'

export default {
    data: function() {
          return {
              id: -2,
              cbl1: {
                path: "M 225.43436 55.644 L 225 56 L 225 285.5 L 253.5 285.5",
                line: {x1:127.85 , y1:170.01, x2: 220.27, y2:170.01},
                circle: {onLineX: 225.27, endX: 120.67706, y: 170.01}
              },
              cbl2: {
                path: "M 369.5 177 L 369.5 165 L 282 165 L 282 56 L 281.5951 55.644",
                line: {x1:287 , y1:100.17, x2: 400.5, y2:100.17},
                circle: {onLineX: 282, endX: 407.68, y: 100.16}
              },
              cbl3: {
                line: {x1: 369.5, x2: 522.45, y:177 },
                circle: {x: 464}
              },
              cbl4: {
                path: "M 253.5 285.5 L 282 285.5 L 282 189 L 369.5 189 L 369.5 177",
                line: {x1:287 , y1:218.97, x2: 377.5, y2:218.97},
                circle: {onLineX: 282, endX: 384.68, y: 218.97}
              },
              cbl5: {
                line1: {x1:253.5, y1:285.5, x2:253.5, y2:364.42},
                line2: {x1:169.85, y1:324.96, x2:253, y2:325},
                circle: {x: 162.68, y: 325}
              },

          }
          
    },
    components: {Fuse, Meter, SimpleCable, HiddenSimpleCable, ComplexCable, HiddenComplexCable, OneLineCable},
    created() {
        this.$store.commit('init', 10)
    },
    computed: {
         isSelected: function() {
            return this.selectedElmt.isSameAs(this.id, 'cable')
        },
        ...mapState({
            selectedElmt: state => state.selectedElmt,
            loads: state => state.cableLoads
        })
    },
    methods: {
        load: function(id) {
              var load = this.loads[id];
              return (load === undefined ||  load === -1)? "To be computed..." : load.toFixed(2) + " A";
        },
        showInspector: function() {
            let info = {
                elemtId: this.id,
                elemtType: "cable"
            }
            this.$store.commit('showInspector', info)
        }
    }
}
</script>


<style lang="scss" scoped>
@import "@/scss/cable.scss";
</style>