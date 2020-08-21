<template>
<!--    <g class="hidden" v-on:click="showInspector();">-->
    <g class="hidden">
<!--        <title>Load: {{load()}}</title>-->
        <title>Load: </title>
        <line :x1=line1.x1 :y1=line1.y1 :x2=line1.x2 :y2=line1.y2 stroke-linecap="round" stroke-linejoin="round"/>
        <line :x1=line2.x1 :y1=line2.y1 :x2=line2.x2 :y2=line2.y2 stroke-linecap="round" stroke-linejoin="round"/>
    </g>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from "vue-property-decorator";
    import {Line} from "../../../../utils/SvgTypes";

    @Component
    export default class HiddenSimpleCable extends Vue{
        @Prop() id!: number;
        @Prop() line1!: Line;
        @Prop() line2!: Line;

    }
</script>

<style scoped lang="scss">
    @import "@/scss/cable.scss";
</style>