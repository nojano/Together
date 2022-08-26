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

class Post(category: String, title: String, description: String, city: String, ownerUser: String, neededMembers: String, membersAlreadyIn: String) {
    /*why spinner?*/
    var ownerUser = ownerUser
    var category = category
    var title = title
    var description = description
    var city = city
    var membersAlreadyIn = membersAlreadyIn
    var neededMembers = neededMembers

}

fun fillPost(postActivity: NewPostActivity,user : FirebaseUser):Post {
    val spinner = postActivity.findViewById<EditText>(R.id.newPostSpinnerCategory) as Spinner
    val category = spinner.selectedItem.toString()
    val title = postActivity.findViewById<EditText>(R.id.newPostTitle).text.toString()
    val description = postActivity.findViewById<EditText>(R.id.newPostDescription).text.toString()
    val city = postActivity.findViewById<EditText>(R.id.newPostCity).text.toString()
    val membersAlreadyIn = postActivity.findViewById<EditText>(R.id.newPostMembersAlreadyIn).text.toString()
    val neededMembers = postActivity.findViewById<EditText>(R.id.newPostNeededMembers).text.toString()
    return Post(category,title,description,city,user.uid, membersAlreadyIn, neededMembers)
}

fun HashPost(post : Post) : HashMap<String, String> {
    return hashMapOf<String, String>(
        "OwnerUser" to post.ownerUser,
        "Category" to post.category,
        "Title" to post.title,
        "City" to post.city,
        "Description" to post.description,
        "MembersAlreadyIn" to post.membersAlreadyIn,
        "NeededMembers" to post.neededMembers,
        "date" to DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now()).toString()
        //"Partecipant Users" to partecipantUsers.toString()
    )
}



fun publishPost(db: FirebaseFirestore, post: Post){ //firebaseuser user.uid is the string

    var hashedPost = HashPost(post)
    db.collection("Post")
        .add(hashedPost)
        .addOnSuccessListener { documentReference ->
            Log.d("Post_Class", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Post_Class", "Error adding document (newPost)", e)
        }
}

