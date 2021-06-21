package com.soethihatun.photogallery.data.model.dto.response

import com.google.gson.annotations.SerializedName

data class PhotosResponse(
    @SerializedName("color")
    val color: String? = null,

    @SerializedName("sponsorship")
    val sponsorship: Sponsorship? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("liked_by_user")
    val likedByUser: Boolean? = null,

    @SerializedName("urls")
    val urls: Urls? = null,

    @SerializedName("alt_description")
    val altDescription: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("width")
    val width: Int? = null,

    @SerializedName("blur_hash")
    val blurHash: String? = null,

    @SerializedName("links")
    val links: Links? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("user")
    val user: User? = null,

    @SerializedName("height")
    val height: Int? = null,

    @SerializedName("likes")
    val likes: Int? = null
)

data class Sponsorship(

    @SerializedName("sponsor")
    val sponsor: Sponsor? = null,

    @SerializedName("tagline_url")
    val taglineUrl: String? = null,

    @SerializedName("tagline")
    val tagline: String? = null,

    @SerializedName("impression_urls")
    val impressionUrls: List<String?>? = null
)

data class Links(

    @SerializedName("followers")
    val followers: String? = null,

    @SerializedName("portfolio")
    val portfolio: String? = null,

    @SerializedName("following")
    val following: String? = null,

    @SerializedName("self")
    val self: String? = null,

    @SerializedName("html")
    val html: String? = null,

    @SerializedName("photos")
    val photos: String? = null,

    @SerializedName("likes")
    val likes: String? = null,

    @SerializedName("download")
    val download: String? = null,

    @SerializedName("download_location")
    val downloadLocation: String? = null
)

data class User(

    @SerializedName("total_photos")
    val totalPhotos: Int? = null,

    @SerializedName("accepted_tos")
    val acceptedTos: Boolean? = null,

    @SerializedName("twitter_username")
    val twitterUsername: String? = null,

    @SerializedName("last_name")
    val lastName: String? = null,

    @SerializedName("bio")
    val bio: String? = null,

    @SerializedName("total_likes")
    val totalLikes: Int? = null,

    @SerializedName("portfolio_url")
    val portfolioUrl: String? = null,

    @SerializedName("profile_image")
    val profileImage: ProfileImage? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("for_hire")
    val forHire: Boolean? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("links")
    val links: Links? = null,

    @SerializedName("total_collections")
    val totalCollections: Int? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("first_name")
    val firstName: String? = null,

    @SerializedName("instagram_username")
    val instagramUsername: String? = null,

    @SerializedName("username")
    val username: String? = null
)

data class ProfileImage(

    @SerializedName("small")
    val small: String? = null,

    @SerializedName("large")
    val large: String? = null,

    @SerializedName("medium")
    val medium: String? = null
)

data class Urls(

    @SerializedName("small")
    val small: String? = null,

    @SerializedName("thumb")
    val thumb: String? = null,

    @SerializedName("raw")
    val raw: String? = null,

    @SerializedName("regular")
    val regular: String? = null,

    @SerializedName("full")
    val full: String? = null
)

data class Sponsor(

    @SerializedName("total_photos")
    val totalPhotos: Int? = null,

    @SerializedName("accepted_tos")
    val acceptedTos: Boolean? = null,

    @SerializedName("twitter_username")
    val twitterUsername: String? = null,

    @SerializedName("last_name")
    val lastName: String? = null,

    @SerializedName("bio")
    val bio: String? = null,

    @SerializedName("total_likes")
    val totalLikes: Int? = null,

    @SerializedName("portfolio_url")
    val portfolioUrl: String? = null,

    @SerializedName("profile_image")
    val profileImage: ProfileImage? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("for_hire")
    val forHire: Boolean? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("links")
    val links: Links? = null,

    @SerializedName("total_collections")
    val totalCollections: Int? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("first_name")
    val firstName: String? = null,

    @SerializedName("instagram_username")
    val instagramUsername: String? = null,

    @SerializedName("username")
    val username: String? = null
)
