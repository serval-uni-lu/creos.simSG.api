<template lang="pug">
    section
        h2 {{title}}

        div#viewer
            Action#action
            SCViewer(v-bind:name="name")#scviewer
            Inspector#inspector


</template>

<script lang="ts">
    import {Component,Vue, Prop} from "vue-property-decorator";
    import scenarios from "@/assets/scenarios/scenarios.json"
    import Action from "@/components/scView/Action.vue";
    import Inspector from "@/components/scView/Inspector.vue";
    import SCViewer from "@/components/scView/SCViewer.vue";

    @Component({
        components: {SCViewer, Action, Inspector}
    })
    export default class SCView extends Vue {
        @Prop() name!: string;

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
        width: 20%;
    }
</style>