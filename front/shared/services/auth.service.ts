import {apiServer} from "../utils";

interface LoginRequest {
    username: string;
    password: string;
}

interface RegisterRequest extends LoginRequest {
    firstName?: string;
    lastName?: string;
}

interface LoginResponse {
    token: string;
}

interface RegisterResponse {
    webSocketToken: string;
    accountConfirmationUrl: string;
}

class AuthService {
    public async register(body: RegisterRequest) {
        return await apiServer.post<RegisterResponse>('auth/register/patient', body);
    }

    public async login(body: LoginRequest) {
        return await apiServer.post<LoginResponse>('auth/login', body);
    }
}

export const authService = new AuthService();