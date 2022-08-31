package com.example.together

import android.util.Log
import android.widget.EditText
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

var myUserProfile = User("", "", "", "", "", "", "" ,"")
private const val TAG = "User_Class"

data class User(var userID : String,
                var name : String,
                var surname : String,
                var email : String,
                var city : String,
                var gender : String,
                var age : String,
                var description : String
)

fun updateMyUserProfile() {
    myUserProfile.userID = FirebaseAuth.getInstance().currentUser?.uid.toString()

    val tag = "User_Class"
    val docRef = FirebaseFirestore.getInstance().collection("user")
        .document(myUserProfile.userID)   /////////////////////////////////////////////////////////
    docRef.get().addOnSuccessListener { document ->
        if(document.data != null) {
            for (a in document.data!!) {
                when (a.key) {
                    "name" -> myUserProfile.name = a.value.toString()
                    "surname" -> myUserProfile.surname = a.value.toString()
                    "email" -> myUserProfile.email = a.value.toString()
                    "city" -> myUserProfile.city = a.value.toString()
                    "gender" -> myUserProfile.gender = a.value.toString()
                    "age" -> myUserProfile.age = a.value.toString()
                    "description" -> myUserProfile.description = a.value.toString()
                }
            }
        }
        Log.d(tag, "DocumentSnapshot data: ${document.data}")
    }
        .addOnFailureListener { exception ->
            Log.w(tag, "Error getting documents: ", exception)
        }
}

fun registerUser(task : Task<AuthResult>, hashedUser : HashMap<String, String>){
    val db = Firebase.firestore
    val firebaseUser: FirebaseUser = task.result!!.user!!

    // Add a new document with the same ID of the firebase user ID
    db.collection("user").document(firebaseUser.uid).set(hashedUser)
        .addOnSuccessListener {
            Log.d(TAG, "DocumentSnapshot successfully written!")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }

    //insert the userID into the myUserProfile class
    myUserProfile.userID = firebaseUser.uid
}

//returns the HashMap document of the User class ready to be inserted into user collection
fun getHashedUser(): HashMap<String, String> {
    return hashMapOf(
        "name" to myUserProfile.name,
        "surname" to myUserProfile.surname,
        "email" to myUserProfile.email,
        "city" to myUserProfile.city,
        "gender" to myUserProfile.gender,
        "age" to myUserProfile.age,
        "description" to myUserProfile.description
    )
}

//takes the user info inserted in RegistrationActivity and sets myUserProfile instance
fun setMyProfileUser(registrationActivity : RegistrationActivity) {
    val name = (registrationActivity.findViewById(R.id.name) as EditText).text.toString()
    val surname = (registrationActivity.findViewById(R.id.surname) as EditText).text.toString()
    val gender = (registrationActivity.findViewById(R.id.gender) as EditText).text.toString()
    val email = (registrationActivity.findViewById(R.id.email) as EditText).text.toString()
    val city = (registrationActivity.findViewById(R.id.city) as EditText).text.toString()
    val age = (registrationActivity.findViewById(R.id.age) as EditText).text.toString()
    val description = (registrationActivity.findViewById(R.id.description) as EditText).text.toString()
    myUserProfile = User("", name, surname, email, city, gender, age, description)
}