<template lang="pug">
    div
        ToolBar
        #sccanvas(v-on:mousedown="startDrag($event)", v-on:mousemove="drag($event)", v-on:mouseup="stopDrag()", v-on:mouseleave="stopDrag()")
            SingleCable(v-if="name === 'sc1-sglCable'")
            Cabinet(v-else-if="name === 'sc2-cabinet'")
            ParaSubs(v-else-if="name === 'sc3-para-transfo'")
            ParaCabinet(v-else-if="name === 'sc4-para-cabinet'")
            IndirectPara(v-else-if="name === 'sc5-indirect-para'")
            h3(v-else) Oups... Component not yet implemented for {{name}}
</template>

<script lang="ts">
    import {Component, Vue, Prop, Watch} from "vue-property-decorator";
    import ToolBar from "@/components/scView/scviewer/ToolBar.vue";
    import SingleCable from "@/components/scView/scviewer/scenarios/SingleCable.vue";
    import Cabinet from "@/components/scView/scviewer/scenarios/Cabinet.vue";
    import {Point} from "@/utils/SvgTypes";
    import {namespace} from "vuex-class";
    import ParaSubs from "@/components/scView/scviewer/scenarios/ParaSubs.vue";
    import ParaCabinet from "@/components/scView/scviewer/scenarios/ParaCabinet.vue";
    import IndirectPara from "@/components/scView/scviewer/scenarios/IndirectPara.vue";

    const inspectorState = namespace('InspectorState');

    @Component({
        components: {IndirectPara, ParaCabinet, ParaSubs, Cabinet, SingleCable, ToolBar}
    })
    export default class SCViewer extends Vue {
        @Prop() name!: string;

        @inspectorState.Mutation
        public reset!: () => void;

        public elmtDragged!: SVGElement | null;
        public offsetDrag!: Point;

        public getMousePosition(evt: MouseEvent, element: SVGElement): Point {
            let svgHtml = element as unknown as HTMLElement | null | undefined;
            while (svgHtml?.tagName !== "svg") {
                svgHtml = svgHtml?.parentElement;
            }

            const svg = svgHtml as unknown as SVGGraphicsElement;
            const CTM = svg.getScreenCTM();

            if(CTM !== null) {
                return {
                    x: (evt.clientX - CTM.e) / CTM.a,
                    y: (evt.clientY - CTM.f) / CTM.d
                };
            } else {
                return {x: 0, y: 0}
            }

        }

        public getTransform(): Point {
            if (this.elmtDragged !== null) {

                const transStr: string | null = this.elmtDragged.getAttributeNS(null, "transform");
                const idxPar = transStr?.indexOf("(");
                const idxEndPar = transStr?.indexOf(")");
                const idxSpace = transStr?.indexOf(" ");

                if (idxPar !== undefined && idxSpace !== undefined && idxEndPar !== undefined) {
                    const xString = transStr?.slice(idxPar + 1, idxSpace).trim();
                    const yString = transStr?.slice(idxSpace + 1, idxEndPar).trim();

                    return {
                        x: Number(xString),
                        y: Number(yString)
                    }
                }
            }
            //should never happen
            return {x: 0, y: 0};
        }

        public startDrag(evt: MouseEvent): void {
            let src = evt.target as SVGElement;
            while(!(src.classList.contains("infoBox")) && src.id !== "sccanvas") {
                src = src.parentNode as SVGElement;
            }

            if(src.classList.contains("infoBox")) {
                this.elmtDragged = src as SVGElement;
                const mousePosition: Point = this.getMousePosition(evt, this.elmtDragged);
                const position: Point = this.getTransform();

                mousePosition.x -= position.x;
                mousePosition.y -= position.y;

                this.offsetDrag = mousePosition;
            }
        }

        public toSvgTrans(position: Point): string {
            return "translate(" + position.x + " " + position.y + ")";
        }


        public drag(evt: MouseEvent): void {
            if(this.elmtDragged) {
                const position = this.getTransform();

                evt.preventDefault();
                const mouseCoord = this.getMousePosition(evt, this.elmtDragged);
                position.x = mouseCoord.x - this.offsetDrag.x;
                position.y = mouseCoord.y - this.offsetDrag.y;

                this.elmtDragged.setAttributeNS(null, "transform", this.toSvgTrans(position))
            }
        }

        public stopDrag(): void {
            this.elmtDragged = null;
        }

        @Watch('$route')
        onRouteChanged() {
            this.reset();
        }
    }
</script>

<style lang="scss">
    svg {
        width: 80%; //todo: to be fixed... https://css-tricks.com/scale-svg/
    }

</style>