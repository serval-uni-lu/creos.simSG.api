import {ULoad} from "@/utils/grid";
import {ULoadInfo, uLoadsData} from "@/utils/uLoadsUtils";

const MARGIN_TOP = 5;
const LINE_HEIGHT = 10;

export function getYText(nbLineInTemplate: number, posElmt: number): number {
    return MARGIN_TOP + posElmt*LINE_HEIGHT;
}

export function layerHeight(nbLineInTemplate: number, nbUloads: number) {
    const realNb = nbUloads===0? 1 : nbUloads;
    return getYText(nbLineInTemplate, nbLineInTemplate + realNb);
}

export function uLoadsDataWithY(uloads: Array<ULoad>, nbLineInTemplate: number): Array<ULoadInfo> {
    const res = uLoadsData(uloads);
    res.forEach((value, index) => {
        value.y = getYText(nbLineInTemplate, nbLineInTemplate + index);
    });
    return res;


    // if (uloads.length === 0) {
    //     const res: ULoadInfo = {
    //         id: 0,
    //         value: "TBD",
    //         confidence: "TBD"
    //     };
    //
    //     if (nbLineInTemplate !== undefined) {
    //         res.y = getYText(nbLineInTemplate, nbLineInTemplate);
    //     }
    //
    //     return [res];
    // }
    //
    // const result = Array<ULoadInfo>();
    // for (let ul = 0; ul < uloads.length; ul++) {
    //     const toAdd: ULoadInfo = {
    //         id: ul,
    //         value: uloads[ul].prettyLoad(),
    //         confidence: uloads[ul].prettyConf()
    //     };
    //
    //     if (nbLineInTemplate !== undefined) {
    //         toAdd.y = getYText(nbLineInTemplate, nbLineInTemplate + ul);
    //     }
    //
    //     result.push(toAdd);
    // }
    //
    // return result;
}


