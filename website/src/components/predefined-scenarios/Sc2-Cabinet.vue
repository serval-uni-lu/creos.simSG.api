<template>
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
        <g fill="none" stroke-dasharray="none" stroke="none" stroke-opacity="1" fill-opacity="1">
            <Meter :id=0 :xRect=208.2 :yRect=194 />
            <Meter :id=1 :xRect=149 :yRect=341.3 />
            <Meter :id=2 :xRect=345.8 :yRect=341.7 />
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
            <Fuse :id=0 :xRect=268.7441 :yRect=139.77368 />
            <Fuse :id=1 :xRect=268.7441 :yRect=241.65787 />
            <Fuse :id=2 :xRect=243.8201 :yRect=293.38053 :displayLeft="'left'" />
            <Fuse :id=3 :xRect=293.67107 :yRect=293.2013 />
            <Fuse :id=4 :xRect=212.78744 :yRect=360.4025 />
            <Fuse :id=5 :xRect=311.8802 :yRect=360.3309 :displayLeft="'left'"/>
        </g>
    </svg>
</template>



<script>
import Fuse from "@/components/sg-elmts/Fuse.vue"
import Meter from "@/components/sg-elmts/Meter.vue"

export default {
    components: {Fuse, Meter},
    created() {
        this.$store.commit('init', 6)
    }
}
</script>
