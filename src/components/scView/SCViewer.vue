<template lang="pug">
    div
        ToolBar
        SingleCable(v-if="name === 'sc1-sglCable'")
        h3(v-else) Oups... Component not yet implemented for {{name}}
</template>

<script lang="ts">
    import {Component,Vue, Prop} from "vue-property-decorator";
    import ToolBar from "@/components/scView/scviewer/ToolBar.vue";
    import SingleCable from "@/components/scView/scviewer/scenarios/SingleCable.vue";

    @Component({
        components: {SingleCable, ToolBar}
    })
    export default class SCViewer extends Vue {
        @Prop() name!: string
    }
</script>

<style lang="scss">
    svg {
        width: 80%; //todo: to be fixed... https://css-tricks.com/scale-svg/
    }

</style>