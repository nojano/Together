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

var myUserProfile = User("", "", "", "", "", "")

data class User(var userID : String,
                var username : String,
                var nameAndSurname : String,
                var phoneNumber : String,
                var email : String,
                var city : String
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
                    "username" -> myUserProfile.username = a.value.toString()
                    "nameAndSurname" -> myUserProfile.nameAndSurname = a.value.toString()
                    "phoneNumber" -> myUserProfile.phoneNumber = a.value.toString()
                    "email" -> myUserProfile.email = a.value.toString()
                    "city" -> myUserProfile.city = a.value.toString()
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
    val tag = "User_Class"
    val db = Firebase.firestore
    val firebaseUser: FirebaseUser = task.result!!.user!!

    // Add a new document with the same ID of the firebase user ID
    db.collection("user").document(firebaseUser.uid).set(hashedUser)
        .addOnSuccessListener {
            Log.d(tag, "DocumentSnapshot successfully written!")
        }
        .addOnFailureListener { e ->
            Log.w(tag, "Error adding document", e)
        }

    //insert the userID into the myUserProfile class
    myUserProfile.userID = firebaseUser.uid
}

//returns the HashMap document of the User class ready to be inserted into user collection
fun getHashedUser(): HashMap<String, String> {
    return hashMapOf(
        "nameAndSurname" to myUserProfile.nameAndSurname,
        "username" to myUserProfile.username,
        "email" to myUserProfile.email,
        "city" to myUserProfile.city,
        "phoneNumber" to myUserProfile.phoneNumber
    )
}

//takes the user info inserted in RegistrationActivity and sets myUserProfile instance
fun setMyProfileUser(registrationActivity : RegistrationActivity) {
    val username = (registrationActivity.findViewById(R.id.username) as EditText).text.toString()
    val nameAndSurname = (registrationActivity.findViewById(R.id.namesurname) as EditText).text.toString()
    val phoneNumber = (registrationActivity.findViewById(R.id.telephonenumber) as EditText).text.toString()
    val email = (registrationActivity.findViewById(R.id.email) as EditText).text.toString()
    val city = (registrationActivity.findViewById(R.id.city) as EditText).text.toString()
    myUserProfile = User("", username, nameAndSurname, phoneNumber, email, city)
}