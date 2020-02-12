<template lang="pug">
    div
        p.title Inspector Meter - {{meterId + 1}}
        .form 
            | Consumption:
            input(type="number", min="0", v-model.number="consumptions[meterId]")
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
import Vue from "vue"
import { mapState, mapMutations } from 'vuex'

export default {
    props: {
        meterId: Number,
    }, 
    computed: {
        ...mapState({
            consumptions: state => state.consumptions,
            inspVisible: state => state.inspVisible,
        })
    },
    methods: {
        ...mapMutations(['hideInspector'])
    }
}

</script>


<style lang="scss" scoped>
.form {
    font-size: $inspector-font-size;
    text-align: left;
    padding-left: 10px;
    input {
        width: 15%;
        margin-left: 20px; 
    }
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