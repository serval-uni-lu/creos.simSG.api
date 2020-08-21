

export enum State {
    CLOSED = "Closed",
    OPEN = "Open"
}

function oppositeState(current: State): State {
    if(current === State.CLOSED) {
        return State.OPEN;
    }
    return State.CLOSED;
}

export const MAX_CONF = 1;


class ConfidenceLevel {
    public level: number;

    constructor(level: number) {
        this.level = level;
    }

    public prettyConf(): string {
        return (this.level * 100).toFixed(2);
    }

    public oppositeLevel(): number {
        return 1 - this.level;
    }
}

class UStatus {
    public state: State;
    public confidence: ConfidenceLevel;


    constructor(state: State, confidence: number) {
        this.state = state;
        this.confidence = new ConfidenceLevel(confidence);
    }

    get prettyConf(): string {
        return this.confidence.prettyConf();
    }

    public isClosed(): boolean {
        return this.state === State.CLOSED;
    }

    public opposite(): UStatus {
        return new UStatus(oppositeState(this.state), this.confidence.oppositeLevel());

    }
}

export class Fuse {
   public status: UStatus;
   public uloads: Array<ULoad>;

   constructor(state: State, confidence: number) {
       this.status = new UStatus(state, confidence);
       this.uloads = Array<ULoad>();
   }



   public isClosed(): boolean {
       return this.status.isClosed();
   }

}

class ULoad {
    public load: number;
    public confidence: ConfidenceLevel;


    constructor(load: number, confidence: number) {
        this.load = load;
        this.confidence = new ConfidenceLevel(confidence);
    }

    public prettyLoad(): string {
        return this.load.toFixed(2);
    }

    public prettyConf(): string {
        return this.confidence.prettyConf();
    }
}



