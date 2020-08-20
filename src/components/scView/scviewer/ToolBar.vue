<template lang="pug">
    .toolbar
        img.cblFuseLayer(src="@/assets/buttons/infoLayerAll.svg" title="Add a layer with all information" class="btn btn-secondary")
        img.cblFuseLayer(src="@/assets/buttons/infoLayerCable.svg" title="Add a layer with cables information" class="btn btn-secondary")
        img.cblFuseLayer(src="@/assets/buttons/infoLayerFuse.svg" title="Add a layer with fuses information" class="btn btn-secondary")
</template>

<script lang="ts">
    import {Vue, Component} from "vue-property-decorator";

    @Component
    export default class ToolBar extends Vue{
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
        width:  40px;
        height: 40px;
        padding: 3px;
    }
</style>