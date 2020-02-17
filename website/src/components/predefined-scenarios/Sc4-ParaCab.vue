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
    <g class="hidden">
      <g v-on:click="id=3; showInspector();">
        <title>Load: {{load(3)}}</title>
        <line x1="913.446" y1="727.439" x2="914.2379" y2="822.6861" stroke-linecap="round" stroke-linejoin="round"/>
        <line x1="919.2961" y1="781.5929" x2="1011.7323" y2="781.5929" stroke-linecap="round" stroke-linejoin="round"/>
      </g>
      <g v-on:click="id=2; showInspector();">
        <title>Load: {{load(2)}}</title>
        <path d="M 912.446 598.5197 L 936.9761 598.5197 L 936.9761 727.439 L 912.5 727.439" stroke-linecap="round" stroke-linejoin="round"/>
        <line x1="1068.5" y1="659.1596" x2="941.9761" y2="659.1596" stroke-linecap="round" stroke-linejoin="round"/>
      </g>
      <g v-on:click="id=1; showInspector();">
        <title>Load: {{load(1)}}</title>
        <path d="M 912.446 598.5197 L 889.3459 598.5197 L 888.9699 727.439 L 913.446 727.439" stroke-linecap="round" stroke-linejoin="round"/>
        <line x1="884.1612" y1="661.89924" x2="764.9999" y2="662.4351" stroke-linecap="round" stroke-linejoin="round"/>
      </g>
      <g v-on:click="id=0; showInspector();">
        <title>Load: {{load(0)}}</title>
        <line x1="912.446" y1="598.5197" x2="911.8566" y2="485.6004" stroke-linecap="round" stroke-linejoin="round"/>
        <line x1="915.7869" y1="529.99085" x2="809.8541" y2="529.8463" stroke-linecap="round" stroke-linejoin="round"/>
      </g>
    </g>
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
    <g class="cable" v-bind:class="{selected: isSelected && id===3}" v-on:click="id=3; showInspector();">
      <title>Load: {{load(3)}}</title>
        <circle cx="1018.7323" cy="781.5929" r="7.00001118531325" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <line x1="913.446" y1="727.439" x2="914.2379" y2="822.6861" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <line x1="919.2961" y1="781.5929" x2="1011.7323" y2="781.5929" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <circle cx="914.2961" cy="781.5929" r="5.00000798950945"/>
    </g>
    <g class="cable" v-bind:class="{selected: isSelected && id===2}" v-on:click="id=2; showInspector();">
        <title>Load: {{load(2)}}</title>
        <circle cx="1075.5" cy="659.1596" r="7.00001118531331" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <circle cx="936.9761" cy="659.1596" r="5.0000079895095"/>
        <path d="M 912.446 598.5197 L 936.9761 598.5197 L 936.9761 727.439 L 912.5 727.439" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <line x1="1068.5" y1="659.1596" x2="941.9761" y2="659.1596" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
    </g>
    <g class="cable" v-bind:class="{selected: isSelected && id===1}" v-on:click="id=1; showInspector();">
        <title>Load: {{load(1)}}</title>
        <path d="M 912.446 598.5197 L 889.3459 598.5197 L 888.9699 727.439 L 913.446 727.439" stroke-linecap="round" stroke-linejoin="round"/>
        <line x1="884.1612" y1="661.89924" x2="764.9999" y2="662.4351" stroke-linecap="round" stroke-linejoin="round"/>
        <circle cx="758" cy="662.4666" r="7.00001118531322" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" />
        <circle cx="889.1611" cy="661.87676" r="5.00000798950948" />
    </g>
    <g class="cable" v-bind:class="{selected: isSelected && id===0}" v-on:click="id=0; showInspector();">
        <title>Load: {{load(0)}}</title>
        <circle cx="802.6771" cy="529.8365" r="7.00001118531322" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" />
        <circle cx="911.8072" cy="530.14906" r="5.12648231674885" />
        <line x1="912.446" y1="598.5197" x2="911.8566" y2="485.6004" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <line x1="915.7869" y1="529.99085" x2="809.8541" y2="529.8463" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
    </g>
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
        this.$store.commit('init', 8)
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