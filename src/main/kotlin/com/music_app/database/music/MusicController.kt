package com.music_app.database.music

class MusicController {

    fun getMusics(limit: Int, offset: Long): List<MusicDTO> =
        Musics.fetch(limit, offset)

    fun getMusicById(musicId: Int) = Musics.selectById(musicId = musicId)

    fun insertMusic(music: MusicDTO) {
        Musics.insert(music)
    }
}
