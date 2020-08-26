import {ElmtType} from "@/utils/selection";
import {ElmtType} from "@/utils/selection";
<template lang="pug">
    div
        h2 {{selectedElement.type}} - {{selectedElement.id + 1}}
        .form
            FuseInsp(v-if="isFuse")
            MeterInsp(v-else-if="isMeter")
            CableInsp(v-else-if="isCable")


</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import {namespace} from "vuex-class";
    import {ElmtType, Selection} from "@/utils/selection";
    import FuseInsp from "@/components/inspector/FuseInsp.vue";
    import {Fuse} from "@/utils/grid";
    import MeterInsp from "@/components/inspector/MeterInsp.vue";
    import CableInsp from "@/components/inspector/CableInsp.vue";

    const inspectorState = namespace('InspectorState');
    const gridState8 = namespace('GridSCState');

    @Component({
        components: {CableInsp, MeterInsp, FuseInsp}
    })
    export default class Inspector extends Vue{

        @inspectorState.State
        public selectedElement!: Selection;

        @gridState8.State
        public allFuses!: Array<Fuse>;

        get isFuse(): boolean {
            return this.selectedElement.type === ElmtType.Fuse;
        }

        get isMeter(): boolean {
            return this.selectedElement.type === ElmtType.Meter;
        }

        get isCable(): boolean {
            return this.selectedElement.type === ElmtType.Cable;
        }

    }
</script>

<style scoped lang="scss">
    @import "@/scss/global-var.scss";

    .form {
        font-size: $inspector-font-size;
        text-align: left;
        padding-left: 10px;
    }
</style>