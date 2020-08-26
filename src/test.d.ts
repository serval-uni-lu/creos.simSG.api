import {ActionData} from "@/utils/actionUtils";

declare module "vue/types/vue" {
    interface Vue {
        $actionCmp: Array<ActionData>;
    }
}

export {};
