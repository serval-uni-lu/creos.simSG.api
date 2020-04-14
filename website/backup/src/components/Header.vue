<template lang="pug">
    #header 
        #title
            p SimSG: Smart Grid Simulator

        #nav
            ul.separator
                li  <router-link to="/">Home</router-link>
                li  <router-link to="/scenario">Scenarios</router-link>
                    ol.stop
                        <ScenarioList v-for="sc in scenarios" v-bind:key="sc.id" v-bind:title="sc.shortTile" v-bind:url="sc.url"/>
                li  <router-link to="/scenario-builder">Scenario Builder</router-link>
                li  <router-link to="/lux-sg">Luxembourg Smart Grid</router-link>


        #logos
            img.creos(src="@/assets/creos.jpg" alt="logo creos")
            img.serval(src="@/assets/serval.svg" alt="logo serval")
  
</template>


<script>
import ScenarioList from "@/components/ScenarioList.vue"
import json from "@/assets/scenarios/scenarios.json"

var scenarios = json;


export default {
    components: {ScenarioList},
    data: function() {
        return {
            scenarios: scenarios
        }
    }
}
</script>


<style lang="scss" scoped>
$logo-height: 90px;
$position: right;
$header-color: lightgray;

$padding-menu-separator: 10px;

ul.separator li + li:before {
    content: "|";
    padding: 0 $padding-menu-separator;
}

ol.stop li + li:before {
    content: "";
    padding: 0px;
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
    background-color: $header-color;
    left: 2 * $padding-menu-separator;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    text-align: left;
}

ul.separator li:hover ol.stop {
    display:inline-block;
}

ol.stop > li {
    display: block;
}

#header {
    margin-bottom: 10px;
    background-color: $header-color;
    padding-bottom: 20px;
}

#title {
    position: relative;
    padding-top: 30px;

    p {
        display: inline;
        font-size: $header-title-size; 
        font-weight: bold;
    }
}

#logos {
    position: absolute;
    top: 0px;
    right: 10px;
    float: $position;
    background-color: white;
    border-radius: 0px 0px 8px 8px;

    .creos {
        padding-right: 10px;
        padding-left: 5px;
        height: $logo-height;
    }

    .serval {
        height: $logo-height;
    }

}
</style>