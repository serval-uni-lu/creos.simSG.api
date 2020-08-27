// eslint-disable-next-line
import Vue from "vue";
import MockVue from "@/plugin/action/mock/MockVue.vue";

export default new (class MockAction {
    public install() {
        Vue.prototype.$actionCmp.push({id: 1, name: 'Test'});
        Vue.component('Test', MockVue)
    }
})