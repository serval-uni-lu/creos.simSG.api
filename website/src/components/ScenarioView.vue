<template lang="pug">
    div
        h2 {{title}}

        .toolbar
            button.infoLayer(title="Add a layer with all information" type="button" class="btn btn-secondary" v-bind:class="{active: infoOverLayerVis}" v-on:click="showHideInfoOL()")
            img.cableLayer(src="@/assets/infoLayerCable.svg" title="Add a layer with cables information" type="button" class="btn btn-secondary" v-bind:class="{active: infoCblVis}" v-on:click="showHideInfoCable()" )
            img.cableLayer(src="@/assets/infoLayerFuse.svg" title="Add a layer with fuses information" type="button" class="btn btn-secondary" v-bind:class="{active: infoFuseVis}" v-on:click="showHideInfoFuse()")

        section#container
            Action(id="action" :scenarioID="scenarioId")

            #vue(v-on:mousedown="startDrag($event)", v-on:mousemove="drag($event)", v-on:mouseup="stopDrag($event)", v-on:mouseleave="stopDrag($event)")
                Sc1SingleCable(v-if="name === 'sc1-sglCable'")
                Sc2Cable(v-else-if="name === 'sc2-cabinet'")
                Sc3ParaSub(v-else-if="name === 'sc3-para-transfo'")
                Sc4ParaCab(v-else-if="name === 'sc4-para-cabinet'")
                Sc5IndiPara(v-else-if="name === 'sc5-indirect-para'")
                h3(v-else) Oups...Component not yet implemented.

            <Inspector id="inspector" :elmtId="selection.id" :elmtType="selection.type" v-bind:class="{show: inspVisible}"/>

</template>

<script>
import scenarios from "@/assets/scenarios/scenarios.json"
import Sc1SingleCable from "@/components/predefined-scenarios/Sc1-SingleCable.vue"
import Sc2Cable from "@/components/predefined-scenarios/Sc2-Cabinet.vue"
import Sc3ParaSub from "@/components/predefined-scenarios/SC3-ParaSubs.vue"
import Sc4ParaCab from "@/components/predefined-scenarios/Sc4-ParaCab.vue"
import Sc5IndiPara from "@/components/predefined-scenarios/Sc5-IndiPara.vue"
import Inspector from "@/components/scenarioView/Inspector.vue"
import Action from "@/components/scenarioView/Action.vue"
import { mapState, mapMutations } from 'vuex'

export default {
    components: {Sc1SingleCable, Sc2Cable, Sc3ParaSub, Sc4ParaCab, Sc5IndiPara, Inspector, Action},
    props: {
        name: String,
    },
    data: function() {
        return {
            elmtDragged: false,
            offSetDrag: {},
        }
    },
    computed: {
        scenarioId: function() {
            for (var sc of scenarios) {
                if(sc.url === this.name) {
                    return sc.id
                }
            }

            return -1
        },
        title: function() {
            for (var sc of scenarios) {
                if(sc.url === this.name) {
                    return sc.title
                }
            }

            return "Scenario \"" + this.name + "\" does not exist."
        },
        infoOverLayerVis: function() {
            return this.infoCblVis && this.infoFuseVis;
        },
        ...mapState({
            inspVisible: state => state.inspVisible,
            selection: state => state.selectedElmt,
            // infoOverLayerVis: state => state.infoOverLayerVis
            infoCblVis: state => state.infoCblVis,
            infoFuseVis: state => state.infoFuseVis
        })
    },
    methods: {
        getTransform: function() {
            var transStr = this.elmtDragged.getAttributeNS(null, "transform");
            var idxParen = transStr.indexOf("(");
            var idxEndPar = transStr.indexOf(")");
            var idxSpace = transStr.indexOf(" ");

            var xString = transStr.slice(idxParen + 1, idxSpace).trim();
            var yString = transStr.slice(idxSpace + 1, idxEndPar).trim();

            return {x: Number(xString), y: Number(yString)};

        },
        toSvgTransf: function(position) {
            return "translate(" + position.x + " " + position.y + ")";
        },
        getMousePosition(evt, srcElmt) {
            var svg = srcElmt;
            while(svg.tagName !== "svg") {
                svg = svg.parentNode;
            }

            var CTM = svg.getScreenCTM();
            return {
                x: (evt.clientX - CTM.e) / CTM.a,
                y: (evt.clientY - CTM.f) / CTM.d
            }
        },
        startDrag: function(evt) {
            var src = evt.target;
            while(!src.classList.contains("infoBox") && src.id !== "vue") {
                src = src.parentNode;
            }

            if(src.classList.contains("infoBox")) {
                this.elmtDragged = src;
                var mousePosition = this.getMousePosition(evt, this.elmtDragged);
                var position = this.getTransform();

                mousePosition.x -= position.x;
                mousePosition.y -= position.y;

                this.offSetDrag = mousePosition;

            }

        },
        drag: function(evt) {
            if(this.elmtDragged) {
                var position = this.getTransform();
                
                evt.preventDefault();
                var mouseCoord = this.getMousePosition(evt, this.elmtDragged);
                position.x = mouseCoord.x - this.offSetDrag.x;
                position.y = mouseCoord.y - this.offSetDrag.y;

                this.elmtDragged.setAttributeNS(null, "transform", this.toSvgTransf(position))
            }
        },
        stopDrag: function(evt) {
            this.elmtDragged = null;
        },
        ...mapMutations(['closeAlertApproximation', 'openAlertApproximation', 'stopApproximation', 'showHideInfoOL', 'showHideInfoCable', 'showHideInfoFuse'])
    },
}
</script>

<style lang="scss" scoped>
.btn-secondary, .btn-secondary, .btn-primary:visited {
    background-color: lightgray !important;
    border-color: lightgray !important;
}

.btn-secondary.active, .btn-secondary:hover {
    background-color: gray !important;
    border-color: gray !important;
}

.toolbar {
    margin-bottom: 10px;
}

.infoLayer {
    margin-left: 3px;
    margin-right: 3px;
}

.infoLayer:before {
    font-family: "Font Awesome 5 Free";
    content: "\f5fd";
    font-weight: 900;
}

.cableLayer {
    margin-left: 3px;
    margin-right: 3px;
    width:  41.1px;
    height: 37.9333px;
}

#container {
    width: 100%;
    margin: auto;
    height: $height-sc-viewer;
}

#action {
    height: inherit;
    width: 19%;
    float: left;
    position: relative;
    background-color: lightgray;
}

#vue {
    height: inherit;
    width: 60%;
    float: left;

    svg {
        height: 100%;
        width: 100%;
        position: relative;
    }
}

#inspector {
    height: inherit;
    width: 19%;
    display: none;
    float: right;
    margin-right: 1%;
    position: relative;

     &.show {
        display: inline;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    }

}

</style>