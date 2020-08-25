export interface Line {
    x1: number;
    x2: number;
    y1: number;
    y2: number;
}

export interface Circle {
    x: number;
    y: number;
}

export interface Point {
    x: number;
    y: number;
}

export interface CirclesComplexLine {
    y: number;
    onLineX: number;
    endX: number;
}


export interface CableInfo {
    type: CableType;
}

export interface SimpleCableInfo extends CableInfo {
    line1: Line;
    line2: Line;
    circle: Circle;
}

export interface ComplexCableInfo extends CableInfo{
    path: string;
    circle: CirclesComplexLine;
    line: Line;
}

export interface HorizontalLine {
    x1: number;
    x2: number;
    y: number;
}

export interface OneLineCableInfo  extends CableInfo {
    line: HorizontalLine;
    circleX: number;
}

export enum CableType {
    Simple,
    Complex,
    OneLine
}