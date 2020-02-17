<template>
    <g class="fuse" v-bind:class="{fClosed: fuses[id].isClosed, selected: isSelected}" v-on:click="showInspector($event, id)">
        <rect :x="xRect" :y="yRect" width="10" height="10" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <text :transform="translate">
            <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" x="0" y="11">Fuse {{id + 1}}</tspan>
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
        isSelected: function() {
            return this.selectedElmt.isSameAs(this.id, 'fuse')
        },
        ...mapState({
            fuses: state => state.fuses,
            selectedElmt: state => state.selectedElmt
        })
    },
    methods: {
        showInspector: function(event, id) {
            if(event.altKey) {
                this.$store.commit('switchFuse', id);
            } else {
                let info = {
                    elemtId: id,
                    elemtType: "fuse"
                }
                this.$store.commit('showInspector', info);
            }
        }
    }
}
</script>



<style lang="scss" scoped>
g.fuse:not(selected) {
    
    rect {
        fill: white;
        stroke:$color-schema;
    }

    text {
        stroke: none;
        fill: $color-schema;
    }

    &.fClosed {
        rect {
            fill: $color-schema;
        }
    }
}

g.fuse.selected {
    
    rect {
        fill: white;
        stroke: $color-selection;
    }

    text {
        stroke: none;
        fill: $color-selection;
    }

    &.fClosed {
        rect {
            fill: $color-selection;
        }
    }
}
</style>