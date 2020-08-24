import {ULoad} from "@/utils/grid";

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