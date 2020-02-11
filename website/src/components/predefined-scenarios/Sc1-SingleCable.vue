<template lang="pug">
section#container
    #empty

    #vue
        <svg viewBox="587 193 222 199">
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
            <g id="SC1-SingleCable" fill="none" stroke-dasharray="none" stroke="none" stroke-opacity="1" fill-opacity="1">
                <g id="Substation">
                    <rect x="644" y="194.4" width="99.99999" height="47.644" fill="white"/>
                    <rect x="644" y="194.4" width="99.99999" height="47.644" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <path d="M 644 242.04035 L 744 194.39635 L 644 194.39635 Z" fill="black"/>
                    <path d="M 644 242.04035 L 744 194.39635 L 644 194.39635 Z" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <text transform="translate(747.8 210.722)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-style="italic" font-weight="400" fill="black" x="1.4260004" y="11">Substation</tspan>
                    </text>
                </g>
                <g id="Meter" v-on:click="showInspector()">
                    <rect x="612" y="305.50093" width="37" height="27.04319" fill="white"/>
                    <rect x="612" y="305.50093" width="37" height="27.04319" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <circle cx="623.8007" cy="319.02252" r="7.37542706758215" fill="white"/>
                    <circle cx="623.8007" cy="319.02252" r="7.37542706758215" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <text transform="translate(612 332.5441)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="5.206" y="11">{{consumption}} A</tspan>
                    </text>
                </g>
                <g id="DeadEnds">
                    <circle cx="694" cy="384" r="7.00001118531325" fill="white"/>
                    <circle cx="694" cy="384" r="7.00001118531325" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                </g>
                <g id="Cable">
                    <line x1="694" y1="377" x2="694" y2="242.044" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <line x1="694" y1="291.70832" x2="601.5" y2="292.00708" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <circle cx="694" cy="291.98997" r="5.00000798950947" fill="black"/>
                    <line x1="630.5" y1="305.50093" x2="630.5" y2="292.02262" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                </g>
                <g id="Fuse 1">
                    <rect class="fuse" x="689" y="244.79135" width="10" height="10" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" v-on:click="switchFuse('f1')" v-bind:class="{close: f1Closed}"/>
                    <text transform="translate(699.494 242.29135)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="0" y="11">Fuse 1</tspan>
                    </text>
                </g>
                <g id="Fuse 2">
                    <rect class="fuse" x="689" y="360.3567" width="10" height="10" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" v-on:click="switchFuse('f2')" v-bind:class="{close: f2Closed}"/>
                    <text transform="translate(701.5 358.1887)" fill="black">
                        <tspan font-family="Helvetica Neue" font-size="12" font-weight="400" fill="black" x="0" y="11">Fuse 2</tspan>
                    </text>
                </g>
                <g id="DE-Connection">
                    <circle cx="594.5" cy="292.0297" r="7.00001118531321" fill="white"/>
                    <circle cx="594.5" cy="292.0297" r="7.00001118531321" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                </g>
            </g>
        </svg>
        

    #inspector(v-bind:class="{show: inspVisible}")
        p.title Inspector
        form 
            | Consumption:
            input(type="number", min="0", v-model="consumption")
</template>

<script>
export default {
    data: function() {
        return {
            f1Closed: true,
            f2Closed: true,
            consumption: 0.,
            inspVisible: false
        }
    },
    methods: {
        switchFuse: function(fuseId) {
            if(fuseId === 'f1') {
                this.f1Closed = !this.f1Closed;
            } else {
                this.f2Closed = !this.f2Closed;
            }
        },
        showInspector: function() {
            this.inspVisible = !this.inspVisible;
        }
    }
}
</script>

<style lang="scss" scoped>
rect.fuse {
    fill: white;

    &.close {
        fill: black;
    }
}

#container {
    width: 100%;
    margin: auto;
    height: 400px;
}

#empty {
    height: 400px;
    width: 20%;
    float: left;
}

#vue {
    height: 400px;
    width: 60%;
    float: left;

     svg {
        height: 400px;
    }
}

#inspector {
    height: 400px;
    width: 20%;
    display: none;
    float: right;

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

    
}


</style>