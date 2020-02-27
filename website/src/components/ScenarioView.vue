<template lang="pug">
    div
        h2 {{title}}

        .toolbar
            button.infoLayer(title="Add a layer with all information" type="button" class="btn btn-secondary" v-bind:class="{active: infoOverLayerVis}" v-on:click="showHideInfoOL()")

        section#container
            Action(id="action" :scenarioID="scenarioId")

            #vue
                Sc1SingleCable(v-if="name === 'sc1-sglCable'")
                Sc2Cable(v-else-if="name === 'sc2-cabinet'")
                Sc3ParaSub(v-else-if="name === 'sc3-para-transfo'")
                Sc4ParaCab(v-else-if="name === 'sc4-para-cabinet'")
                Sc5IndiPara(v-else-if="name === 'sc5-indirect-para'")
                h3(v-else) Oups...Component not yet implemented.

            <Inspector id="inspector" :elmtId="selection.id" :elmtType="selection.type" v-bind:class="{show: inspVisible}"/>

</template>

<script>
import scenarios from "@/assets/scenarios/scenarios.json"
import Sc1SingleCable from "@/components/predefined-scenarios/Sc1-SingleCable.vue"
import Sc2Cable from "@/components/predefined-scenarios/Sc2-Cabinet.vue"
import Sc3ParaSub from "@/components/predefined-scenarios/SC3-ParaSubs.vue"
import Sc4ParaCab from "@/components/predefined-scenarios/Sc4-ParaCab.vue"
import Sc5IndiPara from "@/components/predefined-scenarios/Sc5-IndiPara.vue"
import Inspector from "@/components/scenarioView/Inspector.vue"
import Action from "@/components/scenarioView/Action.vue"
import { mapState, mapMutations } from 'vuex'

export default {
    components: {Sc1SingleCable, Sc2Cable, Sc3ParaSub, Sc4ParaCab, Sc5IndiPara, Inspector, Action},
    props: {
        name: String,
    },
    computed: {
        scenarioId: function() {
            for (var sc of scenarios) {
                if(sc.url === this.name) {
                    return sc.id
                }
            }

            return -1
        },
        title: function() {
            for (var sc of scenarios) {
                if(sc.url === this.name) {
                    return sc.title
                }
            }

            return "Scenario \"" + this.name + "\" does not exist."
        },
        ...mapState({
            inspVisible: state => state.inspVisible,
            selection: state => state.selectedElmt,
            infoOverLayerVis: state => state.infoOverLayerVis
        })
    },
    methods: {
        ...mapMutations(['closeAlertApproximation', 'openAlertApproximation', 'stopApproximation', 'showHideInfoOL'])
    },
}
</script>

<style lang="scss" scoped>
.btn-secondary, .btn-secondary, .btn-primary:visited {
    background-color: lightgray !important;
    border-color: lightgray !important;
}

.btn-secondary.active, .btn-secondary:hover {
    background-color: gray !important;
    border-color: gray !important;
}

.toolbar {
    margin-bottom: 10px;
}

.infoLayer:before {
    font-family: "Font Awesome 5 Free";
    content: "\f5fd";
    font-weight: 900;
}

#container {
    width: 100%;
    margin: auto;
    height: $height-sc-viewer;
}

#action {
    height: $height-sc-viewer;
    width: 19%;
    float: left;
    position: relative;
    background-color: lightgray;
}

#vue {
    height: $height-sc-viewer;
    width: 60%;
    float: left;
}

#blocker {
    position: absolute;
    width: 60%;
    left: 25%;
    height: $height-sc-viewer;
    text-align: left;

    .bigLock {
        font-size: 3em;
        color: red;
    }

    #lockMessage {
        width: 300px;
        text-align: center;
    }

}

#inspector {
    height: $height-sc-viewer;
    width: 19%;
    display: none;
    float: right;
    margin-right: 1%;
    position: relative;

     &.show {
        display: inline;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    }

}

</style>