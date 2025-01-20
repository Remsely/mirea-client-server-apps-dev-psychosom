export type Review = {
    id?: number;
    name?: string;
    rating?: number;
    text?: string;
    date?: string;
}

export interface ServerReview {
    id: number;
    patient: {
        id: number;
        firstName: string;
        lastName: string;
    };
    psychologist: {
        id: number;
    };
    rating: number;
    text: string;
    date: string;
}

export type Navigation = {
    id: number;
    name: string;
    link: string;
}

export type User = {
    firstname: string | null;
    lastname: string | null;
    phone: string | null;
    telegram: string | null
}