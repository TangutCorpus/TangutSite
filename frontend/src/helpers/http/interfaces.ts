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

export type ComponentRole =
    | 'semantic'
    | 'phonetic'
    | 'chinesePhonetic'
    | 'other'

export interface CharacterComponent {
    id: string
    character: string
    role: ComponentRole
    articleId: string
}

export interface Reconstruction {
    id: string
    author: string
    year: number
    value: string
    deprecated: boolean
}

export interface DictionaryInfo {
    id: string
    name: string
    year: number
}

export interface DictionaryReference {
    dictionaryId: string
    dictionaryName: string
    pageOrNumber: string
}

export interface TangutDictionarySource {
    sourceId: string
    sourceName: string
}

export interface TangutInitial {
    character: string
    romanization: string
    sources: TangutDictionarySource[]
}

export interface TangutRhyme {
    number: string
    chapter?: string
    sources: TangutDictionarySource[]
}

export interface Fanqie {
    characters: string[]
    romanizedResult: string
    sources: TangutDictionarySource[]
}

export interface ChineseCharacterEntry {
    character: string
    textId?: string
    textTitle?: string
}

export interface TibetanSyllable {
    syllable: string
    textId?: string
}

export interface SanskritSyllable {
    syllable: string
    language: string
}

export interface CharacterImageEntry {
    imageId: string
    url: string
    thumbnail?: string
}

export interface CharacterImageGroup {
    textId: string
    textName: string
    previewImages: CharacterImageEntry[]
    collapsedImages: CharacterImageEntry[]
}

export interface CompoundWord {
    id: string
    characters: string
    translation: string
    searchCharPosition: number
    secondCharStrokes?: number
}

export interface RelatedWord {
    language: string
    form: string
    meaning?: string
}

export interface CorpusExample {
    textId: string
    textTitle: string
    pageNumber?: number
    segments: Array<{ text: string; highlighted: boolean }>
}

export interface DictionaryArticle {
    id: string
    character: string
    unicodeCode: string
    unicodeKey: string
    strokeCountUnicode: number
    strokeCountTotal: number

    components: CharacterComponent[]
    seaOfWritingAnalysis: string

    reconstructions: Reconstruction[]
    dictionaryReferences: DictionaryReference[]

    initials: TangutInitial[]
    tone: string
    rhymes: TangutRhyme[]
    fanqie: Fanqie[]

    chineseCharacters: ChineseCharacterEntry[]
    tibetanSyllables: TibetanSyllable[]
    sanskritSyllables: SanskritSyllable[]

    imageGroups: CharacterImageGroup[]

    compoundWords: CompoundWord[]
    relatedWords: RelatedWord[]
    corpusExamples: CorpusExample[]
}

export interface DictionarySearchResult {
    id: string
    character: string
    translation: string
    strokeCount: number
    searchCharPosition: number
}