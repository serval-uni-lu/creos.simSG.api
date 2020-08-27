// eslint-disable-next-line
import Vue from "vue";
import ULoadApproxVue from "@/plugin/action/uLoadApprox/ULoadApproxVue.vue";

export default new (class ULoadApprox {
    private readonly compoName = "ULoadApproxVue";

    public install() {
        Vue.prototype.$actionCmp.push({id: Vue.prototype.$actionCmp.length, name: this.compoName});
        Vue.component(this.compoName, ULoadApproxVue)
    }
})