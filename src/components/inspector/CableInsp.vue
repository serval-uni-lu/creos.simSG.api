<template lang="pug">
    div
        | Load:
        div
            span(v-for="ul in uLoads()" :key="ul.id")  - {{ul.value}} A [{{ul.confidence}} %] <br/>
</template>

<script lang="ts">

    import {Component, Vue} from "vue-property-decorator";
    import {namespace} from "vuex-class";
    import {ULoadInfo, uLoadsData} from "@/utils/uLoadsUtils";
    import {Selection} from "@/utils/selection";
    import {Cable} from "@/utils/grid";

    const inspectorState = namespace('InspectorState');
    const gridState = namespace('GridSCState');

    @Component
    export default class CableInsp extends Vue {

        @inspectorState.State
        public selectedElement!: Selection;

        @gridState.State
        public allCables!: Array<Cable>;

        public uLoads(): Array<ULoadInfo> {
            return uLoadsData(this.allCables[this.selectedElement.id].uLoads);
        }


    }
</script>

<style scoped>

</style>