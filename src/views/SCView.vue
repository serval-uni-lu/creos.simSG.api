<template lang="pug">
    div
        h2 {{title}}
</template>

<script lang="ts">
    import {Component,Vue} from "vue-property-decorator";
    import scenarios from "@/assets/scenarios/scenarios.json"

    const SCProps = Vue.extend({
        props: {
            name: String
        }
    });

    @Component
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
</style>