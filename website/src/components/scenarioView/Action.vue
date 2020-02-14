<template lang="pug">
    div
        p.title Actions

        //- button(v-if="!isApproximating" type="button" class="btn btn-primary" v-on:click="startApproximation()" v-bind:disabled="noActuators()") Approximate load
        //- button(v-else type="button" class="btn btn-danger" v-on:click="stopApproximation()") <i class="fa fa-spinner fa-spin"></i> Cancel load approximation

        select(v-model="actuator")
            option(selected disabled) {{def_actuator}}
            option(v-for="ac in actuators", v-bind:value="ac") {{ac.name}} - {{ac.author}}

        button(v-for="act in actions" type="button" class="btn btn-primary" v-on:click="startAction(act.name)") {{act.name}}

</template>

<script>
import { mapState, mapMutations } from 'vuex'
import Vue from "vue"

let select_default = "Choose your actuator"

export default {
    data: function() {
        return {
            def_actuator: select_default,
            actuator: select_default,
            actions: []
        }
    },
    watch: {
        actuator: function(newAct, oldAct) {
            this.actions = []
            for(var actIdx in newAct.actions) {
                Vue.set(this.actions, actIdx, newAct.actions[actIdx])
            }          
        }
    },
    computed: {
       ...mapState({
            isApproximating: state => state.isApproximating,
            actuators: state => state.actuators
        })
    },
    methods: {
        // noActuators: function() {
        //     return this.actuators.length === 0;
        // },
        startAction: function(actName) {
            
        }

        // ...mapMutations([])
    }
}
</script>

<style lang="scss" scoped>
$actuator-list-size: 1em;

button {
    display: block;
    margin-left: auto;
    margin-right: auto;
    margin-top: 20px;
    margin-bottom: 20px;
}

select {
  padding: 5px 35px 5px 5px;
  font-size: $actuator-list-size;
  border: 1px solid #ccc;
  height: 34px;
  width: 80%;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  background: url(/upDown.svg) 96% no-repeat #eee;
  background-size: $actuator-list-size;
}

.actuator-select {
    margin: 0;
    margin-right: 20px;
}

</style>