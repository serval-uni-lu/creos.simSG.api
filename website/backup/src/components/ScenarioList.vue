<template lang="pug">
   li <router-link :to="fullUrl">{{title}}</router-link>
</template>


<script>
export default {
    props: ['title', 'url'],
    computed: {
        fullUrl: function() {
            return "/scenario/" + this.url
        }
    }
}
</script>