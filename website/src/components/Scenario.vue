<template lang="pug">
    div
        h2 Predifined scenarios
        ul
            <ScenarioList v-for="sc in scenarios" v-bind:key="sc.id" v-bind:name="sc.title" v-bind:url="sc.url"/>
</template>

<script lang="ts">
import ScenarioList from "@/components/ScenarioList.vue"
import json from "@/assets/scenarios/scenarios.json"

var scenarios = json;

export default {
    name: 'scenario',
    components: {ScenarioList},
    data: function() {
        return {
            scenarios: scenarios
        }
    }
}
</script>