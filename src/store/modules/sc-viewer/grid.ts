import {Module, Mutation, VuexModule} from "vuex-module-decorators";
import {Fuse, State, MAX_CONF} from "@/utils/grid";
import {Vue} from "vue-property-decorator";

@Module({namespaced: true})
class GridSCState extends VuexModule {
    public allFuses: Array<Fuse> = Array<Fuse>();


    @Mutation
    public init(nbFuses: number): void {
        for (let i=0; i<nbFuses; i++) {
            this.allFuses.push(new Fuse(State.CLOSED, MAX_CONF));
        }
    }

    @Mutation
    public switchFuse(id: number): void {
        const currFuse = this.allFuses[id];
        const oppStatus = currFuse.status.opposite();
        Vue.set(this.allFuses, id, new Fuse(oppStatus.state, oppStatus.confidence.level));
    }

}

export default GridSCState;