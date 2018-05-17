package com.volgoak.fakenewsapp.beans

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class Post(@SerializedName("id")
                @Id(assignable = true) var id: Long = 0,
                @SerializedName("title")
                var title: String = "",
                @SerializedName("body")
                var body: String = "",
                @SerializedName("userId")
                var userId: Int = 0) {

    @Backlink
    var comments : ToMany<Comment>? = null

    //todo possible bugs

}