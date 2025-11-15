//package com.kopim.productlist.ui.navigation
//
//sealed class Route(
//    val path: String,
//    vararg val attributes: Pair<String, String>
//) {
//
//    fun toRoute() {
//        var result = path
//        for(attribute in attributes) {
//            result += "?${attribute.first}=${attribute.second}"
//        }
//    }
//
//    data object HomeFeedScreen : Route(Screens.HOME_FEED){
//
//    }
//    data object LostItemsScreen : Route(Names.LOST)
//    data object RegistrationScreen : Route(Names.REGISTRATION)
//    data object AuthorizationScreen : Route(Names.AUTHORIZATION)
//    data object CreateAnnouncementScreen : Route(Names.CREATE_ANNOUNCEMENT)
//    data object ChatsScreen : Route(Names.CHATS)
//    data object VerificationScreen : Route(Names.VERIFICATION)
//
//    private object Screens {
//        const val FOUNDED = "founded_items_screen"
//        const val LOST = "lost_items_screen"
//        const val REGISTRATION = "registration_screen"
//        const val AUTHORIZATION = "authorization_screen"
//        const val PROFILE = "profile_screen"
//        const val ANNOUNCEMENT = "announcement_screen"
//        const val CREATE_ANNOUNCEMENT = "create_announcement_screen"
//        const val CHAT = "chat_screen"
//        const val CHATS = "chats_screen"
//        const val VERIFICATION = "mail_verification"
//    }
//}