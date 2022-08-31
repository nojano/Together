package com.example.together

import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private const val TAG = "Post_Class"
var res = mutableListOf<Post>()
data class Post(var postID : String,
                var category: String,
                var title: String,
                var description: String,
                var city: String,
                var ownerUser: String,
                var ownerNickname: String,
                var neededMembers: String,
                var membersAlreadyIn: String
)

fun fillPost(postActivity: NewPostActivity):Post {
    val ownerNickname = myUserProfile.name
    val spinner = postActivity.findViewById<EditText>(R.id.newPostSpinnerCategory) as Spinner
    val category = spinner.selectedItem.toString()
    val title = postActivity.findViewById<EditText>(R.id.newPostTitle).text.toString()
    val description = postActivity.findViewById<EditText>(R.id.newPostDescription).text.toString()
    val city = postActivity.findViewById<EditText>(R.id.newPostCity).text.toString()
    val membersAlreadyIn = postActivity.findViewById<EditText>(R.id.newPostMembersAlreadyIn).text.toString()
    val neededMembers = postActivity.findViewById<EditText>(R.id.newPostNeededMembers).text.toString()
    return Post("", category,title,description,city, FirebaseAuth.getInstance().currentUser!!.uid, ownerNickname , membersAlreadyIn, neededMembers)
}

fun hashPost(post : Post) : HashMap<String, String> {
    return hashMapOf(
        "OwnerUser" to post.ownerUser,
        "OwnerNickname" to post.ownerNickname,
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
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document (newPost)", e)
            success= false
        }
    return success
}

fun getPost() {
    res = mutableListOf()
    val db = FirebaseFirestore.getInstance()
    db.collection("post").orderBy("date").get().addOnSuccessListener { documents ->
        for (document in documents) {
            Log.d(TAG, "${document.id} => ${document.data}")
            res.add(deserializePost(document))
        }
    }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents: ", exception)
        }
    Log.w(TAG, "Error getting documents: ")
}

fun deserializePost(document : QueryDocumentSnapshot): Post{
    val postID = document.id
    var category = ""
    var title = ""
    var description = ""
    var city = ""
    var ownerUser = ""
    var ownerNickname = ""
    var neededMembers = ""
    var membersAlreadyIn = ""
    for(a in document.data){
        when(a.key){
            "Category" -> category = a.value.toString()
            "Title" -> title = a.value.toString()
            "Description" -> description = a.value.toString()
            "City" -> city = a.value.toString()
            "OwnerUser" -> ownerUser = a.value.toString()
            "OwnerNickname" -> ownerNickname = a.value.toString()
            "NeededMembers" -> neededMembers = a.value.toString()
            "MembersAlreadyIn" -> membersAlreadyIn = a.value.toString()
        }
    }
    return Post(postID, category,title, description, city, ownerUser, ownerNickname,  neededMembers, membersAlreadyIn)
}

//TODO use only one method
fun fillTitleArray(list : MutableList<Post>): Array<String>{
    val array = Array(list.size) { "it = $it" }
    for (i in 0 until res.size){
        array[i] = res[i].title
    }
    return array
}
fun fillDescriptionArray(list : MutableList<Post>): Array<String>{
    val array = Array(list.size) { "it = $it" }
    for (i in 0 until res.size){
        array[i] = res[i].description
    }
    return array
}
fun fillCityArray(list : MutableList<Post>): Array<String>{
    val array = Array(list.size) { "it = $it" }
    for (i in 0 until res.size){
        array[i] = res[i].city
    }
    return array
}
fun fillMembersAlreadyInArray(list : MutableList<Post>): Array<String> {
    val array = Array(list.size) { "it = $it" }
    for (i in 0 until res.size){
        array[i] = res[i].membersAlreadyIn
    }
    return array
}

fun fillMembersWeNeedArray(list : MutableList<Post>): Array<String> {
    val array = Array(list.size) { "it = $it" }
    for (i in 0 until res.size){
        array[i] = res[i].neededMembers
    }
    return array
}

fun fillOwnerNicknameArray(list : MutableList<Post>): Array<String> {
    val array = Array(list.size) { "it = $it" }
    for (i in 0 until res.size){
        array[i] = res[i].ownerNickname
    }
    return array
}

