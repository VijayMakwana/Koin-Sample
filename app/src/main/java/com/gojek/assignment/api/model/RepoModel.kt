package com.gojek.assignment.api.model

data class RepoModel(
    val author: String?, // apple
    val avatar: String?, // https://github.com/apple.png
    val builtBy: List<BuiltBy?>?,
    val currentPeriodStars: Int?, // 488
    val description: String?,
    val forks: Int?, // 64
    val language: String?, // C
    val languageColor: String?, // #555555
    val name: String?, // HomeKitADK
    val stars: Int?, // 1117
    val url: String? // https://github.com/apple/HomeKitADK
) {
    data class BuiltBy(
        val avatar: String?, // https://avatars3.githubusercontent.com/u/1516153
        val href: String?, // https://github.com/cpfister
        val username: String? // cpfister
    )
}