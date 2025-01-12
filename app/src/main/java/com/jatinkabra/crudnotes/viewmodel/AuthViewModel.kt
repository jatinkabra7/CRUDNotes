package com.jatinkabra.crudnotes.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.jatinkabra.crudnotes.dataClasses.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    val user = auth.currentUser

    val userId = user?.uid

    val db = FirebaseFirestore.getInstance()

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {

        _authState.value = AuthState.Loading

        if(auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        }
        else _authState.value = AuthState.Authenticated
    }

    fun login(email : String, password : String) {
        if(email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password can't be empty")
            return
        }

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if(task.isSuccessful) {
                    _authState.value = AuthState.Authenticated

                    val userDetails = hashMapOf(
                        "email" to (user?.email ?: "test@gmail.com"),
                        "createdAt" to FieldValue.serverTimestamp()
                    )

                    if (userId != null) {
                        db.collection("users").document(userId).set(userDetails, SetOptions.merge())
                    }

                }
                else _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
            }
    }

    fun signup(email: String, password: String) {
        if(email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password can't be empty")
            return
        }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if(task.isSuccessful) {
                    _authState.value = AuthState.Authenticated

                    val userDetails = hashMapOf(
                        "email" to (user?.email ?: "test@gmail.com"),
                        "createdAt" to FieldValue.serverTimestamp()
                    )

                    if (userId != null) {
                        db.collection("users").document(userId).set(userDetails, SetOptions.merge())
                    }

                }
                else _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
            }
    }

    fun sigout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

}

sealed class AuthState() {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
}