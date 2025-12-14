export interface User {
    id: string;
    username: string;
    email: string;
    displayName: string;
    avatarUrl?: string;
    biography?: string;
    roles?: string[];
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface SignupRequest {
    email: string;
    username: string;
    password: string;
    avatarUrl?: string;
    displayName?: string;
    biography?: string;
}

export interface UploadResponse {
    id: string;
}

export interface Text {
    id: string;
    title: string;
    metadata: string;
    pageIds: string[];
}

export interface TextPage {
    id: string;
    textId: string;
    imagesIDs: string[];
    pageNumber: number;
    pureText: string;
    glossedTextXML: string;
    translationsXML: string;
}

export interface TextRequest {
    title: string;
    metadata?: string;
    pageIds?: string[];
}

export interface TextPageRequest {
    textId: string;
    imagesIDs?: string[];
    pageNumber: number;
    pureText: string;
    glossedTextXML: string;
    translationsXML: string;
}

export enum UserRoles {
    EDITOR,
    MODERATOR,
    ADMIN,
}