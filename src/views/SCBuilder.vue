<template lang="pug">
    h2 #[strong TODO] Vue of a scenario builder
</template>


<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";

    @Component
    export default class SCBuilder extends Vue {
    }
</script>

<style lang="scss">
    h2 {
        text-align: center;
    }
</style>