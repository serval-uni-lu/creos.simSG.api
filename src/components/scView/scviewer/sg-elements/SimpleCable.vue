<template>
<!--    <g class="cable" v-bind:class="{selected: isSelected}" v-on:click="showInspector();">-->
    <g class="cable">
<!--        <title>Load: {{load()}}</title>-->
        <title>Load: </title>
        <line :x1=line1.x1 :y1=line1.y1 :x2=line1.x2 :y2=line1.y2 stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <line :x1=line2.x1 :y1=line2.y1 :x2=line2.x2 :y2=line2.y2 stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <circle :cx=line1.x1 :cy=circle.y r="5"/>
        <circle :cx=circle.x :cy=circle.y r="7" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
    </g>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import {Line, Circle} from "@/utils/SvgTypes"

    @Component
    export default class SimpleCable extends Vue{
        @Prop() id!: number;
        @Prop() line1!: Line;
        @Prop() line2!: Line;
        @Prop() circle!: Circle;
    }
</script>

<style scoped lang="scss">
    @import "@/scss/cable.scss";
</style>