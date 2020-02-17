<template>
    <g class="fuse" v-bind:class="{fClosed: fuses[id].isClosed}" v-on:click="switchFuse(id)">
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
            type: String,
            default: "right",
            validator: function(value) {
                return ["left", "right", "top", "bottom"].indexOf(value) !== -1;
            }
        },
        shiftTextX: {
            type: Number,
            default: 0,
        },
        shiftTextY: {
            type: Number,
            default: 0,
        }
    },
    computed: {
        translate: function() {
            var x = this.xRect;
            var y = this.yRect - 2;
            if(this.displayLeft === "left") {
                x = this.xRect - 38;
            } else if(this.displayLeft === "right"){
                x = this.xRect + 11;
            } else if(this.displayLeft === "top") {
                y = this.yRect - 14;
                console.log("here");
            } else {
                y = this.yRect + 12;
            }
            return "translate(" +  (x + this.shiftTextX) + " " + (y + this.shiftTextY) + ")";
        },
        ...mapState({
            fuses: state => state.fuses,
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

    &.fClosed {
        rect {
            fill: black;
        }
    }
}
</style>