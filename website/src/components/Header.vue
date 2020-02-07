<template lang="pug">
    div 
        #header
            p.title SimSG : Smart Grid Simulator

        #logos
            img.creos(src="../assets/creos.jpg" alt="logo creos")
            img.serval(src="../assets/serval.svg" alt="logo serval")
        
</template>


<script lang="ts">
export default {}
</script>


<style lang="scss">
$creos-logo-height: 67px;
$snt-logo-height: 97px;
$creos-logo-top: ($snt-logo-height - $creos-logo-height)/2;
$position: right;

#header {
    position: relative;
    padding-top: 30px;

    p.title {
        display: inline;
        font-size: $header-title-size; 
        font-weight: bold;
    }
}

#logos {
    position: absolute;
    top: 10px;
    right: 60px;
    float: $position;

    .creos {
            height: $creos-logo-height;
            padding: $creos-logo-top 10px;
    }

    .serval {
        height: $snt-logo-height;
    }

}
</style>