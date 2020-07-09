<template lang="pug">
   header#page-header
        h1 SimSG: Smart Grid Simulator

        #logos
            img.creos(src="@/assets/logos/creos.jpg" alt="logo creos")
            img.serval(src="@/assets/logos/serval.svg" alt="logo serval")

</template>

<script lang="ts">
    import { Component, Vue } from 'vue-property-decorator';

    @Component
    export default class Header extends Vue {}
</script>

<style lang="scss" scoped>
    $logo-height: 90px;
    $position: right;
    $text-color: #034164;

    #page-header {
        background-color: lightgray;
        display: flex;
        justify-content: center;
        align-items: center;

        h1 {
            margin: 0;
            text-align: center;
            font-size: 3em;
            color: $text-color;
        }
    }

    #logos {
        position: absolute;
        top: 0;
        right: 10px;
        float: $position;
        background-color: white;
        border-radius: 0 0 8px 8px;

        .creos {
            padding-right: 10px;
            padding-left: 5px;
            height: $logo-height;
        }

        .serval {
            height: $logo-height;
        }

    }
</style>