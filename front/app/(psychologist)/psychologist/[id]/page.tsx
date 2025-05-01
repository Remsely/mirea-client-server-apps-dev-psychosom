import {PsychologistProfile} from "@/shared/componetns";

// export async function generateMetadata({params}: { params: Promise<{ id: string }> }): Promise<Metadata> {
//     const {id} = await params;
//     const idNumber = Number(id);
//
//     const specialist = await psychologistService.getPsychologistProfile(idNumber);
//
//     if (!specialist) {
//         return {title: 'Специалист не найден'};
//     }
//
//     return {
//         title: `Психосоматика | ${specialist.firstName} ${specialist.lastName}`,
//         description: `Профиль психолога ${specialist.firstName} ${specialist.lastName}.`,
//     };
// }

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