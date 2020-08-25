import {Module, Mutation, VuexModule} from "vuex-module-decorators";
import {Fuse, State, MAX_CONF, Cable} from "@/utils/grid";
import {Vue} from "vue-property-decorator";

@Module({namespaced: true})
class GridSCState extends VuexModule {
    public allFuses: Array<Fuse> = Array<Fuse>();
    public allCables: Array<Cable> = Array<Cable>();


    @Mutation
    public init(nbFuses: number): void {
        this.allFuses = Array<Fuse>();
        this.allCables = Array<Cable>();

        for (let i=0; i<nbFuses; i++) {
            this.allFuses.push(new Fuse(State.CLOSED, MAX_CONF));
            if(i%2===0) {
                this.allCables.push(new Cable());
            }
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