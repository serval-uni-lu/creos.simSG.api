export interface ActionData {
    id: number;
    name: string;
}

export const NULL_ACTION: ActionData = {
    id: -1,
    name: "Choose your actuator"
}

export function actionDataIsNotNull(toTest: ActionData): boolean {
    return toTest.id !== NULL_ACTION.id && toTest.name !== NULL_ACTION.name;

}