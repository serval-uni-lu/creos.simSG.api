<template lang="pug">
    div
        h2 Actions
        select(v-model="currentAct")
            option(selected disabled :value="nullValue") {{nullValue.name}}
            option(v-for="ac in $actionCmp", :key="ac.id", :value="ac") {{ac.name}}

        div(v-if="selectionDone")
            component(v-bind:is="currentAct.name")

</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import {ActionData, actionDataIsNotNull, NULL_ACTION} from "@/utils/actionUtils";

    @Component
    export default class Action extends Vue{

        public currentAct: ActionData = NULL_ACTION;
        readonly nullValue: ActionData = NULL_ACTION;

        get selectionDone(): boolean {
            return actionDataIsNotNull(this.currentAct);
        }

    }
</script>

<style scoped lang="scss">
    $actuator-list-size: 1em;

    select {
        margin-bottom: 20px;
        padding: 5px 35px 5px 5px;
        font-size: $actuator-list-size;
        border: 1px solid #ccc;
        height: 34px;
        width: 80%;
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        background: url("../assets/buttons/upDown.svg") 96% no-repeat #eee;
        background-size: $actuator-list-size;
    }
</style>