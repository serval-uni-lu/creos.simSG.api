<template lang="pug">
    div
        ToolBar
        div(v-on:mousedown="startDrag($event)", v-on:mousemove="drag($event)", v-on:mouseup="stopDrag()", v-on:mouseleave="stopDrag()")
            SingleCable(v-if="name === 'sc1-sglCable'")
            h3(v-else) Oups... Component not yet implemented for {{name}}
</template>

<script lang="ts">
    import {Component,Vue, Prop} from "vue-property-decorator";
    import ToolBar from "@/components/scView/scviewer/ToolBar.vue";
    import SingleCable from "@/components/scView/scviewer/scenarios/SingleCable.vue";
    import {Point} from "@/utils/SvgTypes";

    @Component({
        components: {SingleCable, ToolBar}
    })
    export default class SCViewer extends Vue {
        @Prop() name!: string;

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
                console.log("oups...");
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
            console.log("it happens...")
            return {x: 0, y: 0};
        }

        public startDrag(evt: MouseEvent): void {
            let src= evt.target as SVGElement | HTMLElement | null | undefined;
            while (!src?.classList.contains("infoBox") && src?.id !== "vue") {
                src = src?.parentElement;
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
    }
</script>

<style lang="scss">
    svg {
        width: 80%; //todo: to be fixed... https://css-tricks.com/scale-svg/
    }

</style>