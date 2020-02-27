<template>
    <g>
        <g class="fuse" v-bind:class="{fClosed: fuses[id].isClosed, selected: isSelected}" v-on:click="showInspector($event, id)">
            <title>Status: {{status}}; Load: {{load}}</title>
            <rect :x="xRect" :y="yRect" width="10" height="10" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
            <text :transform="translate">
                <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" x="0" y="11">Fuse {{id + 1}}</tspan>
            </text>
        </g>
        <g :transform=gPosition :visibility=showOLInfo class="infoBox">
            <rect x="0" y="0" rx="8" ry="8" width="75" :height="heightOLBox()" fill="white"  stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
            <text transform="translate(5 5)" fill="black">
                <tspan font-family="Helvetica Neue" font-size="8" font-weight="700" x="26" y="5">Fuse {{id + 1}}</tspan>
                <tspan font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" y="15">Status: {{status}}</tspan>
            </text>
        </g>
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
        showOLInfo: function() {
            return (this.infoOverLayerVis)? "visible": "hidden";
        },
        gPosition: function() {
            return "translate(" + (this.xRect - 37.5) + " " + (this.yRect - this.heightOLBox()/2) + ")";
        },
        status: function() {
            return (this.fuses[this.id].isClosed)? "Closed" : "Open";
        },
        load: function() {
            var load = this.fuses[this.id].load;
            return (load === -1)? "To be computed..." : load.toFixed(2) + " A";
        },
        translate: function() {
            var x = this.xRect;
            var y = this.yRect - 2;
            if(this.displayLeft === "left") {
                x = this.xRect - 38;
            } else if(this.displayLeft === "right"){
                x = this.xRect + 11;
            } else if(this.displayLeft === "top") {
                y = this.yRect - 14;
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
            selectedElmt: state => state.selectedElmt,
            infoOverLayerVis: state => state.infoOverLayerVis
        })
    },
    methods: {
         heightOLBox: function() {
            return 25;
        },
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

.infoBox {
    rect {
        stroke: $color-selection,
    }

    tspan {
        fill: $color-selection
    }
}
</style>