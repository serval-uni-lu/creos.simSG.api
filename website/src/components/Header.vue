<template lang="pug">
    #header 
        #title
            p SimSG : Smart Grid Simulator

        #nav
            ul.separator
                li  <router-link to="/">Home</router-link>
                li  <router-link to="/scenario">Scenarios</router-link>
                    ul.stop
                        li Scenario 1 - Test
                        li Scenario 1 - Test
                        li Scenario 1 - Test
                li  <router-link to="/scenario-builder">Scenario Builder</router-link>
                li  <router-link to="/lux-sg">Luxembourg Smart Grid</router-link>


        #logos
            img.creos(src="../assets/creos.jpg" alt="logo creos")
            img.serval(src="../assets/serval.svg" alt="logo serval")
  
</template>


<script lang="ts">
export default {}
</script>


<style lang="scss" scoped>
$logo-height: 90px;
$position: right;
$header-color: lightgray;

ul.separator li + li:before {
    content: "|";
    padding: 0 10px;
}

ul.stop li + li:before {
    content: "";
    padding: 0px;
}

ul li {
    position: relative;
    display: inline-block;
}

ul a {
    display: inline-block;
}


ul li ol {
    padding: 0;
    position: absolute;
    top: 100%;
    display:none;
    background-color: $header-color;
    left: 10px;
    width: 150px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
}

ul li:hover ol {
    display:inline-block;
}

#header {
    margin-bottom: 50px;
    background-color: $header-color;
    padding-bottom: 20px;
}

#title {
    position: relative;
    padding-top: 30px;

    p {
        display: inline;
        font-size: $header-title-size; 
        font-weight: bold;
    }
}

#logos {
    position: absolute;
    top: 0px;
    right: 10px;
    float: $position;
    background-color: white;
    border-radius: 0px 0px 8px 8px;

    .creos {
        padding-right: 10px;
        padding-left: 5px;
        height: $logo-height;
    }

    .serval {
        height: $logo-height;
    }

}

#nav {
    a {
        font-size: $menu-text-size;

        &.router-link-exact-active {
            color: $menu-selected-color;
            font-weight: bold;
        }
    }
}
</style>