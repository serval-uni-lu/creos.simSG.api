<template lang="pug">
    h2 Let's play with {{$route.params.name}}
</template>

<script lang="ts">
export default {
    name: 'scenario',
    props: ['name']
}
</script>