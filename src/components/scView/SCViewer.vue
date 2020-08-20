<template lang="pug">
    div
        .toolbar
            button.infoLayer(title="Add a layer with all information" type="button" class="btn btn-secondary")
            img.cblFuseLayer(src="@/assets/buttons/infoLayerCable.svg" title="Add a layer with cables information" class="btn btn-secondary")
            img.cblFuseLayer(src="@/assets/buttons/infoLayerFuse.svg" title="Add a layer with fuses information" class="btn btn-secondary")
</template>

<script lang="ts">
    import {Component,Vue} from "vue-property-decorator";

    @Component
    export default class SCViewer extends Vue {

    }
</script>

<style scoped lang="scss">
    .btn-secondary, .btn-secondary, .btn-primary:visited {
        background-color: lightgray !important;
        border-color: lightgray !important;
    }

    .btn-secondary.active, .btn-secondary:hover {
        background-color: gray !important;
        border-color: gray !important;
    }

    .toolbar {
        margin-bottom: 10px;
    }

    .infoLayer {
        margin-left: 3px;
        margin-right: 3px;
    }

    .infoLayer:before {
        font-family: "Font Awesome 5 Free",sans-serif;
        content: "\f5fd";
        font-weight: 900;
    }

    .cblFuseLayer {
        margin-left: 3px;
        margin-right: 3px;
        width:  41.1px;
        height: 37.9333px;
    }

</style>