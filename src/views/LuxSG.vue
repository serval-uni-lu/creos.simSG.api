<template lang="pug">
    section
        h2 Real-case scenario: Reckange disctrict (Mersch, Luxembourg)

        p Current: {{lat}} - {{long}} - {{zoom}}

        .container
            Action#action
            #lg-map(v-on:click="update()")

</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import L, {TileLayer} from 'leaflet';
    import Action from "@/components/Action.vue";
    @Component({
        components: {Action}
    })
    export default class LuxSG extends Vue {
        public map!: L.Map;
        public tileLayers!: TileLayer;
        public layers!: Array<object>;

        public lat =  49.749219791749525;
        public long= 6.08051569442007 ;
        public zoom= 16;

        public update() {
            this.lat = this.map.getCenter().lat;
            this.long = this.map.getCenter().lng;
            this.zoom = this.map.getZoom();
        }

        public mounted() {
            this.map = L.map("lg-map").setView([this.lat,this.long], this.zoom);

            this.tileLayers = L.tileLayer('https://cartodb-basemaps-{s}.global.ssl.fastly.net/rastertiles/voyager/{z}/{x}/{y}.png',
                {
                    maxZoom: 18,
                    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>, &copy; <a href="https://carto.com/attribution">CARTO</a>',
                });

            this.tileLayers.addTo(this.map);

            const marker = L.marker([this.lat, this.long]).addTo(this.map);
        }

    }
</script>

<style lang="scss" scoped>
    $size-side-elmt: 19%;
    $margin: 1%;
    $remaining: calc(100% - (#{$margin} + #{$size-side-elmt}) * 1);
    $margin-bottom: 10px;
    $color: lightgrey;

    section {
        text-align: center;
        display: flex;
        flex-direction: column;
    }

    .container {
        flex: 1;
        display: flex;
        flex-direction: row;
    }


    #action {
        width: $size-side-elmt;
        margin-left: $margin;
        margin-bottom: $margin-bottom;
        background-color: $color;
    }

    #lg-map {
        width: $remaining;
        margin: 0 $margin $margin;
    }

</style>