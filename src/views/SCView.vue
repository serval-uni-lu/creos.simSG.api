<template lang="pug">
    div
        h2 {{title}}

        section#container
            Action#action
            div
                p #[strong TBD]
            Inspector


</template>

<script lang="ts">
    import {Component,Vue} from "vue-property-decorator";
    import scenarios from "@/assets/scenarios/scenarios.json"
    import Action from "@/components/scView/Action.vue";
    import Inspector from "@/components/scView/Inspector.vue";

    const SCProps = Vue.extend({
        props: {
            name: String
        }
    });

    @Component({
        components: {Action, Inspector}
    })
    export default class SCView extends SCProps {
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
    div {
        text-align: center;
    }

    #container {
        display: flex;
    }
     #action {
         width: 20%;
         flex: 1;
     }
</style>