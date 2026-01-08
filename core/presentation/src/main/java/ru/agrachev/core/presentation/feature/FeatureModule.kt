package ru.agrachev.core.presentation.feature

interface FeatureModule<Module : Any> {

    fun provideFeature(): Feature<Module>
}
