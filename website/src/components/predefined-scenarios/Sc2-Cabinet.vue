<template lang="pug">
section#container
    #empty

    #vue
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
            <g id="SC2-Cabinet" fill="none" stroke-dasharray="none" stroke="none" stroke-opacity="1" fill-opacity="1">
                <g id="Meter-Cable3" v-on:click="showInspector(3)">
                    <rect x="345.8" y="341.2" width="37" height="27.04319" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                    <circle cx="357.60067" cy="354.7216" r="7.37542706758215" fill="white"/>
                    <circle cx="357.60067" cy="354.7216" r="7.37542706758215" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <text transform="translate(348.42796 368.2432)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x=".30804838" y="11">{{consumptions[2]}} A</tspan>
                    </text>
                    <line x1="364.27475" y1="341.2" x2="364.25583" y2="331.0665" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                </g>
                <g id="Meter-Cable2" v-on:click="showInspector(2)">
                    <rect x="149" y="341.2" width="37" height="27.04319" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                    <circle cx="160.80066" cy="354.7216" r="7.37542706758212" fill="white"/>
                    <circle cx="160.80066" cy="354.7216" r="7.37542706758212" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <line x1="167.47674" y1="341.2" x2="167.45846" y2="330.57293" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <text transform="translate(151.62795 368.2432)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x=".30804838" y="11">{{consumptions[1]}} A</tspan>
                    </text>
                </g>
                <g id="Meter-Cable1" v-on:click="showInspector(1)">
                    <text transform="translate(210.82796 223.3173)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x=".30804838" y="11">{{consumptions[0]}} A</tspan>
                    </text>
                    <rect x="208.2" y="196.2741" width="37" height="27.04319" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" fill="white"/>
                    <circle cx="220.00067" cy="209.7957" r="7.37542706758205" fill="white"/>
                    <circle cx="220.00067" cy="209.7957" r="7.37542706758205" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <line x1="226.7" y1="196.2741" x2="226.7" y2="182.7958" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                </g>
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
                <g id="Cable3">
                    <path d="M 273.7441 273.4163 L 298.6681 273.4163 L 298.6726 311 L 317.2561 311 L 316.79254 378.00016" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <circle cx="404.8" cy="331" r="7.00001118531327" fill="white"/>
                    <circle cx="404.8" cy="331" r="7.00001118531327" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <circle cx="317.1173" cy="331.0592" r="5.00000798950949" fill="black"/>
                    <line x1="322.1173" y1="331.0558" x2="397.8" y2="331.00473" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <circle cx="316.7441" cy="385" r="7.00001118531326" fill="white"/>
                    <circle cx="316.7441" cy="385" r="7.00001118531326" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                </g>
                <g id="Cable2">
                    <path d="M 273.7441 273.4163 L 248.8201 273.4163 L 248.8201 311 L 217.67497 311 L 217.81348 378" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <circle cx="217.71443" cy="330.0858" r="5.00000798950948" fill="black"/>
                    <line x1="212.71466" y1="330.13425" x2="130.39968" y2="330.93216" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <circle cx="123.4" cy="331" r="7.00001118531328" fill="white"/>
                    <circle cx="123.4" cy="331" r="7.00001118531328" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <circle cx="217.82796" cy="385" r="7.00001118531327" fill="white"/>
                    <circle cx="217.82796" cy="385" r="7.00001118531327" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                </g>
                <g id="Cable1">
                    <line x1="268.7441" y1="182.50076" x2="199" y2="182.50076" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <line x1="273.7441" y1="273.4163" x2="273.7441" y2="136.939" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <circle cx="273.7441" cy="182.50076" r="5.00000798950948" fill="black"/>
                    <circle cx="192" cy="182.50076" r="7.00001118531326" fill="white"/>
                    <circle cx="192" cy="182.50076" r="7.00001118531326" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                </g>
                <g id="Fuse6" class="fuse" v-bind:class="{close: fuseStatus[5]}" v-on:click="switchFuse(5)">
                    <text transform="translate(274.8 359.4)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="0" y="11">Fuse 6</tspan>
                    </text>
                    <rect x="311.8802" y="360.3309" width="10" height="10" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" />
                </g>
                <g id="Fuse5" class="fuse" v-bind:class="{close: fuseStatus[4]}" v-on:click="switchFuse(4)">
                    <text transform="translate(223.8 358.2345)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="0" y="11">Fuse 5</tspan>
                    </text>
                    <rect x="212.78744" y="360.4025" width="10" height="10" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" />
                </g>
                <g id="Fuse4" class="fuse" v-bind:class="{close: fuseStatus[3]}" v-on:click="switchFuse(3)">
                    <rect x="293.67107" y="293.2013" width="10" height="10" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" />
                    <text transform="translate(306.8 291.55883)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="0" y="11">Fuse 4</tspan>
                    </text>
                </g>
                <g id="Fuse3" class="fuse" v-bind:class="{close: fuseStatus[2]}" v-on:click="switchFuse(2)">
                    <rect x="243.8201" y="293.38053" width="10" height="10" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" />
                    <text transform="translate(203.988 291.55883)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="0" y="11">Fuse 3</tspan>
                    </text>
                </g>
                <g id="Fuse2" class="fuse" v-bind:class="{close: fuseStatus[1]}" v-on:click="switchFuse(1)">
                    <rect x="268.7441" y="241.65787" width="10" height="10" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" />
                    <text transform="translate(281.2441 240)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="0" y="11">Fuse 2</tspan>
                    </text>
                </g>
                <g id="Fuse1" class="fuse" v-bind:class="{close: fuseStatus[0]}" v-on:click="switchFuse(0)">
                    <rect x="268.7441" y="139.77368" width="10" height="10" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"  />
                    <text transform="translate(279.2381 137.27368)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="0" y="11">Fuse 1</tspan>
                    </text>
                </g>
            </g>
            </svg>

    #inspector(v-bind:class="{show: inspVisible}")
        p.title Inspector Meter - {{currentMeterId}}
        form 
            | Consumption:
            input(type="number", min="0", v-model="consumptions[currentMeterId - 1]")
        .closingButton(v-on:click="hideInspector()")
            <svg viewBox="871 749 32 32">
                <defs/>
                <g fill="white" stroke-dasharray="none" stroke="none" stroke-opacity="1" fill-opacity="1">
                    <line x1="871" y1="749" x2="902.852" y2="780.6595" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <line x1="903" y1="748.9037" x2="871.3405" y2="780.7558" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                </g>
            </svg>
</template>



<script>
import Vue from "vue"

export default {
    data: function() {
        return {
            fuseStatus: [true, true, true, true, true, true],
            consumptions: [0., 0., 0.],
            currentMeterId: 0,
            inspVisible: false
        }
    },
    computed: {
        currentConsumption: function() {
            return this.consumptions[this.currentMeterId]
        }
    },
    methods: {
        switchFuse: function(fuseId) {
            Vue.set(this.fuseStatus, fuseId, !this.fuseStatus[fuseId])
        },
        showInspector: function(meterId) {
            this.currentMeterId = meterId;
            this.inspVisible = true;
        },
        hideInspector: function() {
            this.inspVisible = false;
        }
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

$height: 480px;

#container {
    width: 100%;
    margin: auto;
    height: $height;
}

#empty {
    height: $height;
    width: 19%;
    float: left;
}

#vue {
    height: $height;
    width: 60%;
    float: left;

     svg {
        height: $height;
    }
}

#inspector {
    height: $height;
    width: 19%;
    display: none;
    float: right;
    margin-right: 1%;
    position: relative;

    &.show {
        display: inline;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    }

    form {
        font-size: $inspector-font-size;
        text-align: left;
        padding-left: 10px;
        input {
            width: 15%;
            margin-left: 20px; 
        }
    }

    .closingButton {
        position: absolute;
        top: 5px;
        right: 5px;

        svg {
            width: 20px;

            line {
                stroke: lightgray;
            }

            &:hover {
                line {
                    stroke: black;
                }
            }
        }
    }

    
}


</style>