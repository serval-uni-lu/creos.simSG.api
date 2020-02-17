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
      <g class="hidden">
        <g v-on:click="id=4; showInspector();">
          <title>Load: {{load(4)}}</title>
          <line x1="253.5" y1="285.5" x2="253.5" y2="364.4185" stroke-linecap="round" stroke-linejoin="round"/>
          <line x1="169.85413" y1="324.9552" x2="253" y2="324.90842" stroke-linecap="round" stroke-linejoin="round"/>
        </g>
        <g v-on:click="id=3; showInspector();">
          <title>Load: {{load(3)}}</title>
          <line x1="287" y1="218.96596" x2="377.5" y2="218.96596" stroke-linecap="round" stroke-linejoin="round" />
          <path d="M 253.5 285.5 L 282 285.5 L 282 189 L 369.5 189 L 369.5 177" stroke-linecap="round" stroke-linejoin="round" />
        </g>
        <g v-on:click="id=2; showInspector();">
          <title>Load: {{load(2)}}</title>
          <line x1="369.5" y1="177" x2="522.45184" y2="176.38674" stroke-linecap="round" stroke-linejoin="round"/>
        </g>
        <g v-on:click="id=1; showInspector();">
          <title>Load: {{load(1)}}</title>
          <path d="M 369.5 177 L 369.5 165 L 282 165 L 282 56 L 281.5951 55.644" stroke-linecap="round" stroke-linejoin="round"/>
          <line x1="287" y1="100.1656" x2="400.5" y2="100.1656" stroke-linecap="round" stroke-linejoin="round"/>
        </g>
        <g v-on:click="id=0; showInspector();">
          <title>Load: {{load(0)}}</title>
          <line x1="127.85413" y1="170.01229" x2="220.27" y2="170.01229" stroke-linecap="round" stroke-linejoin="round" />
          <path d="M 225.43436 55.644 L 225 56 L 225 285.5 L 253.5 285.5" stroke-linecap="round" stroke-linejoin="round" />
        </g>
      </g>
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
      <g class="cable" v-bind:class="{selected: isSelected && id===4}" v-on:click="id=4; showInspector();">
          <title>Load: {{load(4)}}</title>
          <line x1="253.5" y1="285.5" x2="253.5" y2="364.4185" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
          <line x1="169.85413" y1="324.9552" x2="253" y2="324.90842" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
          <circle cx="162.67706" cy="324.95926" r="7.17707524344848" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
          <circle cx="253.5" cy="325.5" r="5.00000798950949" fill="black"/>
      </g>
      <g class="cable" v-bind:class="{selected: isSelected && id===3}" v-on:click="id=3; showInspector();">
          <title>Load: {{load(3)}}</title>
          <circle cx="282" cy="218.96596" r="5.00000798950948"/>
          <circle cx="384.67706" cy="218.96596" r="7.17707524344849" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
          <line x1="287" y1="218.96596" x2="377.5" y2="218.96596" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
           <path d="M 253.5 285.5 L 282 285.5 L 282 189 L 369.5 189 L 369.5 177" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
      </g>
      <g class="cable" v-bind:class="{selected: isSelected && id===2}" v-on:click="id=2; showInspector();">
          <title>Load: {{load(2)}}</title>
          <line x1="369.5" y1="177" x2="522.45184" y2="176.38674" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
          <circle cx="464" cy="176.39287" r="5.00000798950948" />
      </g>
      <g class="cable" v-bind:class="{selected: isSelected && id===1}" v-on:click="id=1; showInspector();">
          <title>Load: {{load(1)}}</title>
          <path d="M 369.5 177 L 369.5 165 L 282 165 L 282 56 L 281.5951 55.644" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
          <line x1="287" y1="100.1656" x2="400.5" y2="100.1656" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
          <circle cx="407.67706" cy="100.1656" r="7.17707524344855" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
          <circle cx="282" cy="100.1656" r="5.00000798950949"/>
      </g>
      <g class="cable" v-bind:class="{selected: isSelected && id===0}" v-on:click="id=0; showInspector();">
          <title>Load: {{load(0)}}</title>
          <circle cx="120.67706" cy="170.01229" r="7.17707524344849" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
          <circle cx="225.27" cy="170.01229" r="5.00000798950947"/>
          <line x1="127.85413" y1="170.01229" x2="220.27" y2="170.01229" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
          <path d="M 225.43436 55.644 L 225 56 L 225 285.5 L 253.5 285.5" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
      </g>
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
import { mapState } from 'vuex'

export default {
    data: function() {
          return {
              id: -2
          }
          
    },
    components: {Fuse, Meter},
    created() {
        this.$store.commit('init', 10)
    },
    computed: {
         isSelected: function() {
            return this.selectedElmt.isSameAs(this.id, 'cable')
        },
        ...mapState({
            selectedElmt: state => state.selectedElmt,
            loads: state => state.loads
        })
    },
    methods: {
        load: function(id) {
              var load = this.loads[id];
              return (load === -1)? "To be computed..." : load.toFixed(2) + " A";
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

