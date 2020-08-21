import { VuexModule, Module, Mutation } from "vuex-module-decorators";

@Module({namespaced: true})
class ToolBarState extends VuexModule {
    public fuseLayerVisible = false;
    public cableLayerVisible = false;
    public allVisible = false;

    @Mutation
    public showOrHideFuseLayer(): void {
        this.fuseLayerVisible = !this.fuseLayerVisible;
        this.allVisible = this.fuseLayerVisible && this.cableLayerVisible;
    }

    @Mutation
    public showOrHideCableLayer(): void {
        this.cableLayerVisible = !this.cableLayerVisible;
        this.allVisible = this.fuseLayerVisible && this.cableLayerVisible;
    }

    @Mutation
    public showOrHideAll(): void {
        if(this.fuseLayerVisible && this.cableLayerVisible) {
            this.fuseLayerVisible = false;
            this.cableLayerVisible = false;
            this.allVisible = false;
        } else {
            this.fuseLayerVisible = true;
            this.cableLayerVisible = true;
            this.allVisible = true;
        }
    }

}

export default ToolBarState;