package com.volgoak.fakenewsapp.beans

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class Comment(@SerializedName("name")
                   var name: String = "",
                   @SerializedName("postId")
                   var postId: Long = 0,
                   @SerializedName("id")
                   @Id(assignable = true) var id: Long = 0,
                   @SerializedName("body")
                   var body: String = "",
                   @SerializedName("email")
                   var email: String = "") {

    lateinit var post : ToOne<Post>
}