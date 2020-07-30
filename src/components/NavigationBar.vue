<template lang="pug">
    nav#main-nav
        ul.nav-ul.separator
            li.nav-li  #[router-link.home(to="/") Home]
            li.nav-li  #[router-link(to="/scenario") Scenarios]
                ol.stop
                    ScenarioList(v-for="sc in scenarios" v-bind:key="sc.id" v-bind:title="sc.shortTitle" v-bind:url="sc.url")
            li.nav-li  #[router-link(to="/scenario-builder") Scenario Builder]
            li.nav-li  #[router-link(to="/lux-sg") Luxembourg Smart Grid]
</template>

<script lang="ts">
    import { Component, Vue } from 'vue-property-decorator';
    import ScenarioList from "@/components/ScenarioList.vue";
    import json from "@/assets/scenarios/scenarios.json";

    @Component({
        components: {ScenarioList}
    })
    export default class NavigationBar extends Vue {
        private scenarios = json;
    }
</script>

<style lang="scss">
    #main-nav {
        background-color: lightgray;
        display: flex;
        justify-content: center;
        align-items: center;

        ul.separator {
            margin: 0;
        }

        ul.separator li + li:before {
            content: "|";
            padding: 0 10px;
        }

        ol.stop li + li:before {
            content: "";
            padding: 0;
        }

        ul.separator li {
            position: relative;
            display: inline-block;
        }

        ul.separator a {
            display: inline-block;
        }


        ul.separator li ol.stop {
            padding: 0 10px;
            position: absolute;
            top: 100%;
            display:none;
            left: 2 * 10px;
            box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
            text-align: left;
            background-color: lightgray;
        }

        ul.separator li:hover ol.stop {
            display:inline-block;
        }

        ol.stop > li {
            display: block;
        }

    }
</style>