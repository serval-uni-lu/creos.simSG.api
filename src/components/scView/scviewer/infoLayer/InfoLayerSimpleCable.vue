<template>
<!--    <g :transform=gPosition :visibility=showOLInfo class="infoBox">-->
    <g :transform=gPosition class="infoBox">
<!--        <rect x="0" y="0" rx="8" ry="8" width="75" :height="heightOLBox()" fill="white"  stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>-->
        <rect x="0" y="0" rx="8" ry="8" width="75" :height="15" fill="white"  stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
        <text transform="translate(5 5)" fill="black">
            <tspan font-family="Helvetica Neue" font-size="8" font-weight="700" x="26" y="5">Cable {{id + 1}}</tspan>
<!--            <tspan v-for="ul in uloads()" :key="ul.id" font-family="Helvetica Neue" font-size="8" font-weight="400" x="0" :y="ul.y">- {{ul.value}} A [{{ul.confidence}}%]</tspan>-->
        </text>
    </g>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from "vue-property-decorator";
    import {Line, Circle} from "../../../../utils/SvgTypes";

    @Component
    export default class InfoLayerSimpleCable extends Vue {
       @Prop() id!: number;
       @Prop() line1!: Line;
       @Prop() line2!: Line;
       @Prop() circle!: Circle;

       get xLoadInfo(): number {
           return this.line1.x1 - 25;
       }

       get yLoadInfo(): number {
           return this.circle.x;
       }

       get gPosition(): string {
           return "translate(" + (this.xLoadInfo) + " " + this.yLoadInfo + ")";
       }



    }
</script>

<style scoped>

</style>