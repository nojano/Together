package com.example.together

import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Post(var category: String,
           var title: String,
           var description: String,
           var city: String,
           var ownerUser: String,
           var neededMembers: String,
           var membersAlreadyIn: String
)

fun fillPost(postActivity: NewPostActivity):Post {
    val spinner = postActivity.findViewById<EditText>(R.id.newPostSpinnerCategory) as Spinner
    val category = spinner.selectedItem.toString()
    val title = postActivity.findViewById<EditText>(R.id.newPostTitle).text.toString()
    val description = postActivity.findViewById<EditText>(R.id.newPostDescription).text.toString()
    val city = postActivity.findViewById<EditText>(R.id.newPostCity).text.toString()
    val membersAlreadyIn = postActivity.findViewById<EditText>(R.id.newPostMembersAlreadyIn).text.toString()
    val neededMembers = postActivity.findViewById<EditText>(R.id.newPostNeededMembers).text.toString()
    return Post(category,title,description,city, FirebaseAuth.getInstance().currentUser!!.uid, membersAlreadyIn, neededMembers)
}

fun hashPost(post : Post) : HashMap<String, String> {
    return hashMapOf(
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
    )
}

fun publishPost(db: FirebaseFirestore, post: Post) : Boolean{
    var success = true
    val hashedPost = hashPost(post)
    db.collection("post")
        .add(hashedPost)
        .addOnSuccessListener { documentReference ->
            Log.d("Post_Class", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Post_Class", "Error adding document (newPost)", e)
            success= false
        }
    return success
}

