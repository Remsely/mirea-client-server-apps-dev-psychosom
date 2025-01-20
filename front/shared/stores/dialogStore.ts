import {create} from "zustand";

interface DialogState {
    title: string;
    setTitle: (title: string) => void;
}

const useDialogStore = create<DialogState>((set) => ({
    title: '',
    setTitle: (title) => set({ title }),
}));

export default useDialogStore;
