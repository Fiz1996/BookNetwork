import api from "./api";
import {
    AuthenticationRequest,

    AuthenticationResponse
} from "@/app/types/auth";
import { RegisterRequest} from "@/app/types/register";

export async function authenticate(
    request: AuthenticationRequest
): Promise<AuthenticationResponse> {

    const response = await api.post<AuthenticationResponse>(
        "/auth/authenticate",
        request
    );

    return response.data;
}


export async function register(request: RegisterRequest): Promise<void> {

    const response = await api.post<void>(
    "/auth/register",
        request
    );
    return response.data;

}