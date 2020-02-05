package k.s.yarlykov.animafolio.application

import k.s.yarlykov.animafolio.repository.photo.PhotoRepository

interface DependencySource {
    fun getPhotoReposytory() : PhotoRepository
}