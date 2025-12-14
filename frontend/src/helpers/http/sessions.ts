import api from "./api";
import {ERRORS, sendError} from "@/helpers/http/errors";
import {
    LoginRequest,
    SignupRequest,
    TextPage,
    TextPageRequest,
    TextRequest,
    UploadResponse,
    User
} from "@/helpers/http/interfaces";

export async function login(credentials: LoginRequest): Promise<String | null> {
    try {
        const res = await api.post("/auth/login", credentials);
        return res.data;
    } catch (err) {
        sendError(ERRORS.AUTH_LOGIN_FAILED, err);
        return null;
    }
}

export async function signup(data: SignupRequest): Promise<String | null> {
    try {
        const res = await api.post("/auth/signup", data);
        return res.data;
    } catch (err) {
        sendError(ERRORS.AUTH_SIGNUP_FAILED, err);
        return null;
    }
}

export async function logout(): Promise<void> {
    try {
        await api.post("/auth/logout");
    } catch (err) {
        sendError(ERRORS.AUTH_LOGOUT_FAILED, err);
    }
}

export async function getCurrentUser(): Promise<User | null> {
    try {
        const res = await api.get<User>("/users/me");
        return res.data;
    } catch (err) {
        sendError(ERRORS.FETCHING_USER_FAILED, err);
        return null;
    }
}

export async function uploadImage(file: File): Promise<string | null> {
    const formData = new FormData();
    formData.append("file", file);

    try {
        const res = await api.post<UploadResponse>("/image/upload", formData, {
            headers: {"Content-Type": "multipart/form-data"},
        });
        return res.data.id;
    } catch (err) {
        sendError(ERRORS.IMAGE_UPLOAD_FAILED, err);
        return null;
    }
}

export async function getImage(id: string): Promise<Blob | null> {
    try {
        const res = await api.get(`/images/${id}`, {responseType: "blob"});
        return res.data;
    } catch (err) {
        sendError(ERRORS.FETCHING_IMAGE_FAILED, err);
        return null;
    }
}

export async function search(query = "", mode: "texts" | "dict" = "texts"): Promise<Text[] | TextPage[]> {
    try {
        const res = await api.get(`/search`, {
            params: {query, mode},
        });
        return res.data;
    } catch (err) {
        sendError(ERRORS.SEARCH_FAILED, err);
        return [];
    }
}

export async function createText(request: TextRequest): Promise<string | null> {
    try {
        const res = await api.post("/texts", request);
        return res.data;
    } catch (err) {
        sendError(ERRORS.CREATE_TEXT_FAILED, err);
        return null;
    }
}

export async function getAllTexts(): Promise<Text[]> {
    try {
        const res = await api.get<Text[]>("/texts");
        return res.data;
    } catch (err) {
        sendError(ERRORS.FETCH_ALL_TEXTS_FAILED, err);
        return [];
    }
}

export async function getTextById(id: string): Promise<Text | null> {
    try {
        const res = await api.get<Text>(`/texts/${id}`);
        return res.data;
    } catch (err) {
        sendError(ERRORS.FETCH_TEXT_FAILED, err);
        return null;
    }
}

export async function updateText(id: string, text: TextRequest): Promise<boolean> {
    try {
        const res = await api.put(`/texts/${id}`, text);
        return res.data != null;
    } catch (err) {
        sendError(ERRORS.UPDATE_TEXT_FAILED, err);
        return false;
    }
}

export async function deleteText(id: string): Promise<boolean> {
    try {
        const res = await api.delete(`/texts/${id}`);
        return res.data != null;
    } catch (err) {
        sendError(ERRORS.DELETE_TEXT_FAILED, err);
        return false;
    }
}

export async function createTextPage(request: TextPageRequest): Promise<string | null> {
    try {
        const res = await api.post("/pages", request);
        return res.data;
    } catch (err) {
        sendError(ERRORS.CREATE_TEXT_PAGE_FAILED, err);
        return null;
    }
}

export async function getAllTextPages(): Promise<TextPage[]> {
    try {
        const res = await api.get<TextPage[]>("/pages");
        return res.data;
    } catch (err) {
        sendError(ERRORS.CREATE_ALL_TEXT_PAGES_FAILED, err);
        return [];
    }
}

export async function getTextPageById(id: string): Promise<TextPage | null> {
    try {
        const res = await api.get<TextPage>(`/pages/${id}`);
        return res.data;
    } catch (err) {
        sendError(ERRORS.FETCH_TEXT_PAGE_FAILED, err);
        return null;
    }
}

export async function updateTextPage(id: string, page: TextPageRequest): Promise<boolean> {
    try {
        const res = await api.put(`/pages/${id}`, page);
        return res.data != null;
    } catch (err) {
        sendError(ERRORS.UPDATE_TEXT_PAGE_FAILED, err);
        return false;
    }
}

export async function deleteTextPage(id: string): Promise<boolean> {
    try {
        const res = await api.delete(`/pages/${id}`);
        return res.data != null;
    } catch (err) {
        sendError(ERRORS.DELETE_TEXT_PAGE_FAILED, err);
        return false;
    }
}

export async function getUserById(id: string): Promise<User | null> {
    try {
        const res = await api.get<User>(`/users/${id}`);
        return res.data;
    } catch (err) {
        sendError(ERRORS.FETCH_USER_FAILED, err);
        return null;
    }
}

export async function getAllUsers(): Promise<User[]> {
    try {
        const res = await api.get<User[]>("/users");
        return res.data;
    } catch (err) {
        sendError(ERRORS.FETCH_ALL_USERS_FAILED, err);
        return [];
    }
}

export async function updateUser(id: string, user: Partial<User>): Promise<boolean> {
    try {
        const res = await api.put(`/users/${id}`, user);
        return res.data != null;
    } catch (err) {
        sendError(ERRORS.UPDATE_USER_FAILED, err);
        return false;
    }
}

export async function deleteUser(id: string): Promise<boolean> {
    try {
        const res = await api.delete(`/users/${id}`);
        return res.data != null;
    } catch (err) {
        sendError(ERRORS.DELETE_USER_FAILED, err);
        return false;
    }
}
