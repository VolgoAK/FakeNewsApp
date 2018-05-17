package com.volgoak.fakenewsapp

import android.os.SystemClock
import com.volgoak.fakenewsapp.beans.MyObjectBox
import com.volgoak.fakenewsapp.beans.Post
import com.volgoak.fakenewsapp.dataSource.DataSourceImpl
import io.objectbox.BoxStore
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Created by alex on 5/17/18.
 */
class DataSourceTest {

    @Test
    fun testBoxUpdate() {
        val boxStore = MyObjectBox.builder().build()
        boxStore.boxFor(Post::class.java).put(Post(id = 10, title = "Some post"))

        val api = mock(PlaceHolderApi::class.java)
        `when`(api.getAllPosts()).thenReturn(
                Single.just(listOf(Post(id = 1, title = "first"),
                        Post(id = 2, title = "second"))))

        `when`(api.getPostById(1)).thenReturn(
                Single.just(Post(id = 1, title = "first updated"))
        )

        val dataSource = DataSourceImpl(api, boxStore)
        val testObserver = mock(TestingObserver::class.java)


        dataSource.getAllPosts(false).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe {list ->
                    println("List fetched")
                    testObserver.onPosts(list)
                }

        //Test if observer notified when posts are updated
        verify(testObserver, timeout(1000).times(1))
                .onPosts(any())

        dataSource.refreshPosts()

        verify(testObserver, timeout(1000).times(2))
                .onPosts(any())

        dataSource.refreshPost(1)

        verify(testObserver, timeout(1000).times(3))
                .onPosts(any())
    }

    interface TestingObserver {
        fun onPosts(posts : List<Post>?)
    }
}