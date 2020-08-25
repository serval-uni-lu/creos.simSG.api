<template lang="pug">
    div
        h2 {{selectedElement.type}} - {{selectedElement.id + 1}}
        .form
            FuseInsp(v-if="isFuse")


</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import {namespace} from "vuex-class";
    import {ElmtType, Selection} from "@/utils/selection";
    import FuseInsp from "@/components/inspector/FuseInsp.vue";
    import {Fuse} from "@/utils/grid";

    const inspectorState = namespace('InspectorState');
    const gridState8 = namespace('GridSCState');

    @Component({
        components: {FuseInsp}
    })
    export default class Inspector extends Vue{

        @inspectorState.State
        public selectedElement!: Selection;

        @gridState8.State
        public allFuses!: Array<Fuse>;

        get isFuse(): boolean {
            return this.selectedElement.type === ElmtType.Fuse;
        }

        get fuse(): Fuse {
            return this.allFuses[0];
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