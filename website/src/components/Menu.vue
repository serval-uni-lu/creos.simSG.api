<template>
    <div>
        <div id="nav">
            <router-link to="/">Home</router-link> | 
            <router-link to="/scenario">Scenarios</router-link> | 
            <router-link to="/scenario-builder">Scenario Builder</router-link> | 
            <router-link to="/lux-sg">Luxembourg Smart Grid</router-link> 
        </div>
        <router-view/>
    </div>
</template>


<script lang="ts">
export default {}
</script>

<style lang="scss" scoped>
#nav {
    padding: 30px;

    a {
        font-size: $menu-text-size;

        &.router-link-exact-active {
            color: $menu-selected-color;
            font-weight: bold;
        }
    }
}
</style>