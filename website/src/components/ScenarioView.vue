<template lang="pug">
    div
        h2 {{title}}

        section#container
            Action(id="action" :scenarioID="scenarioId")

            #vue
                Sc1SingleCable(v-if="name === 'sc1-sglCable'")
                Sc2Cable(v-else-if="name === 'sc2-cabinet'")
                Sc3ParaSub(v-else-if="name === 'sc3-para-transfo'")
                Sc4ParaCab(v-else-if="name === 'sc4-para-cabinet'")
                Sc5IndiPara(v-else-if="name === 'sc5-indirect-para'")
                h3(v-else) Oups...Component not yet implemented.

            #blocker(v-show="isApproximating")
                <i class="bigLock fas fa-lock" v-show="showIconLock()" v-on:click="openAlertApproximation()"></i>
                #lockMessage(class="alert alert-info alert-dismissible fade show" role="alert" v-show="showAlert()")
                    | <i class="fas fa-lock"></i> The load is being approximated. Grid cannot be modified. 
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close" v-on:click="closeAlertApproximation()">
                        <span aria-hidden="true">&times;</span>
                    </button>
            
                                    

            <Inspector id="inspector" :meterId="currentMeterId" v-bind:class="{show: inspVisible}"/>

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
            currentMeterId: state => state.currentMeterId,
            isApproximating: state => state.isApproximating,
            showAlertApprox: state => state.showAlertApprox
        })
    },
    methods: {
        showAlert: function() {
            return this.isApproximating && this.showAlertApprox;
        },
        showIconLock: function() {
            return !this.showAlert();
        },
        confirmLeave(next) {
            if(this.isApproximating) {
                var ok = confirm("Leaving this page will cancel the load approximation. Are you sure you want to quit?");
                if(ok) {
                    this.stopApproximation();
                    next();
                }
            } else {
                next();
            }
        },
        ...mapMutations(['closeAlertApproximation', 'openAlertApproximation', 'stopApproximation'])
    },
    beforeRouteLeave(to, from, next) {
        this.confirmLeave(next);
    },
    beforeRouteUpdate(to, from, next) {
        this.confirmLeave(next);
    }
}
</script>

<style lang="scss" scoped>
$height: 480px;

#container {
    width: 100%;
    margin: auto;
    height: $height;
}

#action {
    height: $height;
    width: 19%;
    float: left;
    position: relative;
    background-color: lightgray;
}

#vue {
    height: $height;
    width: 60%;
    float: left;

     svg {
        height: $height;
    }
}

#blocker {
    position: absolute;
    width: 60%;
    left: 25%;
    height: $height;
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
    height: $height;
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