package  com.mera.islam.duaazkar.core

interface EntityModelMapper<Entity,Model> {
    fun entityToModelMapper(entity: Entity): Model
    fun modelToEntityMapper(model: Model): Entity
}