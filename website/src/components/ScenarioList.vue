<template lang="pug">
   li <router-link :to="url">{{name}}</router-link>
</template>


<script lang="ts">
export default{
    props: ['name', 'url']
}
</script>