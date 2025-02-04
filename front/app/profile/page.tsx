import { getUserSession } from '@/shared/lib/get-user-session';
import { redirect } from 'next/navigation';
import {ProfileForm} from "@/shared/componetns/shared/Forms";

export default async function ProfilePage() {
    const session = await getUserSession();

    if (!session) {
        return redirect('/');
    }

    return <ProfileForm/>;
}