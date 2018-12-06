package com.example.framgianguyenvanthanhd.music_professional.data.comment

import com.example.framgianguyenvanthanhd.music_professional.Utils.ResponseType
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 12/5/2018.
 */
class CommentRemoteDataSource private constructor(
        private val service: CommentApi = APIService.getComment()
) : CommentDataSource {

    private object Holder {
        val INSTANCE = CommentRemoteDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance(): CommentRemoteDataSource {
            return Holder.INSTANCE
        }
    }

    override fun postComment(commentRequest: CommentRequest, onResponse: CommentDataSource.OnResponsePostComment) {
        service.postComment(commentRequest).enqueue(
                object : Callback<String> {

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        onResponse.onError()
                    }


                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        if (response?.isSuccessful == true && response.body() == ResponseType.OK.name){
                            onResponse.onSuccess()
                        }else{
                            onResponse.onError()
                        }
                    }
                }
        )
    }

    override fun fetchComment(commentRequest: CommentRequest, onResponse: CommentDataSource.OnResponseFetchComment) {
        service.fetchComments(commentRequest).enqueue(
                object :Callback<List<Comment>>{

                    override fun onFailure(call: Call<List<Comment>>?, t: Throwable?) {
                        onResponse.onError()
                    }

                    override fun onResponse(call: Call<List<Comment>>?, response: Response<List<Comment>>?) {
                        if (response?.isSuccessful == true){
                            response.body()?.let {
                                onResponse.onSuccess(it)
                            }
                        }else{
                            onResponse.onError()
                        }
                    }
                }
        )
    }
}