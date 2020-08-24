import {Cable} from "@/utils/grid";

export function uLoadsStr(cable: Cable): string {
    const uloads = cable.uLoads;
    if(uloads.length == 0) {
        return "TBD";
    }

    let result = "{";
    for(let ul=0; ul<uloads.length; ul++) {
        result += "(" + uloads[ul].prettyLoad() + " [" + uloads[ul].prettyConf() + "%]";
        if(ul !== uloads.length - 1) {
            result += ", ";
        }
    }
    result += "}";

    return result;
}