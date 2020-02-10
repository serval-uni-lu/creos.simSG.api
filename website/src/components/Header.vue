<template lang="pug">
    #header 
        #title
            p SimSG : Smart Grid Simulator

        #nav
            ul.separator
                li  <router-link to="/">Home</router-link>
                li  <router-link to="/scenario">Scenarios</router-link>
                    ol.stop
                        <Test v-for="post in scenarios" v-bind:key="post.id" v-bind:title="post.title" v-bind:url="post.url"/>
                li  <router-link to="/scenario-builder">Scenario Builder</router-link>
                li  <router-link to="/lux-sg">Luxembourg Smart Grid</router-link>


        #logos
            img.creos(src="../assets/creos.jpg" alt="logo creos")
            img.serval(src="../assets/serval.svg" alt="logo serval")
  
</template>


<script lang="ts">
// import Vue from 'vue'
import Test from "@/components/Test.vue"

var scenarios = [
                {id: 1, url: "/scenario/myUrl1", title: "My Url 1"}, 
                {id: 2, url: "/scenario/myUrl2", title: "My Url 2"}, 
                {id: 3, url: "/scenario/myUrl3", title: "My Url 3"}
            ];

export default {
    components: {Test},
    data: function() {
        return {
            scenarios: scenarios
        }
    }
}
</script>


<style lang="scss" scoped>
$logo-height: 90px;
$position: right;
$header-color: lightgray;

ul.separator li + li:before {
    content: "|";
    padding: 0 10px;
}

ol.stop li + li:before {
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
    width: 100px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
}

ul li:hover ol {
    display:inline-block;
}

ol > li {
    display: block;
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