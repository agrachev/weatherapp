package ru.agrachev.location.domain

interface LocationRepository :
    WriteableLocationRepository,
    ListenableLocationRepository
