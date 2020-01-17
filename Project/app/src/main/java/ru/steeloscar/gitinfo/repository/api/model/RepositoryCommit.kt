package ru.steeloscar.gitinfo.repository.api.model

data class RepositoryCommit(
    val sha: String?,
    val node_id: String?,
    val commit: Commit?,
    val url: String?,
    val html_url: String?,
    val comments_url: String?,
    val author: Committer?,
    val committer: Committer?,
    val parents: ArrayList<Parents?>
) {
    data class Commit(
        val author: Author?,
        val commiter: Author?,
        val message: String?,
        val tree: Tree?,
        val url: String?,
        val comment_count: Int?,
        val verification: Verification?
    ) {
        data class Author(
            val name: String?,
            val email: String?,
            val date: String?
        )

        data class Tree(
            val sha: String?,
            val url: String?
        )

        data class Verification(
            val verified: Boolean?,
            val reason: String?,
            val signature: String?,
            val payload: String?
        )

    }

    data class Committer(
        val login: String?,
        val id: Int?,
        val node_id: String?,
        val avatar_url: String?,
        val gravatar_url: String?,
        val url: String?,
        val html_url: String?,
        val followers_url: String?,
        val following_url: String?,
        val gists_url: String?,
        val starred_url: String?,
        val subscriptions_url: String?,
        val organizations_url: String?,
        val repos_url: String?,
        val events_url: String?,
        val received_events_url: String?,
        val type: String?,
        val site_admin: Boolean?
    )

    data class Parents(
        val sha: String?,
        val url: String?,
        val html_url: String?
    )

}