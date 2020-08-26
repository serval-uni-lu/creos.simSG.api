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
        width: 20%;
    }

    #scviewer {
        width: 60%;
    }

    #inspector {
        width: 19%;
        box-shadow: 10px 10px 16px darkgray;
        background-color: lightgrey;
        margin-bottom: 10px;
        margin-right: 1%;
        position: relative;
    }
</style>