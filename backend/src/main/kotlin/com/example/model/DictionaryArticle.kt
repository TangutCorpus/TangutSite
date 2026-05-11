package com.example.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class DictionaryArticle(
    @Contextual val id: UUID = UUID.randomUUID(),
    val character: String,
    val unicodeCode: String,
    val unicodeKey: String,
    val strokeCountUnicode: Int,
    val strokeCountTotal: Int,
    val components: List<CharacterComponent>,
    val seaOfWritingAnalysis: String,
    val reconstructions: List<Reconstruction>,
    val dictionaryReferences: List<DictionaryReference>,
    val initials: List<TangutInitial>,
    val tone: String,
    val rhymes: List<TangutRhyme>,
    val fanqie: List<Fanqie>,
    val chineseCharacters: List<ChineseCharacterEntry>,
    val tibetanSyllables: List<TibetanSyllable>,
    val sanskritSyllables: List<SanskritSyllable>,
    val imageGroups: List<CharacterImageGroup>,
    val compoundWords: List<CompoundWord>,
    val relatedWords: List<RelatedWord>,
    val corpusExamples: List<CorpusExample>
)