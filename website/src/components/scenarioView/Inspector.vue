<template lang="pug">
    div
        p.title {{elmtTypeFormatted}} - {{elmtId + 1}}
        .form 
            <InspMeter v-if="elmtType === 'meter'" :meterId="elmtId"/>
            <InspFuse v-if="elmtType === 'fuse'" :fuseId="elmtId"/>
            <InspCable v-if="elmtType === 'cable'" :cableId="elmtId"/>
        .closingButton(v-on:click="hideInspector()")
            <svg >
                <use xlink:href="#close-button"></use>
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
        height: 20px;
        stroke: lightgray;
        stroke-width: 2;

        &:hover {
            stroke: black;
        }

    }
}
</style>