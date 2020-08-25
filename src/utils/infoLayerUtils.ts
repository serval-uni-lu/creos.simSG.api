import {ULoad} from "@/utils/grid";

export interface ULoadInfo {
    id: number;
    value: string;
    confidence: string;
    y: number;
}

const MARGIN_TOP = 5;
const LINE_HEIGHT = 10;

export function getYText(nbLineInTemplate: number, posElmt: number): number {
    return MARGIN_TOP + posElmt*LINE_HEIGHT;
}

export function layerHeight(nbLineInTemplate: number, nbUloads: number) {
    const realNb = nbUloads===0? 1 : nbUloads;
    return getYText(nbLineInTemplate, nbLineInTemplate + realNb);
}


export function uLoadsData(uloads: Array<ULoad>, nbLineInTemplate: number): Array<ULoadInfo> {
    if(uloads.length === 0) {
        return [{
            id: 0,
            value: "TBD",
            confidence: "TBD",
            y: getYText(nbLineInTemplate, nbLineInTemplate)
        }]
    }

    const result = Array<ULoadInfo>();
    for(let ul=0; ul<uloads.length; ul++) {
        result.push({
            id: ul,
            value: uloads[ul].prettyLoad(),
            confidence: uloads[ul].prettyConf(),
            y: getYText(nbLineInTemplate, nbLineInTemplate + ul)
        });
    }

    return result;
}