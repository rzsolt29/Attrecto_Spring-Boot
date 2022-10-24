type EventName = string;

type Callback = (data?: any) => void;

export class EventEmitter {
  private events: Record<string, Callback[]> = {};

  dispatch = (event: EventName, data?: any) => {
    if (!this.hasListeners(event)) {
      return;
    }

    this.events[event].forEach((callback) => callback(data));
  };

  subscribe = (event: EventName, callback: (data: any) => void) => {
    if (!this.events[event]) {
      this.events[event] = [];
    }

    this.events[event].push(callback);

    return () => {
      if (!this.hasListeners(event)) {
        return;
      }

      this.events[event] = this.events[event].filter((listener) => listener !== callback);

      if (!this.events[event].length) {
        delete this.events[event];
      }
    };
  };

  private hasListeners = (event: EventName) => {
    return Array.isArray(this.events[event]);
  };
}
