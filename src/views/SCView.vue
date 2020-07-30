<template lang="pug">
    div
        h2 Predefined scenarios
        ul
            ScenarioList(v-for="sc in scenarios" v-bind:key="sc.id" v-bind:title="sc.shortTitle" v-bind:url="sc.url")
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import ScenarioList from "@/components/ScenarioList.vue";
    import json from "@/assets/scenarios/scenarios.json";

    @Component({
        components: {ScenarioList}
    })
    export default class SCView extends Vue{
        private scenarios = json;
    }
</script>

<style lang="scss" scoped>
    div {
        text-align: center;
    }
</style>