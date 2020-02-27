<template lang="pug">
    .infoBox 
        <InspCable :cableId=id />
</template>

<script>
import InspCable from "@/components/scenarioView/attrViews/InspCable.vue"

export default {
    components: {InspCable},
    props: {
        id: Number,
        position: Object
    }
}
</script>

<style lang="scss" scoped>
.infoBox {
    background-color: white;
    border-radius: 8px;
    border: 1px black;
    position: absolute;
    top: 292px;
}
</style>