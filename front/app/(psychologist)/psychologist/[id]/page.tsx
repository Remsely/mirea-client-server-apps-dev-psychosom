import {PsychologistProfile} from "@/shared/componetns";

import {Suspense} from 'react';

export default async function PsychologistsPage({params}: { params: Promise<{ id: string }> }) {
    const {id} = await params;

    return (
        <main className="container">
            <Suspense fallback={null}>
                <PsychologistProfile psychologistId={Number(id)}/>
            </Suspense>
        </main>
    );
}