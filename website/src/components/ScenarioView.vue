<template lang="pug">
    div
        h2 {{title}}

        Sc1SingleCable(v-if="name === 'sc1-sglCable'")
        Sc2Cable(v-else-if="name === 'sc2-cabinet'")
        h3(v-else) Oups...Component not yet implemented for {{title}}.

</template>

<script>
import scenarios from "@/assets/scenarios/scenarios.json"
import Sc1SingleCable from "@/components/predefined-scenarios/Sc1-SingleCable.vue"
import Sc2Cable from "@/components/predefined-scenarios/Sc2-Cabinet.vue"

export default {
    components: {Sc1SingleCable, Sc2Cable},
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
        }
    },
}
</script>