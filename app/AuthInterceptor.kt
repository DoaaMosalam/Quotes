


//class AuthInterceptor(private val token: String) : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val newRequest = chain.request().newBuilder()
//            .addHeader("Authorization", "Bearer $token")
//            .build()
//        return chain.proceed(newRequest)
//    }
//}