<template lang="pug">
  .home
    h2 Overview
    p.
      SimGrid is an open-source project developed in collaboration between #[a(href="https://www.creos-net.lu") Creos S.A.]
      and #[a(href="https://wwwen.uni.lu/snt/research/serval") SerVal (SnT/Uni.lu)]. It simulates simple grid, with a high-level
      abstraction (we do not deal with all complexity related to electrical engineering). We use it to investigate different challenges
      in the SmartGrid domain, listed below.
    h2 Investigation direction
    ul
      li Propagation of fuse uncertainty to approximated cable and fuse load #[em (by L. Mouline)]
    h2 Code repositories
    ul
      li #[a(href="https://bitbucket.org/sntcreos/simsg/src/master/") bitbucket.org/sntcreos/simsg] #[em (Frontend)]
      li #[a(href="https://bitbucket.org/sntcreos/ducpropagationjava/src/master/") bitbucket.org/sntcreos/ducpropagationjava] #[em (Backend)]
    h2 Authors
    ul
      li Project Manager: Yves Le Traon, Maxime Cordy
      li Investigator & Developer: Ludovic Mouline
      li Creos' contact: Yves Reckinger, Robert Graglia

</template>

<script lang="ts">
  import { Component, Vue} from "vue-property-decorator";

  @Component
  export default class Home extends Vue {

  }

</script>

<style scoped lang="scss">
  .home {
    text-align: center;
    padding: 0 5% 0 5%;
  }
</style>
