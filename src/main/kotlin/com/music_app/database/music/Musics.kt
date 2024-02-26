package com.music_app.database.music

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Musics: Table("musics") {

    private val id = Musics.integer("music_id").autoIncrement()
    private val title = Musics.varchar("title", 64)
    private val version = Musics.varchar("version", 64).nullable()
    private val duration = Musics.long("duration")
    private val author = Musics.varchar("author", 64)
    private val audio = Musics.varchar("audio", 64)
    private val video = Musics.varchar("video", 64).nullable()
    private val smallCover = Musics.varchar("small_cover", 64).nullable()
    private val largeCover = Musics.varchar("large_cover", 64).nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    fun insert(music: MusicDTO) {
        transaction {
            Musics.insert {
                it[title] = music.title
                it[version] = music.version
                it[duration] = music.duration
                it[author] = music.author
                it[audio] = music.audio
                it[video] = music.video
                it[smallCover] = music.smallCover
                it[largeCover] = music.largeCover
            }
        }
    }

    fun fetchAll(): List<MusicDTO> {
        return transaction {
            val musics = Musics.selectAll()
            musics.map {
                MusicDTO(
                    id = it[Musics.id],
                    title = it[title],
                    version = it[version],
                    duration = it[Musics.duration],
                    author = it[author],
                    audio = it[audio],
                    video = it[video],
                    smallCover = it[smallCover],
                    largeCover = it[largeCover]
                )
            }
        }
    }

    fun fetch(limit: Int, offset: Long): List<MusicDTO> {
        return transaction {
            val musics = Musics.selectAll().limit(limit, offset)
            musics.map {
                MusicDTO(
                    id = it[Musics.id],
                    title = it[title],
                    version = it[version],
                    duration = it[Musics.duration],
                    author = it[author],
                    audio = it[audio],
                    video = it[video],
                    smallCover = it[smallCover],
                    largeCover = it[largeCover]
                )
            }
        }
    }

    fun selectById(musicId: Int): MusicDTO? {
        return transaction {
            val music = Musics.select { Musics.id.eq(musicId) }.singleOrNull()
            music?.let {
                MusicDTO(
                    id = it[Musics.id],
                    title = it[title],
                    version = it[version],
                    duration = it[Musics.duration],
                    author = it[author],
                    audio = it[audio],
                    video = it[video],
                    smallCover = it[smallCover],
                    largeCover = it[largeCover]
                )
            }
        }
    }
}
