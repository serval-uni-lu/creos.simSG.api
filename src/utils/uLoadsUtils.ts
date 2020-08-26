import {ULoad} from "@/utils/grid";
import {getYText} from "@/utils/infoLayerUtils";

export function prettyStr(uLoads: Array<ULoad>): string {
    if(uLoads.length == 0) {
        return "TBD";
    }

    let result = "{";
    for(let ul=0; ul<uLoads.length; ul++) {
        result += "(" + uLoads[ul].prettyLoad() + " [" + uLoads[ul].prettyConf() + "%]";
        if(ul !== uLoads.length - 1) {
            result += ", ";
        }
    }
    result += "}";

    return result;
}

export interface ULoadInfo {
    id: number;
    value: string;
    confidence: string;
    y?: number;
}

export function uLoadsData(uloads: Array<ULoad>): Array<ULoadInfo> {
    if (uloads.length === 0) {
        const res: ULoadInfo = {
            id: 0,
            value: "TBD",
            confidence: "TBD"
        };

        return [res];
    }

    const result = Array<ULoadInfo>();
    for (let ul = 0; ul < uloads.length; ul++) {
        const toAdd: ULoadInfo = {
            id: ul,
            value: uloads[ul].prettyLoad(),
            confidence: uloads[ul].prettyConf()
        };

        result.push(toAdd);
    }

    return result;
}