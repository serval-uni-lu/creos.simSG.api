<template lang="pug">
    div
        ToolBar
</template>

<script lang="ts">
    import {Component,Vue} from "vue-property-decorator";
    import ToolBar from "@/components/scView/scviewer/ToolBar.vue";

    @Component({
        components: {ToolBar}
    })
    export default class SCViewer extends Vue {

    }
</script>

<style scoped lang="scss">


</style>