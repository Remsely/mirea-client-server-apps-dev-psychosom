export type Review = {
    id: number;
    name: string;
    star: number;
    message: string;
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