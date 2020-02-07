<template>
    <div id="header">
        <h1>SimSG : Smart Grid Simulator</h1>

        <div class="logo">
            <img class="serval" src="../assets/serval.svg" alt="logo serval">
            <img class="creos" src="../assets/creos.jpg" alt="logo creos">
        </div>
    </div>
</template>


<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Header extends Vue {
    
}
</script>


<style lang="scss">
.logo {
    position: absolute;
    top: 10px;
    width: 100%;
    height: 20px;
    

    .creos {
        width:7%;
        float: right;
        padding-right: 10px;
    }

    .serval {
        width:4%;
        float: right;
        padding-right: 20px;
    }

}



</style>