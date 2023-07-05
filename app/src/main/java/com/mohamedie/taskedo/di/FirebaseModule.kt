package com.mohamedie.taskedo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    factory { get<FirebaseAuth>().currentUser }
    single(createdAtStart = false) { FirebaseStorage.getInstance() }
    single(createdAtStart = false) { FirebaseFirestore.getInstance() }
}