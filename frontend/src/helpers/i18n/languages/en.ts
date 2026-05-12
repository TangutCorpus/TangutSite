const en = {
    common: {
        loading: "Loading...",
        save: "Save",
        saving: "Saving...",
        cancel: "Cancel",
        hide: "Hide",
        delete: "Delete"
    },
    languages: {
        ru: "Russian",
        en: "English",
        zh_tw: "Traditional Chinese",
        zh_cn: "Simplified Chinese",
    },
    DictionaryArticlePage: {
        title: "Dictionary Entry",
        edit: "Edit",
        loading: "Loading article..."
    },
    CharacterCard: {
        key: "Key",
        strokes: "strokes",
        components: "Components",
        seaOfWriting: "Sea of Writing Analysis",
        seaOfWritingHint: "Structural analysis of the character according to the ancient 'Sea of Writing' (Wenhai) dictionary.",
        roles: {
            semantic: "Semantic",
            phonetic: "Phonetic",
            chinesePhonetic: "Ch. Phonetic",
            other: "Other"
        }
    },
    RawDataCard: {
        tabs: {
            main: "Main",
            correspondences: "Correspondences"
        },
        strokeCountTotal: "Total stroke count",
        initials: "Initials",
        tone: "Tone",
        rhymes: "Rhymes",
        rhymeLabel: "Rhyme {number}",
        fanqie: "Fanqie",
        chineseCharacters: "Chinese Equiv.",
        tibetanSyllables: "Tibetan Syllables",
        sanskritSyllables: "Sanskrit Syllables"
    },
    ReconstructionsCard: {
        title: "Reconstructions",
        deprecated: "depr.",
        hideDeprecated: "Hide deprecated",
        showDeprecated: "Show deprecated ({count})"
    },
    DictionaryReferencesCard: {
        title: "Dictionary References",
        remove: "Remove reference",
        add: "Add reference",
        selectDict: "Select dictionary",
        pageOrNumber: "Page / Number",
        save: "Add"
    },
    CharacterImagesCard: {
        title: "Character Forms in Texts"
    },
    CompoundWordsCard: {
        title: "Compound Words"
    },
    RelatedWordsCard: {
        title: "Related Words (Gyalrongic)"
    },
    CorpusExamplesCard: {
        title: "Corpus Examples"
    },
    DictionaryEdit: {
        createTitle: "Create Dictionary Entry",
        editTitle: "Edit Entry",
        sections: {
            basic: "Basic Information",
            components: "Component Breakdown",
            phonology: "Phonology & Structure",
            compoundWords: "Compound Words"
        },
        character: "Character",
        unicodeCode: "Unicode Code",
        unicodeKey: "Unicode Key",
        strokeCountUnicode: "Strokes (Unicode)",
        strokeCountTotal: "Strokes (Total)",
        tone: "Tone",
        seaOfWriting: "Sea of Writing (4 chars)",
        linkedArticleId: "Linked Article ID",
        addComponent: "Add Component",
        compoundText: "Word",
        compoundMeaning: "Meaning",
        enterMeaning: "Enter word meaning",
        addCompoundWord: "Add Compound Word",
        note: "Note"
    },
    About: {
        title: "Tangut Heritage",
        subtitle: "2026",
        historyTitle: "A Forgotten Script",
        historyP1: "Tangut script (Xi Xia) is one of the most complex and enigmatic writing systems in human history. Created in the 11th century for the needs of the Great State of Xi Xia, it consists of over 6,000 unique characters, each of which can surpass Chinese characters in complexity.",
        historyP2: "After the fall of the state under the onslaught of Genghis Khan's troops in 1227, the language was practically forgotten for seven centuries until priceless manuscripts were discovered in the dead city of Khara-Khoto at the beginning of the 20th century.",
        missionTitle: "Our Project's Mission",
        missionText: "We are creating a digital environment for the deciphering and study of Tangut texts. Our goal is to make ancient manuscripts accessible to a new generation of researchers and linguists, combining philological precision with modern data processing technologies.",
        featuresTitle: "System Features",
        features: [
            "Complete absence of borrowed characters",
            "Ultra-complex ideographic structure",
            "Unique Buddhist canonical texts",
            "Logical system of radical construction"
        ],
        stats: {
            characters: "Characters",
            year: "Year",
            texts: "Texts"
        }
    },
    AuthComponent: {
        email: 'Email',
        nickname: 'Nickname',
        password: 'Password',
        next: 'Next',
        realName: 'Full name',
        biography: 'About',
        agreeWithTerms: 'I agree to the terms',
        completeRegistration: 'Complete registration',
        signIn: 'Sign In',
        authDescription: 'Sign up or log in to get full access',
        registrationHeader: 'Registration',
        signInHeader: 'Sign In'
    }, HeaderNavComponent: {
        dictionary: 'Dictionary',
        aboutPage: 'About us',
        library: 'Library',
        signIn: 'Sign In',
        addText: 'Add text',
        profile: 'Profile'
    }, SearchBar: {
        placeholder: "Enter a query…",
        searchArea: "Search area",
        textMode: "Texts",
        dictionaryMode: "Dictionary",
        chooseRadicals: "Select components"
    }, TextFragmentEdit: {
        page: "Page",
        textId: "Text ID"
    }, ToggleButtons: {
        registration: "Registration",
        signIn: "Sign In"
    }, HomePage: {
        title: "Tangut Language Corpus"
    }, MainLibraryPage: {
        contactAdministrator: "Contact the administrator",
        textsNotFound: "No texts found"
    }, NotFoundPage: {
        contactAdministrator: "Contact the administrator",
        textsNotFound: "No texts found"
    }, SearchResultCard: {
        readFarther: "Read more",
    }, TextFragmentEditPopup: {
        save: "Save",
        close: "Close"
    }, TextFragmentPreview: {
        edit: "Edit",
        error: "Error",
        notAccessible: "Page unavailable…",
        page: "Page",
    }, TextMetadataEdit: {
        textMetadata: "Text metadata",
        deleteProperty: "Delete property",
        choosePropertyToDelete: "Select a property to edit",
        author: "Author",
        date: "Creation date",
        description: "Description",
        material: "Material",
        storage: "Storage",
        catalog: "Catalog reference",
        repository: "Repository",
        form: "Format",
        height: "Height",
        width: "Width",
        frameType: "Frame type",
        frameHeight: "Frame height",
        pages: "Number of pages",
        linesPerPage: "Lines per page",
        charactersPerLine: "Characters per line"
    }, TextEditPage: {
        addFragment: "Add fragment",
        textName: "Text name",
        toSave: "Save",
        saved: "Saved"
    }, TranslationListEdit: {
        editTranslations: "Edit translations",
        addTranslation: "Add translation",
        originalText: "Original text",
        enterTranslation: "Enter translation"
    }, TextFragmentEditPage: {
        save: "Save"
    }, TranslationList: {
        original: "Оригинал",
        translations: "Translations",
        error: "Error",
        noTranslations: "No available translations…",
    }, TextFragmentPage: {
        text: "Text",
        page: "Page",
        edit: "Edit"
    }, GlossedText: {
        error: "Error",
        noConnectedPages: "No pages connected to this text",
        page: "Page"
    }, TextCommentCard: {
        comments: "Comments"
    }, TextPage: {
        information: "Information",
        edit: "Edit"
    }, ImageUpload: {
        images: "Images",
        pickImage: "Select images"
    }, UserAvatarUpload: {
        changeAvatar: "Change avatar"
    }, UserBiographyEdit: {
        biography: "About the user"
    }, UserContactEdit: {
        contactInfo: "Contact information",
        email: "Email",
        realName: "Full name"
    }, UserEditPage: {
        save: "Save"
    }, UserBiography: {
        biography: "About the user"
    }, UserContact: {
        contactInfo: "Contact information",
        email: "Email",
        realName: "Full name",
        role: "Role"
    }, DictionaryIndexPage: {
        title: "Dictionary",
        totalCount: "Number of articles"
    }
}

export default en