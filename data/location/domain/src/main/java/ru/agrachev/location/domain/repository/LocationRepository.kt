package ru.agrachev.location.domain.repository

interface LocationRepository :
    WriteableLocationRepository,
    ListenableLocationRepository
