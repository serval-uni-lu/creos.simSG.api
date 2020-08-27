// eslint-disable-next-line
import Vue from "vue";
import LoadApproxVue from "@/plugin/action/loadApprox/LoadApproxVue.vue";

export default new (class LoadApprox {
    private readonly compoName = "LoadApproxVue";

    public install() {
        Vue.prototype.$actionCmp.push({id: Vue.prototype.$actionCmp.length, name: this.compoName});
        Vue.component(this.compoName, LoadApproxVue)
    }
})