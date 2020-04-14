<template>
    <g class="hidden" v-on:click="showInspector();">
        <title>Load: {{load()}}</title>
        <line :x1=line1.x1 :y1=line1.y1 :x2=line1.x2 :y2=line1.y2 stroke-linecap="round" stroke-linejoin="round"/>
        <line :x1=line2.x1 :y1=line2.y1 :x2=line2.x2 :y2=line2.y2 stroke-linecap="round" stroke-linejoin="round"/>
    </g>   
</template>

<script>
import { mapState } from 'vuex'

export default {
    props: {
        id: Number,
        line1: Object,
        line2: Object,
    },
    computed: {
        ...mapState({
            loads: state => state.cableLoads,
        })
    },
    methods: {
        load: function() {
            var p_load = this.loads[this.id];
            return (p_load === undefined || p_load === -1)? "To be computed..." : p_load.toFixed(2) + " A";
        },
        showInspector: function() {
            let info = {
                elemtId: this.id,
                elemtType: "cable"
            }
            this.$store.commit('showInspector', info)
        },
    }
}
</script>

<style lang="scss" scoped>
@import "@/scss/cable.scss";
@import "@/scss/infoBox.scss";
</style>