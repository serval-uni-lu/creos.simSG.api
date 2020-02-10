<template lang="pug">
   <router-link :to="url">{{title}}</router-link>
</template>


<script lang="ts">
export default{
    props: ['title', 'url']
}
</script>