package com.example.model

import com.example.config.jsonConfig
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.json.jsonb

object DictionaryArticles : Table("dictionary_articles") {
    val id = uuid("id")
    val character = text("character")
    val unicodeCode = varchar("unicode_code", 20)
    val unicodeKey = varchar("unicode_key", 20)
    val strokeCountUnicode = integer("stroke_count_unicode")
    val strokeCountTotal = integer("stroke_count_total")
    val tone = varchar("tone", 50)
    val seaOfWritingAnalysis = text("sea_of_writing_analysis")

    // JSONB хранение сложных вложенных структур
    val components = jsonb("components", jsonConfig, ListSerializer(CharacterComponent.serializer()))
    val reconstructions = jsonb("reconstructions", jsonConfig, ListSerializer(Reconstruction.serializer()))
    val dictionaryReferences = jsonb("dictionary_references", jsonConfig, ListSerializer(DictionaryReference.serializer()))
    val initials = jsonb("initials", jsonConfig, ListSerializer(TangutInitial.serializer()))
    val rhymes = jsonb("rhymes", jsonConfig, ListSerializer(TangutRhyme.serializer()))
    val fanqie = jsonb("fanqie", jsonConfig, ListSerializer(Fanqie.serializer()))
    val chineseCharacters = jsonb("chinese_characters", jsonConfig, ListSerializer(ChineseCharacterEntry.serializer()))
    val tibetanSyllables = jsonb("tibetan_syllables", jsonConfig, ListSerializer(TibetanSyllable.serializer()))
    val sanskritSyllables = jsonb("sanskrit_syllables", jsonConfig, ListSerializer(SanskritSyllable.serializer()))
    val imageGroups = jsonb("image_groups", jsonConfig, ListSerializer(CharacterImageGroup.serializer()))
    val compoundWords = jsonb("compound_words", jsonConfig, ListSerializer(CompoundWord.serializer()))
    val relatedWords = jsonb("related_words", jsonConfig, ListSerializer(RelatedWord.serializer()))
    val corpusExamples = jsonb("corpus_examples", jsonConfig, ListSerializer(CorpusExample.serializer()))

    override val primaryKey = PrimaryKey(id)
}