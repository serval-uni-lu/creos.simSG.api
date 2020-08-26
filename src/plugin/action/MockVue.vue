<template lang="pug">
    p I am dynamically added!
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";

    @Component
    export default class MockVue extends Vue{
    }
</script>

<style scoped>

</style>