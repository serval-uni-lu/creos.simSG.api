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
            <g class="hidden">
                <g v-on:click="id=0; showInspector();">
                    <path d="M 891.5825 485.6004 L 891.5825 598.5197 L 911.7323 598.5197" stroke-linecap="round" stroke-linejoin="round"/>
                    <line x1="891.5825" y1="532" x2="791.9999" y2="531.53284" stroke-linecap="round" stroke-linejoin="round" />
                </g>
                <g v-on:click="id=1; showInspector();">
                    <path d="M 931.8821 485.6004 L 931.8821 598.5197 L 911.7323 598.5197" stroke-linecap="round" stroke-linejoin="round"/>
                    <line x1="1020.5" y1="531.5" x2="932" y2="531.5" stroke-linecap="round" stroke-linejoin="round" />
                </g>
                <g v-on:click="id=2; showInspector();">
                    <line x1="911.7323" y1="706.5" x2="911.7323" y2="598.5197" stroke-linecap="round" stroke-linejoin="round" />
                    <line x1="1001.1179" y1="660.50986" x2="912.6179" y2="660.50986" stroke-linecap="round" stroke-linejoin="round"/>
                </g>
            </g>
            <g id="Cabinet">
                <rect x="876.8251" y="582.5197" width="71" height="32" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                <text transform="translate(947.8251 591.3517)" fill="black">
                    <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="2.0160011" y="11">Cabinet</tspan>
                </text>
            </g>
            <g class="cable" v-bind:class="{selected: isSelected && id===0}" v-on:click="id=0; showInspector();">
                <path d="M 891.5825 485.6004 L 891.5825 598.5197 L 911.7323 598.5197" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                <line x1="891.5825" y1="532" x2="791.9999" y2="531.53284" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                <circle cx="785" cy="531.5" r="7.00001118531322" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                <circle cx="891.5825" cy="532.03756" r="5.00000798950946"/>
            </g>
            <g class="cable" v-bind:class="{selected: isSelected && id===1}" v-on:click="id=1; showInspector();">
                <path d="M 931.8821 485.6004 L 931.8821 598.5197 L 911.7323 598.5197" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                <line x1="1020.5" y1="531.5" x2="932" y2="531.5" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                <circle cx="931.8821" cy="531.86574" r="5.00000798950949"/>
                <circle cx="1027.5" cy="531.5" r="7.00001118531322" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
            </g>
            <g class="cable" v-bind:class="{selected: isSelected && id===2}" v-on:click="id=2; showInspector();">
                <line x1="911.7323" y1="706.5" x2="911.7323" y2="598.5197" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                <line x1="1001.1179" y1="660.50986" x2="912.6179" y2="660.50986" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                <circle cx="912.5" cy="660.8756" r="5.00000798950948"/>
                <circle cx="1008.1179" cy="660.50986" r="7.00001118531324" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
            </g>
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
import { mapState } from 'vuex'

export default {
    data: function() {
        return {
            id: -1
        }
        
    },
    components: {Fuse, Meter},
    computed: {
         isSelected: function() {
            return this.selectedElmt.isSameAs(this.id, 'cable')
        },
        ...mapState({
            selectedElmt: state => state.selectedElmt
        })
    },
    methods: {
        showInspector: function() {
            let info = {
                elemtId: this.id,
                elemtType: "cable"
            }
            this.$store.commit('showInspector', info)
        }
    },
    created() {
        this.$store.commit('init', 6)
    }
}
</script>



<style lang="scss" scoped>
@import "@/scss/cable.scss";
</style>
