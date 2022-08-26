package com.example.together

import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import com.google.common.base.Objects
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Post constructor(category: String, title: String, description: String, city: String, ownerUser: String, partecipantUsers: MutableList<String>, neededMembers: String, membersAlreadyIn: String) {

}

fun publishPost(db: FirebaseFirestore, user : FirebaseUser, postActivity : NewPostActivity){ //firebaseuser user.uid is the string
    val spinner = postActivity.findViewById(R.id.newPostSpinnerCategory) as Spinner
    val category = spinner.selectedItem.toString()
    val title = postActivity.findViewById<EditText>(R.id.newPostTitle).text.toString()
    val description = postActivity.findViewById<EditText>(R.id.newPostDescription).text.toString()
    val city = postActivity.findViewById<EditText>(R.id.newPostCity).text.toString()
    val membersAlreadyIn = postActivity.findViewById<EditText>(R.id.newPostMembersAlreadyIn).text.toString()
    val neededMembers = postActivity.findViewById<EditText>(R.id.newPostNeededMembers).text.toString()
    val userID = user.uid
    val partecipantUsers = mutableListOf<String>()
    var newPost = Post(category, title, description, city, userID, partecipantUsers, neededMembers, membersAlreadyIn)
    var newpostdue = HashPost(category, title, description, city, userID, partecipantUsers)

/*
    db.collection("Post").document(newPost)
        .set(newPost, SetOptions.merge())
        .addOnSuccessListener { Log.d("Post_Class", "DocumentSnapshot successfully written!") }
        .addOnFailureListener { e -> Log.w("Post_Class", "Error writing document", e) }
*/

    db.collection("Post")
        .add(newPost)
        .addOnSuccessListener { documentReference ->
            Log.d("Post_Class", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Post_Class", "Error adding document (newPost)", e)
        }
}

fun HashPost(category: String, title: String, description: String, city: String, ownerUser: String, partecipantUsers: MutableList<String>) : HashMap<String, String> {
    return hashMapOf<String, String>(
        "OwnerUser" to ownerUser,
        "Category" to category,
        "Title" to title,
        "City" to city,
        "Description" to description,
        "dateExample" to DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now()).toString()
        //"Partecipant Users" to partecipantUsers.toString()
        )
}