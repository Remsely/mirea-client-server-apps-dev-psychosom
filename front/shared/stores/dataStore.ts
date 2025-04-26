import {create} from "zustand";
import {FieldValues} from "react-hook-form";

interface DataState {
    dataAfterRegistration: FieldValues;
    setDataAfterRegistration: (data: FieldValues) => void;
}

export const useDataStore = create<DataState>((set) => ({
    dataAfterRegistration: {},
    setDataAfterRegistration: (dataAfterRegistration) => set({ dataAfterRegistration }),
}));