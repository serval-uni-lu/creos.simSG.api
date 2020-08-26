<template lang="pug">
    div
        | Consumption:
        input(type="number", min="0", step="0.01", v-model.number="consumption")
</template>

<script lang="ts">

    import {Component, Vue} from "vue-property-decorator";
    import {namespace} from "vuex-class";
    import {Selection} from "@/utils/selection";
    import {Cable} from "@/utils/grid";

    const inspectorState = namespace('InspectorState');
    const gridState = namespace('GridSCState');

    @Component
    export default class MeterInsp extends Vue {

        @inspectorState.State
        public selectedElement!: Selection;

        @gridState.State
        public allCables!: Array<Cable>;


        get consumption(): number {
            return this.allCables[this.selectedElement.id].consumption;
        }

        set consumption(newCons: number) {
            this.allCables[this.selectedElement.id].consumption = newCons;
        }


    }
</script>

<style scoped>
    input {
        width: 30%;
        margin-left: 20px;
    }
</style>