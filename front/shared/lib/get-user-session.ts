import { getServerSession } from 'next-auth';
import { authConfig } from '@/shared/constants/auth';

export const getUserSession = async () => {
    const session = await getServerSession(authConfig);

    return session?.user ?? null;
};