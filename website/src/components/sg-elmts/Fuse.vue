<template>
    <g class="fuse" v-bind:class="{close: fuseStatus[id]}" v-on:click="switchFuse(id)">
        <rect :x="xRect" :y="yRect" width="10" height="10" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <text :transform="translate" fill="black">
            <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="0" y="11">Fuse {{id + 1}}</tspan>
        </text>
    </g>
</template>


<script>
import { mapState, mapMutations } from 'vuex'

export default {
    props: {
        id: Number,
        xRect: Number,
        yRect: Number,
        displayLeft: {
            type: Boolean,
            default: false
        }
    },
    computed: {
        translate: function() {
            var x;
            if(this.displayLeft) {
                x = this.xRect - 38;
            } else {
                x = this.xRect + 11;
            }
            return "translate(" +  (x) + " " + (this.yRect - 2) + ")";
        },
        ...mapState({
            fuseStatus: state => state.fuseStatus,
        })
    },
    methods: {
        ...mapMutations(['switchFuse'])
    }
}
</script>



<style lang="scss" scoped>
g.fuse {
    rect {
        fill: white;
    }

    &.close {
        rect {
            fill: black;
        }
    }
}
</style>