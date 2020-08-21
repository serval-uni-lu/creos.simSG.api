<template>
<!--    <g :transform=gPosition :visibility=showOLInfo class="infoBox">-->
    <g :transform=gPosition class="infoBox">
        <rect x="0" y="0" rx="8" ry="8" width="90" :height="15" fill="white"  stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
        <text transform="translate(5 5)" fill="black">
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="700" x="26" y="5">Fuse {{id + 1}}</tspan>
<!--            <tspan font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" y="15">Status: {{status}} [{{fuses[id].confidenceLevel}}%]</tspan>-->
<!--            <tspan font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" y="15">Status: {{status}} [XX%]</tspan>-->
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" y="15">Status: Closed [XX%]</tspan>
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" y="25">Load:</tspan>
<!--            <tspan v-for="ul in uloads()" :key="ul.id" font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" :y="ul.y">- {{ul.value}} A [{{ul.confidence}}%]</tspan>-->
        </text>
    </g>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from "vue-property-decorator";
    import {Point} from "@/utils/SvgTypes";

    @Component
    export default class InfoLayerFuse extends Vue{
        @Prop() id!: number;
        @Prop() location!: Point;

        get gPosition(): string {
            return "translate(" + (this.location.x - 37.5) + " " + (this.location.y - this.heightOLBox/2) + ")";
        }

        get heightOLBox(): number {
            return 15;
        }
    }
</script>

<style scoped>

</style>