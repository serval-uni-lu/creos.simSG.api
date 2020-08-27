<template lang="pug">
    section
        h2 {{title}}

        div#viewer
            Action#action
            SCViewer(v-bind:name="name")#scviewer
            Inspector#inspector(v-if="inspVisible")


</template>

<script lang="ts">
    import {Component,Vue, Prop} from "vue-property-decorator";
    import scenarios from "@/assets/scenarios/scenarios.json"
    import SCViewer from "@/components/scView/SCViewer.vue";
    import Action from "@/components/Action.vue";
    import Inspector from "@/components/inspector/Inspector.vue";
    import {namespace} from "vuex-class";
    import {NullSelection, Selection} from "@/utils/selection";

    const inspectorState = namespace('InspectorState');

    @Component({
        components: {SCViewer, Action, Inspector}
    })
    export default class SCView extends Vue {
        @Prop() name!: string;

        @inspectorState.State
        public selectedElement!: Selection;

        get inspVisible(): boolean {
            return !this.selectedElement.equals(NullSelection);
        }

        get title(): string {
            for(const sc of scenarios) {
                if(sc.url === this.name) {
                    return sc.title;
                }
            }
            return "Scenario \"" + this.name + "\" does not exist."
        }

    }

</script>

<style lang="scss" scoped>
    $size-side-elmt: 19%;
    $margin: 1%;
    $remaining: calc(100% - ($margin + $size-side-elmt) * 2);
    $margin-bottom: 10px;
    $color: lightgrey;

    section {
        text-align: center;
        display: flex;
        flex-direction: column;
    }

    #viewer {
        flex: 1;
        display: flex;
        flex-direction: row;
    }


    #action {
        width: $size-side-elmt;
        margin-left: $margin;
        margin-bottom: $margin-bottom;
        background-color: $color;
    }

    #scviewer {
        width: $remaining;
    }

    #inspector {
        width: $size-side-elmt;
        box-shadow: 10px 10px 16px darkgray;
        background-color: $color;
        margin-bottom: $margin-bottom;
        margin-right: $margin;
        position: relative;
    }
</style>