<template lang="pug">
    div
        h2 {{title}}

        section#container
            #empty

            #vue
                Sc1SingleCable(v-if="name === 'sc1-sglCable'")
                Sc2Cable(v-else-if="name === 'sc2-cabinet'")
                h3(v-else) Oups...Component not yet implemented for {{title}}.

            <Inspector id="inspector" :meterId="currentMeterId" v-bind:class="{show: inspVisible}"/>

</template>

<script>
import scenarios from "@/assets/scenarios/scenarios.json"
import Sc1SingleCable from "@/components/predefined-scenarios/Sc1-SingleCable.vue"
import Sc2Cable from "@/components/predefined-scenarios/Sc2-Cabinet.vue"
import Inspector from "@/components/scenarioView/Inspector.vue"
import { mapState } from 'vuex'

export default {
    components: {Sc1SingleCable, Sc2Cable, Inspector},
    props: {
        name: String,
    },
    computed: {
        title: function() {
            for (var sc of scenarios) {
                if(sc.url === this.name) {
                    return sc.title
                }
            }

            return "ERROR"
        },
        ...mapState({
            inspVisible: state => state.inspVisible,
            currentMeterId: state => state.currentMeterId,
        })
    },
}
</script>

<style lang="scss" scoped>
$height: 480px;

#container {
    width: 100%;
    margin: auto;
    height: $height;
}

#empty {
    height: $height;
    width: 19%;
    float: left;
}

#vue {
    height: $height;
    width: 60%;
    float: left;

     svg {
        height: $height;
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