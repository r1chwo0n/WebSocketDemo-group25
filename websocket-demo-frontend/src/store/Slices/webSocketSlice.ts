import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { RootState } from "../store.ts";
import Stomp from "stompjs";

export enum messageType {
  CHAT = "CHAT",
  JOIN = "JOIN",
  LEAVE = "LEAVE",
}
interface webSocketMessage {
  sender: string;
  content: string;
  timestamp: string;
  type: messageType;
}
interface webSocketState {
  userCount: number;
  isConnected: boolean;
  stompClient: Stomp.Client | undefined;
  messages: webSocketMessage[] | undefined;
}

const initialState: webSocketState = {
  userCount: 0,
  isConnected: false,
  stompClient: undefined,
  messages: [],
};

export const webSocketSlice = createSlice({
  name: "webSocket",
  initialState,
  reducers: {
    setIsConnected: (state, action: PayloadAction<boolean>) => {
      state.isConnected = action.payload;
    },
    appendMessage: (state, action: PayloadAction<webSocketMessage>) => {
      state.messages?.push(action.payload);
    },
    setStompClient: (state, action: PayloadAction<Stomp.Client>) => {
      state.stompClient = action.payload;
    },
    appendCount: (state, action: PayloadAction<number>) => {
      state.userCount = action.payload;
    },
  },
});

export const { setIsConnected, appendMessage, setStompClient, appendCount } =
  webSocketSlice.actions;
export default webSocketSlice.reducer;
export const selectWebSocket = (state: RootState) => state.webSocket;
