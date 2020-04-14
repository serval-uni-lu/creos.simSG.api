<template>
    <g class="hidden" v-on:click="showInspector();">
        <title>Load: {{load()}}</title>
        <line :x1=line.x1 :y1=line.y :x2=line.x2 :y2=line.y stroke-linecap="round" stroke-linejoin="round" />
    </g>
</template>

<script>
import { mapState } from 'vuex'

export default {
    props: {
        id: Number,
        line: Object,
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