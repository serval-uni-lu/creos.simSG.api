import {Module, Mutation, VuexModule} from "vuex-module-decorators";
import {NullSelection, Selection} from "@/utils/selection";

@Module({namespaced: true})
class InspectorState extends VuexModule {
    public selectedElement: Selection = NullSelection;

    @Mutation
    public select(elmt: Selection): void {
        this.selectedElement = elmt;
    }

}

export default InspectorState;
