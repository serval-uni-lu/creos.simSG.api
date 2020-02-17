<template lang="pug">
    div
        p.title {{elmtTypeFormatted}} - {{elmtId + 1}}
        .form 
            <InspMeter v-if="elmtType === 'meter'" :meterId="elmtId"/>
            <InspFuse v-if="elmtType === 'fuse'" :fuseId="elmtId"/>
            <InspCable v-if="elmtType === 'cable'" :cableId="elmtId"/>
        .closingButton(v-on:click="hideInspector()")
            <svg viewBox="871 749 32 32">
                <defs/>
                <g fill="white" stroke-dasharray="none" stroke="none" stroke-opacity="1" fill-opacity="1">
                    <line x1="871" y1="749" x2="902.852" y2="780.6595" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                    <line x1="903" y1="748.9037" x2="871.3405" y2="780.7558" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
                </g>
            </svg>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import InspMeter from '@/components/scenarioView/attrViews/InspMeter.vue'
import InspFuse from '@/components/scenarioView/attrViews/InspFuse.vue'
import InspCable from '@/components/scenarioView/attrViews/InspCable.vue'

export default {
    props: {
        elmtId: Number,
        elmtType: {
            type: String,
            validator: function(value) {
                return ['meter', 'fuse', 'cable', 'null'].indexOf(value) !== -1
            }
        }
    }, 
    computed: {
        elmtTypeFormatted: function() {
            return this.elmtType.charAt(0).toUpperCase() + this.elmtType.slice(1)
        },
        ...mapState({
            inspVisible: state => state.inspVisible,
        })
    },
    methods: {
        ...mapMutations(['hideInspector'])
    },
    components: {InspMeter, InspFuse, InspCable}
}

</script>


<style lang="scss" scoped>
.form {
    font-size: $inspector-font-size;
    text-align: left;
    padding-left: 10px;
}

.closingButton {
    position: absolute;
    top: 5px;
    right: 5px;

    svg {
        width: 20px;

        line {
            stroke: lightgray;
        }

        &:hover {
            line {
                stroke: black;
            }
        }
    }
}
</style>