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
    y: number,
    onLineX: number,
    endX: number
}


export interface CableInfo {}

export interface SimpleCableInfo extends CableInfo {
    line1: Line;
    line2: Line;
    circle: Circle;
}

export interface ComplexCableInfo extends CableInfo{
    path: String;
    circle: CirclesComplexLine;
    line: Line;
}