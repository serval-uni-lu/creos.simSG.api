import Vue from "vue";
import MockVue from "@/plugin/action/MockVue.vue";

export default new (class MockAction {
    public install(vue: Vue) {
        Vue.prototype.$actionCmp.push({id: 1, name: 'Test'});
        Vue.component('Test', MockVue)
    }
})