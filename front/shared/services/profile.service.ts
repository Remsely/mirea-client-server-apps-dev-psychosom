import {api} from "../utils"

export interface ProfileData {
	firstName: string;
	lastName: string;
}

class ProfileService {
	public async findProfile() {
		return await api.get<ProfileData>('patients')
	}

	public async updateProfile(body: ProfileData) {
		return await api.put<ProfileData>('patients', body)
	}
}

export const profileService = new ProfileService()